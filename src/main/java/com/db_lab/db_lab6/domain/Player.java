package com.db_lab.db_lab6.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Schema(description = "Описание пользователя")
@Data
@Entity(name = "players")
public class Player {

    @Schema(description = "Это уникальный идентификатор пользователя")
    @Id
    @SequenceGenerator(name = "playerSeqGen", sequenceName = "players_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "playerSeqGen")
    private Long id;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "player_number")
    private Long playerNumber;

    @Column(name = "titles")
    private String titles;

    @Column(name = "football_club_id")
    private Long footballClubId;

}
