package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.MatchResults;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchResultsRepository extends JpaRepository<MatchResults, Long> {

    @Query(value = "SELECT * FROM match_results", nativeQuery = true)
    List<MatchResults> findAllResults();

    @Query(value = "SELECT * FROM match_results WHERE id = :id", nativeQuery = true)
    Optional<MatchResults> findByIdResult(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO match_results (id, description, final_score, winner_id) " +
            "VALUES (:#{#matchResults.id}, :#{#matchResults.description}, :#{#matchResults.finalScore}, :#{#matchResults.winnerId})", nativeQuery = true)
    void saveResult(@Param("matchResults") MatchResults matchResults);

    @Transactional
    @Modifying
    @Query(value = "UPDATE match_results SET description = :#{#matchResults.description}, " +
            "final_score = :#{#matchResults.finalScore}, winner_id = :#{#matchResults.winnerId} " +
            "WHERE id = :#{#matchResults.id}", nativeQuery = true)
    void saveAndFlushResult(@Param("matchResults") MatchResults matchResults);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM match_results WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") Long id);
}
