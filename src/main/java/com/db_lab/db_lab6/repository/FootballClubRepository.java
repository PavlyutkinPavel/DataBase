package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.FootballClub;
import com.db_lab.db_lab6.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FootballClubRepository extends JpaRepository<FootballClub, Long> {

    @Query(value = "SELECT * FROM football_club ORDER BY club_name", nativeQuery = true)
    List<FootballClub> findAllOrderedByName();

    @Query(value = "SELECT * FROM football_club WHERE club_name = :name", nativeQuery = true)
    FootballClub findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM football_club WHERE location = :location", nativeQuery = true)
    List<FootballClub> findByLocation(@Param("location") String location);

    @Query(value = "SELECT * FROM football_club WHERE achievements IS NOT NULL", nativeQuery = true)
    List<FootballClub> findClubsWithAchievements();

    @Query(value = "SELECT * FROM football_club WHERE status = :status", nativeQuery = true)
    List<FootballClub> findByStatus(@Param("status") String status);

    @Query(value = "SELECT * FROM football_club", nativeQuery = true)
    List<FootballClub> findAllClubs();

    @Query(value = "SELECT * FROM football_club WHERE id = :id", nativeQuery = true)
    Optional<FootballClub> findByIdClub(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO football_club (id, club_name, location, achievements, status, wins) VALUES (:#{#footballClub.id}, :#{#footballClub.clubName},:#{#footballClub.location}, :#{#footballClub.achievements}, :#{#footballClub.status}, :#{#footballClub.wins})", nativeQuery = true)
    void saveFootballClub(@Param("footballClub") FootballClub footballClub);

    @Transactional
    @Modifying
    @Query(value = "UPDATE football_club SET club_name = :#{#footballClub.clubName}, location = :#{#footballClub.location}, achievements = :#{#footballClub.achievements}, status = :#{#footballClub.status}, wins = :#{#footballClub.wins} WHERE id = :#{#footballClub.id}", nativeQuery = true)
    void saveAndFlushFootballClub(@Param("footballClub") FootballClub footballClub);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM football_club WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
