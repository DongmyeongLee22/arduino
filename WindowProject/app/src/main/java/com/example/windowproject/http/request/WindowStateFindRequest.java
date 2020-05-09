package com.example.windowproject.http.request;

import android.os.AsyncTask;

import com.example.windowproject.MainActivity;
import com.example.windowproject.common.Profile;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WindowStateFindRequest extends AsyncTask<Void, Void, String> {

    private MainActivity mainActivity;

    public WindowStateFindRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(Profile.BASE_URL + "/window");
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

    @Override
    public void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            mainActivity.setWindowState(jsonObject.getString("windowMessage"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
