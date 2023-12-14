package com.db_lab.db_lab6.exception;

public class CoachNotFoundException extends RuntimeException{
    public CoachNotFoundException(){
        super("Coach not found");
    }
}

