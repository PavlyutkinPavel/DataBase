package com.db_lab.db_lab6.domain.dto;

import lombok.Data;

@Data
public class NewsDTO {

    private String title;

    private String newsText;

    private Long football_club_id;
}