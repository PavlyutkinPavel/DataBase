package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.FootballClub;
import com.db_lab.db_lab6.domain.dto.FootballClubDTO;
import com.db_lab.db_lab6.exception.FootballClubNotFoundException;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.FootballClubService;
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

@Tag(name = "FootballClub Controller", description = "Makes all operations with FootballClub")
@RestController
@RequestMapping("/footballClub")
public class FootballClubController {
    private final FootballClubService footballClubService;
    private final SecurityService securityService;

    public FootballClubController(FootballClubService footballClubService, SecurityService securityService) {
        this.footballClubService = footballClubService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all FootballClubs(for all)")
    @GetMapping
    public ResponseEntity<List<FootballClub>> getFootballClubs() {
        List<FootballClub> footballClubs = footballClubService.getFootballClubs();
        if (footballClubs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(footballClubs, HttpStatus.OK);
        }
    }

    @Operation(summary = "get FootballClub (for all)")
    @GetMapping("/{id}")
    public ResponseEntity<FootballClub> getFootballClub(@PathVariable Long id) {
        FootballClub footballClub = footballClubService.getFootballClub(id);
        if (footballClub != null) {
            return new ResponseEntity<>(footballClub, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "create FootballClub (for admins)")
    @PostMapping
    public ResponseEntity<HttpStatus> createFootballClub(@RequestBody FootballClubDTO footballClubDTO, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (securityService.checkIfAdmin(principal.getName())) {
            footballClubService.createFootballClub(footballClubDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @Operation(summary = "update FootballClub (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateFootballClub(@RequestBody FootballClub footballClub, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (securityService.checkIfAdmin(principal.getName())) {
            footballClubService.updateFootballClub(footballClub);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete FootballClub (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFootballClub(@PathVariable Long id, Principal principal) {
        if(principal == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (securityService.checkIfAdmin(principal.getName())) {
            footballClubService.deleteFootballClubById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
