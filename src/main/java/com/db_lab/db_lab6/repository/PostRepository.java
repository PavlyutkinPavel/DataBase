package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Post;
import com.db_lab.db_lab6.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM db_university.post WHERE id = :postId ")
    Optional<Post> findByIdPost(Long postId);

    @Query(nativeQuery = true, value = "SELECT * FROM db_university.post")
    List<Post> findAllPosts();
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO db_university.post(id, post_text, description, post_likes, post_dislikes, comment_number, created_date, user_id) VALUES (:#{#post.id}, :#{#post.title}, :#{#post.content}, :#{#post.likes}, :#{#post.dislikes}, :#{#post.comments}, :#{#post.createdAt}, :#{#post.userId})")
    void savePost(@Param("post") Post post);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE db_university.post SET post_text = :#{#post.title}, description = :#{#post.email}, post_likes = :#{#post.likes}, post_dislikes = :#{#post.dislikes}, comment_number = :#{#post.comments}, created_date = :#{#post.createdAt}, user_id = :#{#post.userId} WHERE id = :#{#post.id}")
    void saveAndFlushPost(@Param("post") Post post);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM db_university.post WHERE id = :id")
    void deleteByIdPost(@Param("id") Long id);
}
