package com.example.temp.english_premier_league.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Player;

import java.util.ArrayList;

import static com.example.temp.english_premier_league.Controller.positionCoder;

/**
 * Created by temp on 30/08/2017.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerHolder> {



    public interface OnItemClickedListener
    {
        void onItemClick(Player item, int a);
    }

    private final OnItemClickedListener listener;
    private ArrayList<Player> playerData;
    private Activity player_activity;



    public PlayersAdapter( ArrayList<Player> playerData, Activity player_activity,OnItemClickedListener listener) {
        this.listener = listener;
        this.playerData = playerData;
        this.player_activity = player_activity;
    }

    @Override
    public PlayersAdapter.PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item,parent,false);

        return new PlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayersAdapter.PlayerHolder holder, int position) {
        if(position < playerData.size())
            holder.bind(playerData.get(position),listener);
    }

    @Override
    public int getItemCount() {
        if(playerData == null)
            return 0;
        return playerData.size();
    }

    public class PlayerHolder extends RecyclerView.ViewHolder{


        TextView playerposition;
        TextView playerName;
        TextView playerNumber;

        public PlayerHolder(View itemView) {
            super(itemView);

            playerposition = (TextView)itemView.findViewById(R.id.player_postition_textview);
            playerName = (TextView)itemView.findViewById(R.id.player_name_textView);
            playerNumber = (TextView)itemView.findViewById(R.id.player_number_textView);


        }


        //new binder method to bind data to the view
        public void bind(final Player item, final PlayersAdapter.OnItemClickedListener listener) {


            playerName.setText(item.getPlayerName());
            playerNumber.setText(item.getJerseyNumber());
            playerposition.setText(positionCoder(item.getPosition()));


            itemView.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {

                    listener.onItemClick(item,playerData.indexOf(item));

                }

            });

        }
    }

}
