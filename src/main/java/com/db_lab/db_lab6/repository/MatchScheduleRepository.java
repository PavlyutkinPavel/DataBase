package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.MatchSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchScheduleRepository extends JpaRepository<MatchSchedule, Long> {
}
