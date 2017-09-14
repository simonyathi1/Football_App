package com.example.temp.english_premier_league.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.temp.english_premier_league.adapters.PlayersAdapter;
import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Player;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class PlayersActivity extends AppCompatActivity {
    private RecyclerView mTeamsRecycleView;
    private PlayersAdapter mPlayersAdapter;
    public ArrayList<Player> mPlayers;
    private String playerLink , crestLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        init();

        playerLink = getIntent().getStringExtra("Players_link");
        crestLink = getIntent().getStringExtra("teamCrest");
        actionBarSetup(getIntent().getStringExtra("TeamName"),"Players");
        new FetchData().execute();


        final Intent intent = new Intent(PlayersActivity.this, SinglePlayerActivity.class);
        mPlayersAdapter = new PlayersAdapter(mPlayers, this, new PlayersAdapter.OnItemClickedListener() {

            @Override
            public void onItemClick(Player item, int p) {
                Toast.makeText(PlayersActivity.this, item.getPlayerName(), Toast.LENGTH_SHORT).show();


                intent.putExtra("PlayerName",item.getPlayerName());
                intent.putExtra("Position",item.getPosition());
                intent.putExtra("JerseyNumber",item.getJerseyNumber());
                intent.putExtra("DOB",item.getDOB());
                intent.putExtra("Nationality",item.getNationality());
                intent.putExtra("Contract",item.getContractEnd());
                intent.putExtra("MarketValue",item.getMarketValue());
                intent.putExtra("teamCrest",crestLink);


                startActivity(intent);
            }
        });
        mTeamsRecycleView.setAdapter(mPlayersAdapter);
        //Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();

    }

    private void init() {

        mTeamsRecycleView = (RecyclerView) findViewById(R.id.players_recycleView);
        mTeamsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mTeamsRecycleView.setHasFixedSize(true);
        mPlayers = new ArrayList<>();

    }

    private void actionBarSetup(String title,String subtitle) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setSubtitle(subtitle);

    }



    public class FetchData extends AsyncTask<Void, Void,Void> {
        private String mDataString;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(playerLink);
            URL url;


            try
            {
                url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-Auth-Token",getString(R.string.api_key));
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null)
                {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null)
                {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0)
                {
                    return null;
                }

                mDataString = buffer.toString();
                JSONObject jsonO = new JSONObject(mDataString);

                Log.v("Response",jsonO.toString());

                JSONArray playersArray = jsonO.getJSONArray("players");

                for (int i = 0; i < playersArray.length(); i++)
                {
                    Log.v("Success__",i+"");
                     String playerName;
                     String position;
                     String jerseyNumber;
                     String DOB;
                     String nationality;
                     String contractEnd;
                     String marketValue;


                    JSONObject jPlayer = (JSONObject)playersArray.get(i);


                    playerName = jPlayer.getString("name");
                    position = jPlayer.getString("position");
                    jerseyNumber = jPlayer.getString("jerseyNumber");
                    DOB = jPlayer.getString("dateOfBirth");
                    nationality = jPlayer.getString("nationality");
                    contractEnd = jPlayer.getString("contractUntil");
                    marketValue = jPlayer.getString("marketValue");
                    if(marketValue.equals("null"))
                        marketValue="__";
                    if(jerseyNumber.equals("null"))
                        jerseyNumber="00";





                    Player player = new Player();

                    player.setPlayerName(playerName);
                    player.setPosition(position);
                    player.setJerseyNumber(jerseyNumber);
                    player.setDOB(DOB);
                    player.setNationality(nationality);
                    player.setContractEnd(contractEnd);
                    player.setMarketValue(marketValue);

                    mPlayers.add(player);
                }
                Collections.sort(mPlayers);


            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(urlConnection != null)
                {
                    urlConnection.disconnect();
                }
                if(reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch(final IOException e)
                    {
                        Log.e("MainActivity","Error closing stream", e);
                    }
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            mPlayersAdapter.notifyDataSetChanged();
        }
    }

}
