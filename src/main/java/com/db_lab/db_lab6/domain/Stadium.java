package com.db_lab.db_lab6.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Entity(name = "stadiums")
@Data
public class Stadium {
    @Id
    @SequenceGenerator(name = "stadiumSeqGen", sequenceName = "stadiums_id_seq", allocationSize = 1)//для нерандомных id а по sequence
    @GeneratedValue(generator = "stadiumSeqGen")
    private Long id;

    @Column(name = "stadium_name")
    private String stadiumName;

    @Column(name = "stadium_location")
    private String stadiumLocation;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "football_club_id")
    private String footballClubId;

}
