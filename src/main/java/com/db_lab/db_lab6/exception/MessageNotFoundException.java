package com.db_lab.db_lab6.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(){
        super("Message not found");
    }
}
