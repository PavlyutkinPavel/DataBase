package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.News;
import com.db_lab.db_lab6.exception.NewsNotFoundException;
import com.db_lab.db_lab6.repository.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getNewsList() {
        return newsRepository.findAll(Sort.by("id"));
    }

    public News getNews(Long id) {
        return newsRepository.findById(id).orElseThrow(NewsNotFoundException::new);
    }
    public void createNews(News news) {
        newsRepository.save(news);
    }

    public void updateNews(News news) {
        newsRepository.saveAndFlush(news);
    }

    @Transactional
    public void deleteNewsById(Long id){
        newsRepository.deleteById(id);
    }

}