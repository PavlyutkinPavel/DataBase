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
@Entity(name = "match_results")
@Data
public class MatchResults {
    @Id
    @SequenceGenerator(name = "matchResultsSeqGen", sequenceName = "match_results_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "matchResultsSeqGen")
    private Long id;

    @Column(name = "final_score")
    private String final_score;

    @Column(name = "description")
    private String description;

    @Column(name = "winner_id")
    private Long winnerId;
}