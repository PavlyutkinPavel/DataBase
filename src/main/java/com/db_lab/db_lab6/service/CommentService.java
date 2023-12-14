package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Comment;
import com.db_lab.db_lab6.exception.CommentNotFoundException;
import com.db_lab.db_lab6.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments() {
        return commentRepository.findAllComments();
    }

    public Optional<Comment> getComment(Long id) {
        return commentRepository.findByIdComment(id);
    }

    public void createComment(Comment comment) {
        commentRepository.saveComment(comment);
    }

    public void updateComment(Comment comment) {
        commentRepository.saveAndFlushComment(comment);
    }

    public void putLike(Long id) {
        Comment comment = getComment(id).orElseThrow(CommentNotFoundException::new);
        comment.setLikes(comment.getLikes()+1);
        commentRepository.saveAndFlushComment(comment);
    }
    public void putDislike(Long id) {
        Comment comment = getComment(id).orElseThrow(CommentNotFoundException::new);
        comment.setDislikes(comment.getDislikes()+1);
        commentRepository.saveAndFlushComment(comment);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteByIdComment(id);
    }

}