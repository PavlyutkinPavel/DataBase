package com.db_lab.db_lab6.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Schema(description = "Описание пользователя")
@Data
@Entity(name = "coaches")
public class Coach {

    @Schema(description = "Это уникальный идентификатор пользователя")
    @Id
    @SequenceGenerator(name = "coachesSeqGen", sequenceName = "coaches_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "coachesSeqGen")
    private Long id;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "biography")
    private Long biography;

    @Column(name = "achievements")
    private String achievements;

    @Column(name = "football_club_id")
    private Long footballClubId;

}
