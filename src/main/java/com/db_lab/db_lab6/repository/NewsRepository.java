package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
