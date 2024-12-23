package com.db_lab.db_lab6.controller;

import com.db_lab.db_lab6.domain.News;
import com.db_lab.db_lab6.security.service.SecurityService;
import com.db_lab.db_lab6.service.NewsService;
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

@Tag(name = "News Controller", description = "Makes all operations with News")
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final SecurityService securityService;

    public NewsController(NewsService newsService, SecurityService securityService) {
        this.newsService = newsService;
        this.securityService = securityService;
    }

    @Operation(summary = "get all News(for admins)")
    @GetMapping
    public ResponseEntity<List<News>> getNewsList() {
        List<News> newsList = newsService.getNewsList();
        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        }
    }

    @Operation(summary = "get News (for authorized users)")
    @GetMapping("/{id}")
    public ResponseEntity<News> getNews(@PathVariable Long id) {
        News news = newsService.getNews(id);
        if (news != null) {
            return new ResponseEntity<>(news, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //maybe should be deleted(or only for admins)
    @Operation(summary = "create News (for authorized users)")
    @PostMapping
    public ResponseEntity<HttpStatus> createNews(@RequestBody News news) {
        newsService.createNews(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "update News (for authorized users)")
    @PutMapping
    public ResponseEntity<HttpStatus> updateNews(@RequestBody News news, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            newsService.updateNews(news);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "delete News (for authorized users)")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNews(@PathVariable Long id, Principal principal) {
        if (securityService.checkIfAdmin(principal.getName())) {
            newsService.deleteNewsById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}