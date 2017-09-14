package com.example.temp.english_premier_league.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Fixture;

import java.util.ArrayList;

import static com.example.temp.english_premier_league.Controller.displayImage;
import static com.example.temp.english_premier_league.Controller.hasImage;
import static com.example.temp.english_premier_league.Controller.splitter;

/**
 * Created by temp on 30/08/2017.
 */

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.FixtureHolder> {



    public interface OnItemClickedListener
    {
        void onItemClick(Fixture item);
    }

    private final OnItemClickedListener listener;
    private ArrayList<Fixture> fixtureData;
    private Activity fixture_activity;
    private String competitionName;

    public FixturesAdapter( ArrayList<Fixture> fixtureData, Activity fixture_activity,String competitionName,OnItemClickedListener listener) {
        this.listener = listener;
        this.fixtureData = fixtureData;
        this.fixture_activity = fixture_activity;
        this.competitionName =competitionName;
    }

    @Override
    public FixturesAdapter.FixtureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_item,parent,false);

        return new FixtureHolder(view);
    }

    @Override
    public void onBindViewHolder(FixturesAdapter.FixtureHolder holder, int position) {
        if(position < fixtureData.size())
            holder.bind(fixtureData.get(position),listener);
    }

    @Override
    public int getItemCount() {
        if(fixtureData == null)
            return 0;
        return fixtureData.size();
    }

    public class FixtureHolder extends RecyclerView.ViewHolder{

        ImageView homeTeamCrestImageView;
        ImageView awayTeamCrestImageView;
        TextView homeTeamNameTextView;
        TextView awayTeamNameTextView;
        TextView homeScoreTextView;
        TextView awayScoreTextView;
        TextView competitionNameTextView;
        TextView matchDateTextView;
        TextView matchTimeTextView;

        public FixtureHolder(View itemView) {
            super(itemView);

            homeTeamCrestImageView = (ImageView)itemView.findViewById(R.id.home_team_crest);
            awayTeamCrestImageView = (ImageView)itemView.findViewById(R.id.away_team_crest);
            homeTeamNameTextView = (TextView)itemView.findViewById(R.id.home_team_name);
            awayTeamNameTextView = (TextView)itemView.findViewById(R.id.away_team_name);
            homeScoreTextView = (TextView)itemView.findViewById(R.id.home_team_score);
            awayScoreTextView = (TextView)itemView.findViewById(R.id.away_team_score);
            competitionNameTextView = (TextView)itemView.findViewById(R.id.competition_id);
            matchDateTextView = (TextView)itemView.findViewById(R.id.match_date_small);
            matchTimeTextView = (TextView)itemView.findViewById(R.id.match_date_time_small);


        }
   //new binder method to bind data to the view
        public void bind(final Fixture item, final FixturesAdapter.OnItemClickedListener listener) {
            ArrayList<String> dateTime = new ArrayList();
            homeTeamNameTextView.setText(item.getHomeTeamName());
            awayTeamNameTextView.setText(item.getAwayTeamName());

            homeScoreTextView.setText(item.getFullTime()[0]);
            awayScoreTextView.setText(item.getFullTime()[1]);
            competitionNameTextView.setText(competitionName);

            dateTime = splitter(item.getDate());
            matchDateTextView.setText(dateTime.get(0));
            matchTimeTextView.setText(dateTime.get(1));

            displayImage(item.getHomeTeamCrest(),homeTeamCrestImageView,fixture_activity);
            displayImage(item.getAwayTeamCrest(),awayTeamCrestImageView,fixture_activity);

            if(hasImage(homeTeamCrestImageView)==false)
            {
                Glide.with(fixture_activity).load(Uri.parse(item.getHomeTeamCrest())).into(homeTeamCrestImageView);
            }
            if(hasImage(awayTeamCrestImageView)==false)
            {
                Glide.with(fixture_activity).load(Uri.parse(item.getAwayTeamCrest())).into(awayTeamCrestImageView);
            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(item);

                }

            });

        }

    }


}
