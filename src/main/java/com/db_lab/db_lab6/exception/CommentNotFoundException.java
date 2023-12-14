package com.db_lab.db_lab6.exception;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(){
        super("Comment not found");
    }
}
