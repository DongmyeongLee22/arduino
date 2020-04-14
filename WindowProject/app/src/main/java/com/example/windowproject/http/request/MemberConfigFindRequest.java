package com.example.windowproject.http.request;

import android.os.AsyncTask;

import com.example.windowproject.common.Profile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.Getter;

@Getter
public class MemberConfigFindRequest extends AsyncTask<Void, Void, String> {

    private String name;
    private String urlStr;

    public MemberConfigFindRequest(String name) {
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        try {
            urlStr = Profile.BASE_URL + "/user?name=" + name;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(urlStr);
            System.out.println("urlStr = " + urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp + "\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate();
    }

}
