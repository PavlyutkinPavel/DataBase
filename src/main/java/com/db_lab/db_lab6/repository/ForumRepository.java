package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Forum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM forum")
    List<Forum> findAllForums();

    @Query(nativeQuery = true, value = "SELECT * FROM forum WHERE id = :id")
    Optional<Forum> findForumById(@Param("id") Long id);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO forum (id, title, description, forum_members, date_creation, user_id, post_id) " +
            "VALUES (:#{#forum.id}, :#{#forum.title}, :#{#forum.content}, :#{#forum.members}, :#{#forum.dateCreation}, :#{#forum.userId}, :#{#forum.postId})")
    void saveForum(@Param("forum") Forum forum);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE forum f SET f.title = :#{#forum.title}, f.description = :#{#forum.content}, f.forum_members = :#{#forum.members}, " +
            "f.date_creation = :#{#forum.dateCreation}, f.user_id = :#{#forum.userId}, f.post_id = :#{#forum.postId} " +
            "WHERE f.id = :#{#forum.id}")
    void saveAndFlushForum(@Param("forum") Forum forum);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM forum WHERE id = :id")
    void deleteForumById(@Param("id") Long id);
}
