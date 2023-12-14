package com.db_lab.db_lab6.security.controller;

import com.db_lab.db_lab6.security.domain.AdminDTO;
import com.db_lab.db_lab6.security.domain.AuthRequest;
import com.db_lab.db_lab6.security.domain.AuthResponse;
import com.db_lab.db_lab6.security.domain.RegistrationDTO;
import com.db_lab.db_lab6.security.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest){
        //1. generate JWT if all is good
        //2. return 401 code if all is bad
        String token = securityService.generateToken(authRequest);
        if (token.isBlank()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationDTO registrationDTO){
        securityService.registration(registrationDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<HttpStatus> admin(@RequestBody AdminDTO adminDTO, Principal principal){
        securityService.authorizeAdmin(adminDTO, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}