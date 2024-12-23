package com.db_lab.db_lab6.domain.dto;

import lombok.Data;

@Data
public class PlayerDTO {

    private String playerName;

    private Long playerNumber;

    private String titles;

    private Long footballClubId;
}