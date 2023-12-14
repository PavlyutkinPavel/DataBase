package com.db_lab.db_lab6.exception;

public class ForumNotFoundException extends RuntimeException{
    public ForumNotFoundException(){
        super("Chat not found");
    }
}

