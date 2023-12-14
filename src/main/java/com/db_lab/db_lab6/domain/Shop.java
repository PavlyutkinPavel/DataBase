package com.db_lab.db_lab6.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

@Component
@Entity(name = "shop")
@Data
public class Shop {
    @Id
    @SequenceGenerator(name = "shopSeqGen", sequenceName = "shop_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "shopSeqGen")
    private Long id;

    @Column(name = "match_id")
    private Long matchId;

    @Column(name = "user_id")
    private Long user_id;
}