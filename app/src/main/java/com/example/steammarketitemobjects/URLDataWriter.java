package com.example.steammarketitemobjects;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLDataWriter extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        publishProgress();
        String data = null;
        for (String url : urls) {
            try {
                String urlData;
                URLConnection urlConnection = new URL(url).openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                urlData = in.readLine();
                data = urlData;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
    }
}
