package com.db_lab.db_lab6.exception;

public class FootballClubNotFoundException extends RuntimeException{
    public FootballClubNotFoundException(){
        super("FootballClub not found");
    }
}

