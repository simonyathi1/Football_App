package com.example.temp.english_premier_league.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Team;
import java.util.ArrayList;
import static com.example.temp.english_premier_league.Controller.displayImage;
import static com.example.temp.english_premier_league.Controller.hasImage;

/**
 * Created by temp on 28/08/2017.
 */

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamHolder> {

    //interface implemented by the Activity creating an event
    public interface OnItemClickListener {

        void onItemClick(Team team);

    }

    private final OnItemClickListener listener;
    private ArrayList<Team> teamData;
    private Activity teams_Activity;

    public TeamsAdapter(ArrayList<Team> teamData, Activity teamActivity, OnItemClickListener listener) {
        this.teamData = teamData;
        this.teams_Activity = teamActivity;
        this.listener = listener;
    }

    @Override
    public TeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_item,parent,false);

        return new TeamHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamHolder holder, int position) {

        holder.bind(teamData.get(position), listener);

    }

    @Override
    public int getItemCount() {
        if(teamData == null)
            return 0;
        return teamData.size();
    }



    public class TeamHolder extends RecyclerView.ViewHolder{

        ImageView teamCrestImageView;
        TextView teamNameTextView;

        public TeamHolder(View itemView) {
            super(itemView);

            teamCrestImageView = (ImageView)itemView.findViewById(R.id.teams_list_crest);
            teamNameTextView = (TextView)itemView.findViewById(R.id.teams_list_name);


        }

        //new binder method to bind data to the view
        public void bind(final Team item, final OnItemClickListener listener) {

            String crestURL = item.getCrestUrl();

            displayImage(crestURL,teamCrestImageView,teams_Activity);
            if(!hasImage(teamCrestImageView))
            {
                Glide.with(teams_Activity).load(Uri.parse(crestURL)).into(teamCrestImageView);
            }

            teamNameTextView.setText(item.getTeamName());

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(item);

                }

            });

        }


    }


}
