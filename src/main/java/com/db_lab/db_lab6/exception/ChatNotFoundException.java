package com.db_lab.db_lab6.exception;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException(){
            super("Chat not found");
        }
}

