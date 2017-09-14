package com.example.temp.english_premier_league.model;

import java.util.ArrayList;

/**
 * Created by temp on 11/09/2017.
 */

//Class names must always start with uppercase
public class Log {

    // CamelCase when naming e.g. teamLink
    private String teamlink;
    private ArrayList<Integer> teamPoints = new ArrayList<>();
    private ArrayList<Integer> goalsFor = new ArrayList<>();
    private ArrayList<Integer> goalsAgainst = new ArrayList<>();
    
    public Log(){}
    
    public Log(String teamlink, ArrayList<Integer> teamPoints, ArrayList<Integer> goalsFor, ArrayList<Integer> goalsAgainst) {
        this.teamlink = teamlink;
        this.teamPoints = teamPoints;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
    }
    

    public String getTeamlink() {
        
        return teamlink;
    }

    public void setTeamlink(String teamlink) {
        this.teamlink = teamlink;
    }

    public ArrayList<Integer> getTeampoints() {
        return teamPoints;
    }

    public void setTeampoints(ArrayList<Integer> teampoints) {
        this.teamPoints = teampoints;
    }

    public ArrayList<Integer> getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(ArrayList<Integer> goalsFor) {
        this.goalsFor = goalsFor;
    }

    public ArrayList<Integer> getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(ArrayList<Integer> goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }
}
