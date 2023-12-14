package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.MatchSchedule;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.MatchScheduleService;
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

@Tag(name = "MatchSchedule Controller", description = "Makes all operations with MatchSchedule")
@RestController
@RequestMapping("/matchSchedule")
public class MatchScheduleController {

    private final MatchScheduleService matchScheduleService;
    private final SecurityService securityService;

    public MatchScheduleController(MatchScheduleService matchScheduleService, SecurityService securityService) {
        this.matchScheduleService = matchScheduleService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all MatchSchedule(for admins)")
    @GetMapping
    public ResponseEntity<List<MatchSchedule>> getMatchSchedules() {
        List<MatchSchedule> matchSchedules = matchScheduleService.getMatchSchedules();
        if (matchSchedules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(matchSchedules, HttpStatus.OK);
        }
    }

    @Operation(summary = "get MatchSchedule (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<MatchSchedule> getMatchSchedule(@PathVariable Long id) {
        MatchSchedule matchSchedule = matchScheduleService.getMatchSchedule(id);
        if (matchSchedule != null) {
            return new ResponseEntity<>(matchSchedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create MatchSchedule (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createMatchSchedule(@RequestBody MatchSchedule matchSchedule) {
        matchScheduleService.createMatchSchedule(matchSchedule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update MatchSchedule (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateMatchSchedule(@RequestBody MatchSchedule matchSchedule, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            matchScheduleService.updateMatchSchedule(matchSchedule);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete MatchSchedule (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMatchSchedule(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            matchScheduleService.deleteMatchScheduleById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
