package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.User;
import com.db_lab.db_lab6.exception.UserNotFoundException;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "User Controller", description = "Makes all operations with users")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all users(for admins)")
    @GetMapping
    public ResponseEntity<List<User>> getUsers(Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (securityService.checkIfAdmin(principal.getName())) {
            List<User> users = userService.getUsers(principal);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "get user by last name(for all users)")
    @GetMapping("/last")
    public ResponseEntity<User> getUserByLastName(@RequestParam String lastName, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findUserByLastName(lastName).orElseThrow(UserNotFoundException::new);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "get user by first name(for all users)")
    @GetMapping("/first")
    public ResponseEntity<User> getUserByFirstName(@RequestParam String firstName, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.findUserByFirstName(firstName).orElseThrow(UserNotFoundException::new);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "get user (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUser(id, principal);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    //maybe should be deleted(or only for admins)
//    @Operation(summary = "create user (for authorized users)")
//    @PostMapping
//    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
//        userService.createUser(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }


    @Operation(summary = "update user (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (securityService.checkIfAdmin(principal.getName()) || (securityService.getUserIdByLogin(principal.getName()) == user.getId())) {
            userService.updateUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete user (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (securityService.checkIfAdmin(principal.getName()) || (securityService.getUserIdByLogin(principal.getName()) == id)) {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
