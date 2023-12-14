package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Forum;
import com.db_lab.db_lab6.exception.ForumNotFoundException;
import com.db_lab.db_lab6.repository.ForumRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ForumService {
    private final ForumRepository forumRepository;

    public ForumService(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }
    public List<Forum> getForums() {
        return forumRepository.findAllForums();
    }
    public Forum getForum(Long id) {
        return forumRepository.findForumById(id).orElseThrow(ForumNotFoundException::new);
    }
    @Transactional
    public void createForum(Forum forum) {
        forumRepository.saveForum(forum);
    }
    @Transactional
    public void updateForum(Forum forum) {
        forumRepository.saveAndFlushForum(forum);
    }
    @Transactional
    public void deleteForumById(Long id){
        forumRepository.deleteForumById(id);
    }

}