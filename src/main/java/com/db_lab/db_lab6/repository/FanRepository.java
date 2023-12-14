package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Fan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FanRepository extends JpaRepository<Fan, Long> {
}
