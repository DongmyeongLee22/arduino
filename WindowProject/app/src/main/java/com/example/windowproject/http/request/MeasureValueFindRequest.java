package com.example.windowproject.http.request;

import android.os.AsyncTask;

import com.example.windowproject.MainActivity;
import com.example.windowproject.common.Profile;
import com.example.windowproject.domain.MeasureValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MeasureValueFindRequest extends AsyncTask<Void, Void, String> {

    private MainActivity mainActivity;

    public MeasureValueFindRequest(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(Profile.BASE_URL + "/values/이혜은");
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
            JSONArray jsonArray = new JSONArray(result);
            List<MeasureValue> measureValues = build(jsonArray);
            mainActivity.reLanding(measureValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<MeasureValue> build(JSONArray jsonArray) throws JSONException {
        List<MeasureValue> measureValues = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            measureValues.add(build(jsonArray.getJSONObject(i)));
        }

        return measureValues;
    }

    private MeasureValue build(JSONObject jsonObject) throws JSONException {
        String createDate = jsonObject.getString("createDate");
        return MeasureValue.builder()
                .createDate(createDate.substring(0, 19))
                .temperature(jsonObject.getString("temperature"))
                .humidity(jsonObject.getString("humidity"))
                .fineDust(jsonObject.getString("fineDust"))
                .isRain(jsonObject.getBoolean("rain"))
                .build();
    }
}
