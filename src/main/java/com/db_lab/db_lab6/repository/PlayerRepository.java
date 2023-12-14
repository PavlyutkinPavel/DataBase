package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
