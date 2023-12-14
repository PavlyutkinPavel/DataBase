package com.db_lab.db_lab6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.db_lab.db_lab6.domain.Message;
public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteAll();
}
