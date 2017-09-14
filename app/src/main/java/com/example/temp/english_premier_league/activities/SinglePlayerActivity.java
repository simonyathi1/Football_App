package com.example.temp.english_premier_league.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.temp.english_premier_league.R;
import static com.example.temp.english_premier_league.Controller.displayImage;
import static com.example.temp.english_premier_league.Controller.positionCoder;

public class SinglePlayerActivity extends AppCompatActivity {



    private TextView mPlayerName, mPlayerNumber,mPlayerPosition,mPlayerDOB,mPlayerContactEnd, mPlayerNationality,mplayerMarketValue,mPlayerPositionCode;
    private ImageView mPlayerTeamCrest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        mPlayerName = (TextView)findViewById(R.id.single_player_name_BIG);
        mPlayerNumber = (TextView)findViewById(R.id.single_player_number_BIG);
        mPlayerPosition = (TextView)findViewById(R.id.single_player_position);
        mPlayerDOB = (TextView)findViewById(R.id.single_player_DOB_textView);
        mPlayerContactEnd = (TextView)findViewById(R.id.single_player_ContractUntil_textView);
        mPlayerNationality = (TextView)findViewById(R.id.single_player_nationality_textView);
        mplayerMarketValue = (TextView)findViewById(R.id.single_player_market_value);
        mPlayerPositionCode = (TextView)findViewById(R.id.single_player_postition_Code);
        mPlayerTeamCrest =(ImageView)findViewById(R.id.single_player_big_crest);

        mPlayerName.setText(getIntent().getStringExtra("PlayerName"));
        mPlayerNumber.setText(getIntent().getStringExtra("JerseyNumber"));
        mPlayerPosition.setText(getIntent().getStringExtra("Position"));
        mPlayerDOB.setText(getIntent().getStringExtra("DOB"));
        mPlayerContactEnd.setText(getIntent().getStringExtra("Contract"));
        mPlayerNationality.setText(getIntent().getStringExtra("Nationality"));
        mplayerMarketValue.setText(getIntent().getStringExtra("MarketValue"));

        mPlayerPositionCode.setText(positionCoder(getIntent().getStringExtra("Position")));
        actionBarSetup("Player",getIntent().getStringExtra("JerseyNumber"));

        displayImage(getIntent().getStringExtra("teamCrest"),mPlayerTeamCrest,this);


    }

    private void actionBarSetup(String title,String subtitle) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setSubtitle(subtitle);

    }

}
