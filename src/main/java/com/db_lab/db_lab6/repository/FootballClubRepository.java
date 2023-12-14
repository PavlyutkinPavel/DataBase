package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.FootballClub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballClubRepository extends JpaRepository<FootballClub, Long> {
}
