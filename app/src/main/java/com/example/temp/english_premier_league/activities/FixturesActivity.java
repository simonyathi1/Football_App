package com.example.temp.english_premier_league.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.temp.english_premier_league.adapters.FixturesAdapter;
import com.example.temp.english_premier_league.R;
import com.example.temp.english_premier_league.model.Fixture;

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

public class FixturesActivity extends AppCompatActivity {



    private RecyclerView mTeamsRecycleView;
    private FixturesAdapter mFixturesAdapter;
    public ArrayList<Fixture> mFixtures;
    private String [] teamLinks, crestLinks;
    private String fixtureLink, competitionName, teamName;
    private String currentCompetition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        init();

        fixtureLink = getIntent().getStringExtra("Fixture_link");
        teamLinks = getIntent().getStringArrayExtra("teams");
        crestLinks = getIntent().getStringArrayExtra("crests");
        currentCompetition = getIntent().getStringExtra("currentCompetition");
        competitionName = getIntent().getStringExtra("ActionBarTitle");
        teamName = getIntent().getStringExtra("TeamName");
        actionBarSetup(teamName,"Fixtures");


        new FetchData().execute();


        final Intent intent = new Intent(FixturesActivity.this, SingleFixture.class);
        mFixturesAdapter = new FixturesAdapter(mFixtures, this,competitionName ,new FixturesAdapter.OnItemClickedListener() {

            @Override
            public void onItemClick(Fixture item) {


                String matchday = item.getMatchday();

                intent.putExtra("homeTeamName",item.getHomeTeamName());
                intent.putExtra("awayTeamName",item.getAwayTeamName());
                intent.putExtra("Status",item.getStatus());
                intent.putExtra("matchDate",item.getDate());
                intent.putExtra("homeCrest",item.getHomeTeamCrest());
                intent.putExtra("awayCrest",item.getAwayTeamCrest());
                intent.putExtra("links",item.getLinks());
                intent.putExtra("status",item.getLinks());
                intent.putExtra("fullTime",item.getFullTime());
                intent.putExtra("halfTime",item.getHalfTime());
                intent.putExtra("matchday",matchday);
                intent.putExtra("odds",item.getOdds());
                intent.putExtra("competitionName",competitionName);
                intent.putExtra("matchTitle",item.getHomeTeamName()+" VS "+item.getAwayTeamName());


                startActivity(intent);
            }
        });
        mTeamsRecycleView.setAdapter(mFixturesAdapter);
        //Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();

    }

    private void init() {

        mTeamsRecycleView = (RecyclerView) findViewById(R.id.fixtures_recycler_view);
        mTeamsRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mTeamsRecycleView.setHasFixedSize(true);
        mFixtures = new ArrayList<>();


    }
    private void actionBarSetup(String title,String subtitle) {
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(title);
        ab.setSubtitle(subtitle);

    }



    public class FetchData extends AsyncTask<Void, Void,Void>
    {
        private String mDataString;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(fixtureLink);
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

                JSONArray fixturesArray = jsonO.getJSONArray("fixtures");

                /*****************CHEAT CODE********************/

                /*****************CHEAT CODE********************/


                for (int i = 0; i < fixturesArray.length(); i++)
                {
                    Log.v("Success__",i+"");
                    String [] links = new String[4];
                    String HomeTeamName;
                    String AwayTeamName;
                    String date;
                    String status;
                    String matchday;
                    String awayTeamCrest;
                    String homeTeamCrest;
                    String [] fullTime = new String[2];
                    String [] halfTime = new String [2];
                    String odds;

                    JSONObject jFixture = (JSONObject)fixturesArray.get(i);
                    JSONObject jLinks = jFixture.getJSONObject("_links");
                    JSONObject jResults = jFixture.getJSONObject("result");
                    if(jResults.has("halfTime")) {
                        JSONObject jHalfTime = jResults.getJSONObject("halfTime");
                        halfTime[0] = jHalfTime.getString("goalsHomeTeam");
                        halfTime[1] = jHalfTime.getString("goalsAwayTeam");
                    }else {
                        halfTime[0]="__";
                        halfTime[1] = "__";
                    }
                    JSONObject jSelf = jLinks.getJSONObject("self");
                    JSONObject jCompetition = jLinks.getJSONObject("competition");
                    JSONObject jhomeTeam = jLinks.getJSONObject("homeTeam");
                    JSONObject jAwayTeam = jLinks.getJSONObject("awayTeam");

                    links[0] = jSelf.getString("href");
                    links[1] = jCompetition.getString("href");
                    links[2] = jhomeTeam.getString("href");
                    links[3] = jAwayTeam.getString("href");

                    if (jResults.getString("goalsHomeTeam").equals("null"))
                        fullTime[0] = "__";
                    else
                        fullTime[0] = jResults.getString("goalsHomeTeam");

                    if (jResults.getString("goalsAwayTeam").equals("null"))
                        fullTime[1] = "__";
                    else
                    fullTime[1] = jResults.getString("goalsAwayTeam");



                    HomeTeamName = jFixture.getString("homeTeamName");
                    AwayTeamName = jFixture.getString("awayTeamName");
                    date = jFixture.getString("date");
                    status = jFixture.getString("status");
                    matchday = jFixture.getString("matchday");

                    homeTeamCrest = "";
                    awayTeamCrest = "";
                    for(int j = 0; j < teamLinks.length; j++)
                    {
                        if (teamLinks[j].equals(links[2]))
                        {
                            homeTeamCrest = crestLinks[j];
                            break;
                        }
                    }
                    for(int j = 0; j < teamLinks.length; j++)
                    {
                        if (teamLinks[j].equals(links[3]))
                        {
                            awayTeamCrest = crestLinks[j];
                        }
                    }

                    //homeTeamCrest = "https://upload.wikimedia.org/wikipedia/en/0/02/Burnley_FC_badge.png";
                    odds = jFixture.getString("odds");




                    Fixture fixture = new Fixture();

                    fixture.setLinks(links);
                    fixture.setHomeTeamName(HomeTeamName);
                    fixture.setAwayTeamName(AwayTeamName);
                    fixture.setHomeTeamCrest(homeTeamCrest);
                    fixture.setAwayTeamCrest(awayTeamCrest);
                    fixture.setDate(date);
                    fixture.setStatus(status);
                    fixture.setMatchday(matchday);
                    fixture.setHalfTime(halfTime);
                    fixture.setFullTime(fullTime);
                    fixture.setOdds(odds);

                    if(currentCompetition.equals(links[1]))
                        mFixtures.add(fixture);

                }


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
            mFixturesAdapter.notifyDataSetChanged();
        }
    }

}
