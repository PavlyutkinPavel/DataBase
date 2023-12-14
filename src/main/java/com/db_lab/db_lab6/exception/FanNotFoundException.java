package com.db_lab.db_lab6.exception;
public class FanNotFoundException extends RuntimeException{
    public FanNotFoundException(){
        super("Fan not found");
    }
}

