package com.db_lab.db_lab6.domain.dto;

import lombok.Data;

@Data
public class CommentDTO {

    private String content;

    private Long postId;

}