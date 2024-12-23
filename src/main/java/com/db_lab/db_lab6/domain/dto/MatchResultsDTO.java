package com.db_lab.db_lab6.domain.dto;

import lombok.Data;

@Data
public class MatchResultsDTO {

    private String final_score;

    private String description;

    private Long winnerId;
}