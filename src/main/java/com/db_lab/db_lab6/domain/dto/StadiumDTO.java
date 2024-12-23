package com.db_lab.db_lab6.domain.dto;

import lombok.Data;

@Data
public class StadiumDTO {

    private String stadiumName;

    private String stadiumLocation;

    private Long capacity;

    private String footballClubId;

}