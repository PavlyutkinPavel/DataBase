package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.MatchResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchResultsRepository extends JpaRepository<MatchResults, Long> {
}
