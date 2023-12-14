package com.db_lab.db_lab6.domain;

import com.db_lab.db_lab6.domain.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "football_club")
@Data
public class FootballClub {
    @Id
    @SequenceGenerator(name = "footballClubSeqGen", sequenceName = "football_club_id_seq", allocationSize = 1)//для нерандомных id а по sequence
    @GeneratedValue(generator = "footballClubSeqGen")
    private Long id;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "club_logo")
    private String club_logo;

    @Column(name = "location")
    private String location;

    @Column(name = "achievements")
    private String achievements;

    @Column(name = "status")
    private String status;

    @Column(name = "wins")
    private String wins;

}
