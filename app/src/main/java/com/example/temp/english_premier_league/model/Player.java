package com.example.temp.english_premier_league.model;

import android.support.annotation.NonNull;

/**
 * Created by temp on 30/08/2017.
 */

public class Player implements Comparable<Player>{

    private String playerName;
    private String position;
    private String jerseyNumber;
    private String DOB;
    private String nationality;
    private String contractEnd;
    private String marketValue;

    public Player(){}

    public Player(String playerName, String position, String jerseyNumber, String DOB, String nationality, String contractEnd, String marketValue) {
        this.playerName = playerName;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
        this.DOB = DOB;
        this.nationality = nationality;
        this.contractEnd = contractEnd;
        this.marketValue = marketValue;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(String contractEnd) {
        this.contractEnd = contractEnd;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    @Override
    public int compareTo(@NonNull Player player) {

        int compareQuantity = Integer.parseInt(((Player) player).getJerseyNumber());

        //ascending order
        return Integer.parseInt(jerseyNumber) - compareQuantity;

    }
}
