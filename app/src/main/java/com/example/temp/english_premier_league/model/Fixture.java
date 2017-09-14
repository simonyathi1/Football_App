package com.example.temp.english_premier_league.model;

/**
 * Created by temp on 30/08/2017.
 */

public class Fixture {
    private String [] links;
    private String teamCode;
    private String HomeTeamName;
    private String AwayTeamName;
    private String date;
    private String status;
    private String matchday;
    private String awayTeamCrest;
    private String homeTeamCrest;
    private String [] fullTime;
    private String [] halfTime;
    private String odds;

    public Fixture(){}

    public Fixture(String[] links, String homeTeamName, String awayTeamName, String date, String status, String matchday, String awayTeamCrest, String homeTeamCrest, String[] fullTime, String[] halfTime,String odds) {
        this.links = links;

        HomeTeamName = homeTeamName;
        AwayTeamName = awayTeamName;
        this.date = date;
        this.status = status;
        this.matchday = matchday;
        this.awayTeamCrest = awayTeamCrest;
        this.homeTeamCrest = homeTeamCrest;
        this.fullTime = fullTime;
        this.halfTime = halfTime;
        this.odds = odds;
    }

    public String getAwayTeamCrest() {
        return awayTeamCrest;
    }

    public void setAwayTeamCrest(String awayTeamCrest) {
        this.awayTeamCrest = awayTeamCrest;
    }

    public String getHomeTeamCrest() {
        return homeTeamCrest;
    }

    public void setHomeTeamCrest(String homeTeamCrest) {
        this.homeTeamCrest = homeTeamCrest;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getHomeTeamName() {
        return HomeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        HomeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return AwayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        AwayTeamName = awayTeamName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMatchday() {
        return matchday;
    }

    public void setMatchday(String matchday) {
        this.matchday = matchday;
    }

    public String[] getFullTime() {
        return fullTime;
    }

    public void setFullTime(String[] fullTime) {
        this.fullTime = fullTime;
    }

    public String[] getHalfTime() {
        return halfTime;
    }

    public void setHalfTime(String[] halfTime) {
        this.halfTime = halfTime;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getOdds() {

        return odds;
    }
}
