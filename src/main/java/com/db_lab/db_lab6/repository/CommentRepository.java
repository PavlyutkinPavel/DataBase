package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM comments WHERE id = :commentId")
    Optional<Comment> findByIdComment(@Param("commentId") Long commentId);

    @Query(nativeQuery = true, value = "SELECT * FROM comments")
    List<Comment> findAllComments();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO comments(id, comment_content, likes, dislikes, user_id) VALUES (:#{#comment.id}, :#{#comment.content}, :#{#comment.likes}, :#{#comment.dislikes}, :#{#comment.userId})")
    void saveComment(@Param("comment") Comment comment);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE comments SET comment_content = :#{#comment.content}, likes = :#{#comment.likes}, dislikes = :#{#comment.dislikes}, user_id = :#{#comment.userId} WHERE id = :#{#comment.id}")
    void saveAndFlushComment(@Param("comment") Comment comment);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM comments WHERE id = :commentId")
    void deleteByIdComment(@Param("commentId") Long commentId);
}
