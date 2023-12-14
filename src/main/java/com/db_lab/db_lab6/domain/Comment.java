package com.db_lab.db_lab6.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "comments")
@Data
public class Comment {
    @Id
    @SequenceGenerator(name = "commentSeqGen", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "commentSeqGen")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_likes")
    private Long likes;

    @Column(name = "comment_dislikes")
    private Long dislikes;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

}