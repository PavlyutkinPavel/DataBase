package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.Forum;
import com.db_lab.db_lab6.exception.ForumNotFoundException;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.ForumService;
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

@Tag(name = "Forum Controller", description = "Makes all operations with Forum")
@RestController
@RequestMapping("/forum")
public class ForumController {

    private final ForumService forumService;
    private final SecurityService securityService;

    public ForumController(ForumService forumService, SecurityService securityService) {
        this.forumService = forumService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all Forums(for admins)")
    @GetMapping
    public ResponseEntity<List<Forum>> getForums() {
        List<Forum> forums = forumService.getForums();
        if (forums.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(forums, HttpStatus.OK);
        }
    }

    @Operation(summary = "get Forum (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<Forum> getForum(@PathVariable Long id) {
        Forum forum = forumService.getForum(id);
        if (forum != null) {
            return new ResponseEntity<>(forum, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create Forum (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createForum(@RequestBody Forum forum) {
        forumService.createForum(forum);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update Forum (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateForum(@RequestBody Forum forum, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            forumService.updateForum(forum);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete Forum (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteForum(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            forumService.deleteForumById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
