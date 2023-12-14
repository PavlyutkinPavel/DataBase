package com.db_lab.db_lab6.exception;


public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(){
        super("Post not found");
    }
}
