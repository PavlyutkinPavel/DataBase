package com.db_lab.db_lab6.repository;

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
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM users u WHERE u.lastName = :ln")
    Optional<User> findByLastName(@Param("ln") String ln);

    @Query("SELECT u FROM users u WHERE u.firstName = :fn")
    Optional<User> findByFirstName(@Param("fn") String fn);

    @Query("SELECT u FROM users u WHERE u.id = :userId")
    Optional<User> findByIdUser(Long userId);

    @Query("SELECT u FROM users u")
    List<User> findAllUsers();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO users(id, first_name, last_name, created, email, favourite_club_id) VALUES (:#{#user.id}, :#{#user.firstName}, :#{#user.lastName}, :#{#user.createdAt}, :#{#user.email}, :#{#user.favouriteClubId})")
    void saveUser(@Param("user") User user);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users SET first_name = :#{#user.firstName}, last_name = :#{#user.lastName}, created = :#{#user.createdAt}, email = :#{#user.email}, favourite_club_id = :#{#user.favouriteClubId} WHERE id = :#{#user.id}")
    void saveAndFlushUser(@Param("user") User user);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE id = :id")
    void deleteByIdUser(@Param("id") Long id);

    @Query(value = "SELECT NEXTVAL('users_id_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}
