package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Coach;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.CoachService;
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
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Coach Controller", description = "Makes all operations with coaches")
@RestController
@RequestMapping("/coach")
public class CoachController {
    private final CoachService coachService;
    private final SecurityService securityService;

    public CoachController(CoachService coachService, SecurityService securityService) {
        this.coachService = coachService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all coaches(for admins)")
    @GetMapping
    public ResponseEntity<List<Coach>> getCoaches() {
        List<Coach> coaches = coachService.getCoaches();
        if (coaches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(coaches, HttpStatus.OK);
        }
    }

    @Operation(summary = "get coach (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Coach> getCoach(@PathVariable Long id) {
        Coach coach = coachService.getCoach(id);
        if (coach != null) {
            return new ResponseEntity<>(coach, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create coach (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createCoach(@RequestBody Coach coach) {
        coachService.createCoach(coach);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update coach (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateCoach(@RequestBody Coach coach, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            coachService.updateCoach(coach);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete coach (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCoach(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            coachService.deleteCoachById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
