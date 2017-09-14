package com.example.temp.english_premier_league.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.widget.Toast;

import com.example.temp.english_premier_league.adapters.TeamsAdapter;
import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mTeamsRecycleView;
    private TeamsAdapter mTeamsAdapter;
    public ArrayList<Team> mTeams;
    private String [] crestLinks , teamlinks ;
    private String teams_api,activityActionBarTitle;
    private int numOfTeams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        teams_api = getIntent().getStringExtra("teams_api_link");
        numOfTeams = Integer.parseInt(getIntent().getStringExtra("numberOfTeams"));
        activityActionBarTitle = getIntent().getStringExtra("ActionBarTitle");
        actionBarSetup(activityActionBarTitle,"Teams");


        crestLinks = new String[numOfTeams];
        teamlinks = new String[numOfTeams];

        new FetchData().execute();

        final Intent intent = new Intent(MainActivity.this, TeamActivity.class);

        mTeamsAdapter = new TeamsAdapter(mTeams, MainActivity.this, new TeamsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( Team team) {
                Toast.makeText(MainActivity.this, team.getTeamName(), Toast.LENGTH_SHORT).show();

                intent.putExtra("TeamName",team.getTeamName());
                intent.putExtra("ShortName",team.getTeamShortName());
                intent.putExtra("Code",team.getTeamCode());
                intent.putExtra("SquadValue",team.getSquadMarketValue());
                intent.putExtra("Crest",team.getCrestUrl());
                intent.putExtra("links",team.getLinks());
                intent.putExtra("crests",crestLinks);
                intent.putExtra("teams",teamlinks);
                intent.putExtra("currentCompetition",getIntent().getStringExtra("currentCompetition"));
                intent.putExtra("ActionBarTitle",activityActionBarTitle);

                startActivity(intent);
            }
        });
        mTeamsRecycleView.setAdapter(mTeamsAdapter);


    }

    private void actionBarSetup(String title,String subtitle) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setSubtitle(subtitle);

    }

    private void init() {

        mTeamsRecycleView = (RecyclerView) findViewById(R.id.teams_recycleview);
        mTeamsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mTeamsRecycleView.setHasFixedSize(true);
        mTeams = new ArrayList<>();


    }




    public class FetchData extends AsyncTask<Void, Void,Void>
    {
        private String mDataString;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(teams_api);
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

                JSONArray teamsArray = jsonO.getJSONArray("teams");

                for (int i = 0; i < teamsArray.length(); i++)
                {
                    Log.v("Success__",i+"");
                    String [] links = new String[3];
                    String teamName;
                    String teamShortName;
                    String squadMarketValue;
                    String crestUrl;
                    String teamCode;

                    JSONObject jTeam = (JSONObject)teamsArray.get(i);
                    JSONObject jLinks = jTeam.getJSONObject("_links");

                    JSONObject jSelf = jLinks.getJSONObject("self");
                    JSONObject jFixtures = jLinks.getJSONObject("fixtures");
                    JSONObject jPlayers = jLinks.getJSONObject("players");


                    teamName = jTeam.getString("name");
                    teamCode = jTeam.getString("code");
                    teamShortName = jTeam.getString("shortName");
                    squadMarketValue = jTeam.getString("squadMarketValue");
                    crestUrl = jTeam.getString("crestUrl");

                    links[0] = jSelf.getString("href");
                    links[1] = jFixtures.getString("href");
                    links[2] = jPlayers.getString("href");

                    //crestNames.put(links[0],crestUrl);

                    teamlinks[i]= links[0];
                    crestLinks[i]=crestUrl;

                    Team team = new Team();

                    team.setTeamName(teamName);
                    team.setTeamShortName(teamShortName);
                    team.setTeamCode(teamCode);
                    team.setLinks(links);
                    team.setSquadMarketValue(squadMarketValue);
                    team.setCrestUrl(crestUrl);

                    mTeams.add(team);
                }
                Collections.sort(mTeams);

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
            mTeamsAdapter.notifyDataSetChanged();
        }
    }



}
