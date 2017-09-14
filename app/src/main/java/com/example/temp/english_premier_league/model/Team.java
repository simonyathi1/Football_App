package com.example.temp.english_premier_league.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by temp on 28/08/2017.
 */

public class Team  implements Comparable<Team>{

    private String [] links;
    private String teamCode;
    private String teamName;
    private String teamShortName;
    private String squadMarketValue;
    private String crestUrl;

    public Team() {}

    public Team(String[] links, String teamName,String teamCode, String teamShortName, String squadMarketValue, String crestUrl) {
        this.links = links;
        this.teamName = teamName;
        this.teamCode = teamCode;
        this.teamShortName = teamShortName;
        this.squadMarketValue = squadMarketValue;
        this.crestUrl = crestUrl;
    }



    public void setLinks(String[] links) {
        this.links = links;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamShortName(String teamShortName) {
        this.teamShortName = teamShortName;
    }

    public void setSquadMarketValue(String squadMarketValue) {
        this.squadMarketValue = squadMarketValue;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String[] getLinks() {
        return links;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamShortName() {
        return teamShortName;
    }

    public String getSquadMarketValue() {
        return squadMarketValue;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public String getTeamCode() {
        return teamCode;
    }


    @Override
    public int compareTo(@NonNull Team team) {
        return teamName.compareTo(team.teamName);
    }
}
