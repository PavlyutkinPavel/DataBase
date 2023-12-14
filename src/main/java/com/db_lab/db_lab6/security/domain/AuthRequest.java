package com.db_lab.db_lab6.security.domain;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}