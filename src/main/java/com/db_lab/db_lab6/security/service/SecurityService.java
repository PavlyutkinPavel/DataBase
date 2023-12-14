package com.db_lab.db_lab6.security.service;


import com.db_lab.db_lab6.domain.Chat;
import com.db_lab.db_lab6.domain.Role;
import com.db_lab.db_lab6.domain.User;
import com.db_lab.db_lab6.exception.UserNotFoundException;
import com.db_lab.db_lab6.repository.UserRepository;
import com.db_lab.db_lab6.security.JwtUtils;
import com.db_lab.db_lab6.security.domain.AdminDTO;
import com.db_lab.db_lab6.security.domain.AuthRequest;
import com.db_lab.db_lab6.security.domain.RegistrationDTO;
import com.db_lab.db_lab6.security.domain.SecurityCredentials;
import com.db_lab.db_lab6.security.repository.SecurityCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SecurityService {

    private final PasswordEncoder passwordEncoder;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final JwtUtils jwtUtils;

    private final User user;

    private final SecurityCredentials securityCredentials;

    private final UserRepository userRepository;

    public SecurityService(PasswordEncoder passwordEncoder, SecurityCredentialsRepository securityCredentialsRepository, JwtUtils jwtUtils, User user, SecurityCredentials securityCredentials, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.securityCredentialsRepository = securityCredentialsRepository;
        this.jwtUtils = jwtUtils;
        this.user = user;
        this.securityCredentials = securityCredentials;
        this.userRepository = userRepository;
    }

    public String generateToken(AuthRequest authRequest){
        //1. get User by login
        //2. check passwords
        //3. generate token by login
        //4. if all is bad then return empty string ""
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByUserLogin(authRequest.getLogin());
        if (credentials.isPresent() && passwordEncoder.matches(authRequest.getPassword(),credentials.get().getUserPassword())){
            return jwtUtils.generateJwtToken(authRequest.getLogin());
        }
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDTO registrationDTO){
        //1. parse DTO
        //2. create User+SecurityCredentials
        //3. make transaction and execution
        //Optional<SecurityCredentials> securityCredentials1 = securityCredentialsRepository.findByUserLogin(registrationDTO.getUserLogin())

        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setCreatedAt(LocalDateTime.now());
        user.setEmail(registrationDTO.getEmail());
        user.setFavouriteClubId(registrationDTO.getFavouriteClub());
        Integer size = userRepository.findAllUsers().size();
        user.setId((long) (size+1));
        userRepository.saveUser(user);
        User userResult = userRepository.findByFirstName(registrationDTO.getFirstName()).orElseThrow(UserNotFoundException::new);

        securityCredentials.setUserLogin(registrationDTO.getUserLogin());
        securityCredentials.setUserPassword(passwordEncoder.encode(registrationDTO.getUserPassword()));
        securityCredentials.setUserRole(Role.USER);
        securityCredentials.setUserId(userResult.getId());
        securityCredentialsRepository.save(securityCredentials);
    }

    public Boolean checkIfAdmin(String login){
        Optional<SecurityCredentials> credentials = securityCredentialsRepository.findByUserLogin(login);
        if (credentials.isPresent() && credentials.get().getUserRole().toString() == "ADMIN"){
            return true;
        }else{
            return false;
        }
    }

    public void authorizeAdmin(AdminDTO adminDTO, Principal principal){
        String adminPassword = "admin";
        log.info(adminDTO.getAdminPassword());
        if(adminPassword.equals(adminDTO.getAdminPassword())){
            SecurityCredentials securityCredentials = securityCredentialsRepository.findByUserLogin(principal.getName()).orElseThrow(UserNotFoundException::new);
            securityCredentials.setUserRole(Role.ADMIN);
            securityCredentialsRepository.save(securityCredentials);
        }else {
            log.info("User "+ principal.getName() + " tried to become admin");
        }
    }

    public String getUserByLogin(String login){
        SecurityCredentials credentials = securityCredentialsRepository.findByUserLogin(login).orElseThrow(UserNotFoundException::new);
        if (credentials != null){
            return credentials.getUserLogin();
        }else{
            return "";
        }
    }

    public Long getUserIdByLogin(String login){
        String username  = getUserByLogin(login);
        SecurityCredentials credentials = securityCredentialsRepository.findUserIdByLogin(username).orElseThrow(UserNotFoundException::new);
        if (credentials != null){
            return credentials.getUserId();
        }else{
            return 0L;
        }
    }
}
