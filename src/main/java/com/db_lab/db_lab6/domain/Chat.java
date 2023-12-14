package com.db_lab.db_lab6.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Entity(name = "chats")
@Data
@EqualsAndHashCode(exclude = {"users", "messages"})
@ToString(exclude = {"users", "messages"})
public class Chat {
    @Id
    @SequenceGenerator(name = "chatSeqGen", sequenceName = "chats_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "chatSeqGen")
    private Long id;

    @Column(name = "chat_name")
    private String chatName;

    @Column(name = "chat_description")
    private String description;

    @Column(name = "chat_creator")
    private String creator;

    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "l_users_chats", joinColumns = @JoinColumn(name = "chat_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<User> users;

    @OneToMany(mappedBy = "chats", fetch = FetchType.EAGER)
    private Collection<Message> messages;
}