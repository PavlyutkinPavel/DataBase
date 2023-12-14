package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Fan;
import com.db_lab.db_lab6.exception.FanNotFoundException;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.FanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.diagnostics.FailureAnalysis;
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

@Tag(name = "Fan Controller", description = "Makes all operations with fans community")
@RestController
@RequestMapping("/fan")
public class FanController {
    private final FanService fanService;
    private final SecurityService securityService;

    public FanController(FanService fanService, SecurityService securityService) {
        this.fanService = fanService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all fans community(for admins)")
    @GetMapping
    public ResponseEntity<List<Fan>> getFans() {
        List<Fan> fans = fanService.getFans();
        if (fans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(fans, HttpStatus.OK);
        }
    }

    @Operation(summary = "get fans community (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Fan> getFan(@PathVariable Long id) {
        Fan fan = fanService.getFan(id);
        if (fan != null) {
            return new ResponseEntity<>(fan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create fan (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createFan(@RequestBody Fan fan) {
        fanService.createFan(fan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update fan (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateFan(@RequestBody Fan fan, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            fanService.updateFan(fan);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete fan (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFan(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            fanService.deleteFanById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
