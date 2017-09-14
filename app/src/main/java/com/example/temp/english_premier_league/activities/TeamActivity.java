package com.example.temp.english_premier_league.activities;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Team;

import static com.example.temp.english_premier_league.Controller.displayImage;

public class TeamActivity extends AppCompatActivity {

    private ImageView mBigCrest;
    private TextView mFixtures, mPlayers, mTeamName, mShortName ,mCode, mSquadMV;
    private String [] mlinks, mTeamlinks, mcrestLinks;
    private String mteam,mShtNm, mCd,mSmv,mCrst, mCurrentCompetitionLink, mCompetitionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        mlinks = getIntent().getStringArrayExtra("links");
        mteam = getIntent().getStringExtra("TeamName");
        mShtNm = getIntent().getStringExtra("ShortName");
        mCd = getIntent().getStringExtra("Code");
        mSmv = getIntent().getStringExtra("SquadValue");
        mCrst = getIntent().getStringExtra("Crest");
        mTeamlinks = getIntent().getStringArrayExtra("teams");
        mcrestLinks = getIntent().getStringArrayExtra("crests");
        mCurrentCompetitionLink = getIntent().getStringExtra("currentCompetition");
        actionBarSetup(mteam,"Dashboard");

        Team selectedTeam = new Team(mlinks,mteam,mCd,mShtNm,mSmv,mCrst);
        init(selectedTeam);
        final Intent intent_to_fixtures =new Intent(TeamActivity.this,FixturesActivity.class);
        intent_to_fixtures.putExtra("Fixture_link",String.valueOf(mlinks[1])) ;
        intent_to_fixtures.putExtra("teams",mTeamlinks);
        intent_to_fixtures.putExtra("crests",mcrestLinks);
        intent_to_fixtures.putExtra("currentCompetition",mCurrentCompetitionLink);
        intent_to_fixtures.putExtra("ActionBarTitle",mCompetitionName);
        intent_to_fixtures.putExtra("TeamName",mteam);


        final Intent intent_to_players = new Intent(TeamActivity.this,PlayersActivity.class);
        intent_to_players.putExtra("Players_link",String.valueOf(mlinks[2]));
        intent_to_players.putExtra("teamCrest",mCrst);
        intent_to_players.putExtra("TeamName",mteam);


        mFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(intent_to_fixtures);
            }
        });
        mPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent_to_players);
            }
        });

    }
    public void init( Team item)
    {
        mBigCrest = (ImageView) findViewById(R.id.big_crest);
        mFixtures = (TextView) findViewById(R.id.fixture_textView);
        mPlayers = (TextView) findViewById(R.id.players_textView);
        mTeamName = (TextView) findViewById(R.id.team_name_textView);
        mShortName = (TextView) findViewById(R.id.short_name_textView);
        mCode = (TextView) findViewById(R.id.team_code_textView);
        mSquadMV = (TextView) findViewById(R.id.squad_market_value);
        displayImage(item.getCrestUrl(),mBigCrest,this);



        mTeamName.setText("Team:    "+item.getTeamName());
        mShortName.setText("Short:  "+item.getTeamShortName());
        mCode.setText("Code:    "+item.getTeamCode());
        mCompetitionName = getIntent().getStringExtra("ActionBarTitle");


        if(item.getSquadMarketValue().equals("null"))
            mSquadMV.setText("");
        else
            mSquadMV.setText("Squad Value:  "+item.getSquadMarketValue());
     }

    private void actionBarSetup(String title,String subtitle) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setSubtitle(subtitle);

    }

}
