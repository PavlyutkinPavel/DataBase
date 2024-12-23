package com.db_lab.db_lab6.domain.dto;

import com.db_lab.db_lab6.domain.MessageType;
import lombok.Data;

@Data
public class MessageDTO {

    private String content;

    private MessageType type;

    private Long chat_id;

}