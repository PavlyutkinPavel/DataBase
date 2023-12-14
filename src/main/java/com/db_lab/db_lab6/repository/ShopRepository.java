package com.db_lab.db_lab6.repository;

import com.db_lab.db_lab6.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
