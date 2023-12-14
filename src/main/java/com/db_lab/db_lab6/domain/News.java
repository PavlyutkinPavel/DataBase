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
@Entity(name = "news")
@Data
public class News {
    @Id
    @SequenceGenerator(name = "newsSeqGen", sequenceName = "news_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "newsSeqGen")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "news_text")
    private String newsText;

    @Column(name = "publication_date")
    private LocalDateTime publicationDate;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "football_club_id")
    private Long football_club_id;
}