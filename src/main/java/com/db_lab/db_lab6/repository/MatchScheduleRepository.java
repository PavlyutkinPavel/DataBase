package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.MatchSchedule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchScheduleRepository extends JpaRepository<MatchSchedule, Long> {

    @Query(value = "SELECT * FROM match_schedule", nativeQuery = true)
    List<MatchSchedule> findAllMatches();

    @Query(value = "SELECT * FROM match_schedule WHERE id = :id", nativeQuery = true)
    Optional<MatchSchedule> findByIdMatch(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO match_schedule (id, match_date, match_location, home_team_id, away_team_id, available_tickets) " +
            "VALUES (:#{#matchSchedule.id}, :#{#matchSchedule.matchDate}, :#{#matchSchedule.matchLocation}, " +
            ":#{#matchSchedule.homeTeam}, :#{#matchSchedule.awayTeam}, :#{#matchSchedule.availableTickets})", nativeQuery = true)
    void saveMatch(@Param("matchSchedule") MatchSchedule matchSchedule);

    @Transactional
    @Modifying
    @Query(value = "UPDATE match_schedule SET match_date = :#{#matchSchedule.matchDate}, " +
            "match_location = :#{#matchSchedule.matchLocation}, home_team_id = :#{#matchSchedule.homeTeam}, " +
            "away_team_id = :#{#matchSchedule.awayTeam}, available_tickets = :#{#matchSchedule.availableTickets} " +
            "WHERE id = :#{#matchSchedule.id}", nativeQuery = true)
    void saveAndFlushMatch(@Param("matchSchedule") MatchSchedule matchSchedule);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM match_schedule WHERE id = :id", nativeQuery = true)
    void deleteByIdMatch(@Param("id") Long id);
}
