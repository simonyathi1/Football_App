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

import com.example.temp.english_premier_league.adapters.CompetitionsAdapter;
import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Competition;

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

public class CompetitionActivity extends AppCompatActivity {

    private RecyclerView mCompetitionsRecycleView;
    private CompetitionsAdapter mCompetitionsAdapter;
    public ArrayList<Competition> mCompetitions;
    private String mDataString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        initializer();
        actionBarSetup("ABSA Football App","Competitions");
        new FetchData().execute();

        final Intent intent_to_main = new Intent(CompetitionActivity.this,MainActivity.class);



        mCompetitionsAdapter = new CompetitionsAdapter(mCompetitions, this, new CompetitionsAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(Competition item) {
                Toast.makeText(CompetitionActivity.this, item.getLeague(), Toast.LENGTH_SHORT).show();
                String teamsLink = item.getLinks()[1];
                String currentComp = item.getLinks()[0];
                String noOfTeams = item.getNumberOfTeams();
                intent_to_main.putExtra("teams_api_link",teamsLink);
                intent_to_main.putExtra("numberOfTeams",noOfTeams );
                intent_to_main.putExtra("currentCompetition",currentComp);
                intent_to_main.putExtra("ActionBarTitle",item.getLeague());
                startActivity(intent_to_main);
            }
        });

        mCompetitionsRecycleView.setAdapter(mCompetitionsAdapter);
    }


    public void initializer()
    {
        mCompetitionsRecycleView = (RecyclerView) findViewById(R.id.competition_recycleview);
        mCompetitionsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mCompetitionsRecycleView.setHasFixedSize(true);
        mCompetitions = new ArrayList<>();

        mDataString = getString(R.string.competition_api);

    }


    public class FetchData extends AsyncTask<Void, Void,Void>
    {

        private String mData;


        private JSONArray jsonO;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(mDataString);
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

                mData = buffer.toString();
                //jsonO = new JSONObject(mData);

                jsonO = new JSONArray(mData);
                Log.v("Response","+hgjhghg"+jsonO.toString());

                JSONArray jsonArray = (jsonO);

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    Log.v("Success__",i+"");

                    String[] links = new String[4];
                    String id;
                    String league;
                    String year;
                    String currentMatchDay;
                    String numberOfMatchDays;
                    String numberOfTeams;
                    String numberOfGames;
                    String lastUpdated;

                    JSONObject jCompetition = (JSONObject)jsonArray.get(i);
                    JSONObject jLinks = jCompetition.getJSONObject("_links");

                    JSONObject jSelf = jLinks.getJSONObject("self");
                    JSONObject jTeams = jLinks.getJSONObject("teams");
                    JSONObject jFixtures = jLinks.getJSONObject("fixtures");
                    JSONObject jLeagueTable = jLinks.getJSONObject("leagueTable");


                    id = jCompetition.getString("id");
                    league = jCompetition.getString("caption");
                    year = jCompetition.getString("year");
                    currentMatchDay = jCompetition.getString("currentMatchday");
                    numberOfMatchDays = jCompetition.getString("numberOfMatchdays");
                    numberOfTeams = jCompetition.getString("numberOfTeams");
                    numberOfGames = jCompetition.getString("numberOfGames");
                    lastUpdated = jCompetition.getString("lastUpdated");



                    links[0] = jSelf.getString("href");
                    links[1] = jTeams.getString("href");
                    links[2] = jFixtures.getString("href");
                    links[3] = jLeagueTable.getString("href");

                    //crestNames.put(links[0],crestUrl);


                    Competition competition = new Competition();
                    competition.setId(id);
                    competition.setLeague(league);
                    competition.setYear(year);
                    competition.setCurrentMatchDay(currentMatchDay);
                    competition.setNumberOfMatchDay(numberOfMatchDays);
                    competition.setNumberOfTeams(numberOfTeams);
                    competition.setNumberOfGames(numberOfGames);
                    competition.setLastUpdated(lastUpdated);
                    competition.setLinks(links);



                    mCompetitions.add(competition);
                }

                Collections.sort(mCompetitions);



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
            mCompetitionsAdapter.notifyDataSetChanged();
        }
    }

    private void actionBarSetup(String title,String subtitle) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle(title);
            ab.setSubtitle(subtitle);
    }


}
