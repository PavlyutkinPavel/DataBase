package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Player;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.PlayerService;
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
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;
    private final SecurityService securityService;

    public PlayerController(PlayerService playerService, SecurityService securityService) {
        this.playerService = playerService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all players(for admins)")
    @GetMapping
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = playerService.getPlayers();
        if (players.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(players, HttpStatus.OK);
        }
    }

    @Operation(summary = "get Player (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        Player player = playerService.getPlayer(id);
        if (player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create player (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createPlayer(@RequestBody Player player) {
        playerService.createPlayer(player);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update player (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updatePlayer(@RequestBody Player player, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            playerService.updatePlayer(player);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete player (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePlayer(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            playerService.deletePlayerById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
