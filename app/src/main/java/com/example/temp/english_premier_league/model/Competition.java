package com.example.temp.english_premier_league.model;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by temp on 30/08/2017.
 */

public class Competition implements Comparable<Competition>{

    private String[] links;
    private String id;
    private String league;
    private String year;
    private String currentMatchDay;
    private String numberOfMatchDay;
    private String numberOfTeams;
    private String numberOfGames;
    private String lastUpdated;

    public Competition(){}

    public Competition(String[] links, String id, String league, String year, String currentMatchDay, String numberOfMatchDay, String numberOfTeams, String numberOfGames, String lastUpdated) {
        this.links = links;
        this.id = id;
        this.league = league;
        this.year = year;
        this.currentMatchDay = currentMatchDay;
        this.numberOfMatchDay = numberOfMatchDay;
        this.numberOfTeams = numberOfTeams;
        this.numberOfGames = numberOfGames;
        this.lastUpdated = lastUpdated;
    }




    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCurrentMatchDay() {
        return currentMatchDay;
    }

    public void setCurrentMatchDay(String currentMatchDay) {
        this.currentMatchDay = currentMatchDay;
    }

    public String getNumberOfMatchDay() {
        return numberOfMatchDay;
    }

    public void setNumberOfMatchDay(String numberOfMatchDay) {
        this.numberOfMatchDay = numberOfMatchDay;
    }

    public String getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(String numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(String numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int compareTo(@NonNull Competition competition) {
        return league.compareTo(competition.getLeague());
    }



}
