package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
