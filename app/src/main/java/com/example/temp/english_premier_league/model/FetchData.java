package com.example.temp.english_premier_league.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by temp on 05/09/2017.
 */

public class FetchData extends AsyncTask<Void, Void,Void>
{
    private String mDataString;
    private String mData;
    private RecyclerView.Adapter fetchAdapter;
    JSONArray jArr;

    public FetchData(String link, RecyclerView.Adapter adapter)
    {
        mDataString =link;
        fetchAdapter = adapter;
    }
    private JSONObject jsonO;


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
            urlConnection.setRequestProperty("X-Auth-Token",mDataString);
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

            jArr = new JSONArray(mData);
            Log.v("Response",jsonO.toString());



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

    public JSONObject getJasonData()
    {
        return jsonO;
    }
    public JSONArray getJasonArray() throws JSONException {
        return jArr;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        fetchAdapter.notifyDataSetChanged();
    }
}
