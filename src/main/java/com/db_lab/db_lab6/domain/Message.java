package com.db_lab.db_lab6.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"chats"})
@ToString(exclude = {"chats"})
public class Message {
    @Id
    @SequenceGenerator(name = "messageSeqGen", sequenceName = "messages_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "messageSeqGen")
    private Long id;

    @Column(name = "message_content")
    private String content;

    @Column(name = "sender")
    private String sender;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chats;

}
