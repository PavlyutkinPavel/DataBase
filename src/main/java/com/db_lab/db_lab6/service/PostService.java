package com.db_lab.db_lab6.service;

import com.db_lab.db_lab6.domain.Post;
import com.db_lab.db_lab6.repository.PostRepository;
import com.db_lab.db_lab6.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAllPosts();
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findByIdPost(id);
    }

    public void createPost(Post post) {
        postRepository.savePost(post);
    }

    public void updatePost(Post post) {
        postRepository.saveAndFlushPost(post);
    }

    public void putLike(Long id) {
        Post post = getPost(id).orElseThrow(PostNotFoundException::new);
        post.setLikes(post.getLikes()+1);
        postRepository.saveAndFlushPost(post);
    }
    public void putDislike(Long id) {
        Post post = getPost(id).orElseThrow(PostNotFoundException::new);
        post.setDislikes(post.getDislikes()+1);
        postRepository.saveAndFlushPost(post);
    }

    public void deletePostById(Long id) {
        postRepository.deleteByIdPost(id);
    }

}