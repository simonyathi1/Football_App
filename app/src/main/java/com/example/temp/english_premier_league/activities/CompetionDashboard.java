package com.example.temp.english_premier_league.activities;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.temp.english_premier_league.R;

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

public class CompetionDashboard extends AppCompatActivity {

    private String competitionfixtures;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competion_dashboard);

        competitionfixtures = getIntent().getStringExtra("competitionFixture");
    }

    public class FetchData extends AsyncTask<Void, Void,Void>
    {

        private String mData;


        private JSONObject jsonO;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Uri builtUri = Uri.parse(competitionfixtures);
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
                jsonO = new JSONObject(mData);
                Log.v("Response","+hgjhghg"+jsonO.toString());

                JSONArray jCompetitionsArray = jsonO.getJSONArray("fixtures");

                for (int i = 0; i < jCompetitionsArray.length(); i++)
                {
                    Log.v("Success__",i+"");

                    String homelink,awayLink,homeScore,awayScore;

                    ArrayList<Integer> teamPoints;
                    ArrayList<Integer> goalsFor;
                    ArrayList<Integer> goalsAgainst;


                    JSONObject Result = (JSONObject)jCompetitionsArray.get(i);
                    JSONObject jLinks = Result.getJSONObject("_links");

                    JSONObject jSelf = jLinks.getJSONObject("self");
                    JSONObject jCompetition = jLinks.getJSONObject("competition");
                    JSONObject jHometeam = jLinks.getJSONObject("homeTeam");
                    JSONObject jAwayteam = jLinks.getJSONObject("awayTeam");


//                    id = jCompetition.getString("id");
//                    league = jCompetition.getString("caption");
//                    year = jCompetition.getString("year");
//                    currentMatchDay = jCompetition.getString("currentMatchday");
//                    numberOfMatchDays = jCompetition.getString("numberOfMatchdays");
//                    numberOfTeams = jCompetition.getString("numberOfTeams");
//                    numberOfGames = jCompetition.getString("numberOfGames");
//                    lastUpdated = jCompetition.getString("lastUpdated");
//
//
//
//                    links[0] = jSelf.getString("href");
//                    links[1] = jTeams.getString("href");
//                    links[2] = jFixtures.getString("href");
//                    links[3] = jLeagueTable.getString("href");
//
//                    //crestNames.put(links[0],crestUrl);
//
//
//                    Competition competition = new Competition();
//                    competition.setId(id);
//                    competition.setLeague(league);
//                    competition.setYear(year);
//                    competition.setCurrentMatchDay(currentMatchDay);
//                    competition.setNumberOfMatchDay(numberOfMatchDays);
//                    competition.setNumberOfTeams(numberOfTeams);
//                    competition.setNumberOfGames(numberOfGames);
//                    competition.setLastUpdated(lastUpdated);
//                    competition.setLinks(links);
//
//
//
//                    mCompetitions.add(competition);
                }

//                Competitionollections.sort(mCompetitions);



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



       // @Override
        //protected void onPostExecute(Void aVoid)
       // {
       //     mCompetitionsAdapter.notifyDataSetChanged();
        //}
    }
}
