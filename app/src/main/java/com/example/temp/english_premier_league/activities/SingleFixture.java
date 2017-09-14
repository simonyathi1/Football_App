package com.example.temp.english_premier_league.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.temp.english_premier_league.R;
import java.util.ArrayList;
import static com.example.temp.english_premier_league.Controller.displayImage;
import static com.example.temp.english_premier_league.Controller.splitter;

//Suffix your activity name with Activity
public class SingleFixture extends AppCompatActivity {

    private ImageView homeTeamCrestImageView;
    private ImageView awayTeamCrestImageView;
    private TextView homeTeamNameTextView;
    private TextView awayTeamNameTextView;
    private TextView homeScoreTextView;
    private TextView awayScoreTextView;
    private TextView competitionNameTextView;
    private TextView matchDateTextView;
    private TextView haltimeHomeScore;
    private TextView halfTimeAway ;
    private TextView matchDay;
    private TextView odds;
    private TextView matchTime;
    private ArrayList<String> dateTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fixture);
        dateTime = splitter(getIntent().getStringExtra("matchDate"));


        homeTeamCrestImageView = (ImageView) findViewById(R.id.home_team_crest);
        awayTeamCrestImageView = (ImageView) findViewById(R.id.away_team_crest);
        homeTeamNameTextView = (TextView) findViewById(R.id.home_team_name);
        awayTeamNameTextView = (TextView) findViewById(R.id.away_team_name);
        homeScoreTextView = (TextView) findViewById(R.id.home_team_score);
        awayScoreTextView = (TextView) findViewById(R.id.away_team_score);
        competitionNameTextView = (TextView) findViewById(R.id.competition_id_BIG);
        matchDateTextView = (TextView) findViewById(R.id.match_date);
        matchTime = (TextView) findViewById(R.id.match_date_time);
        haltimeHomeScore = (TextView) findViewById(R.id.half_home_team_score);
        halfTimeAway = (TextView) findViewById(R.id.half_away_team_score);
        matchDay = (TextView) findViewById(R.id.match_day);
        odds = (TextView) findViewById(R.id.odds);
        actionBarSetup(getIntent().getStringExtra("matchTitle"),dateTime.get(0));

        homeTeamNameTextView.setText(getIntent().getStringExtra("homeTeamName"));
        awayTeamNameTextView.setText(getIntent().getStringExtra("awayTeamName"));

        if (getIntent().getStringArrayExtra("fullTime").equals("null")) {
            homeScoreTextView.setText("__");
        } else
            {
                homeScoreTextView.setText(getIntent().getStringArrayExtra("fullTime")[0]);
            }

        if (getIntent().getStringArrayExtra("fullTime").equals("null"))
        {
            homeScoreTextView.setText("__");
        } else
            {
                awayScoreTextView.setText(getIntent().getStringArrayExtra("fullTime")[1]);
            }
        competitionNameTextView.setText(getIntent().getStringExtra("competitionName"));
        matchDateTextView.setText(dateTime.get(0));


        matchTime.setText(dateTime.get(1));

        haltimeHomeScore.setText(getIntent().getStringArrayExtra("halfTime")[0]);
        halfTimeAway.setText(getIntent().getStringArrayExtra("halfTime")[1]);
        matchDay.setText("Match Day "+ getIntent().getStringExtra("matchday"));

        if(getIntent().getStringExtra("odds").equals("null"))
        {
            odds.setText("");

        }
        else
            {
            odds.setText("Odds: " + getIntent().getStringExtra("odds"));
            }

        displayImage(getIntent().getStringExtra("homeCrest"),homeTeamCrestImageView,this);

        displayImage(getIntent().getStringExtra("awayCrest"),awayTeamCrestImageView,this);


    }
    private void actionBarSetup(String title,String subtitle) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setSubtitle(subtitle);

    }


}
