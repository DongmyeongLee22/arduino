package com.example.windowproject.http.request;

import android.os.AsyncTask;

import com.example.windowproject.Item;
import com.example.windowproject.common.Profile;
import com.example.windowproject.common.StringUtils;
import com.example.windowproject.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.Getter;

import static com.example.windowproject.common.StringUtils.*;

@Getter
public class MemberConfigRequest extends AsyncTask<Void, Void, String> {

    private String name;
    private String urlStr;
    private User user;

    public MemberConfigRequest(String name) {
        this.name = name;
    }

    @Override
    protected void onPreExecute() {
        try {
            urlStr = Profile.BASE_URL + "/member?name=" + name;
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

    @Override
    public void onPostExecute(String result) {
        try {

            System.out.println("================" + result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            JSONObject object = jsonArray.getJSONObject(0);

            user = User.builder()
                    .name(object.getString("name"))
                    .checkedTemp(transToBoolean(object.getString("checkedTemp")))
                    .closeWindowTemp(transToNumber(object.getString("closeWindowTemp")))
                    .openWindowTemp(transToNumber(object.getString("openWindowTemp")))
                    .checkedHumidity(transToBoolean(object.getString("checkedHumidity")))
                    .closeWindowHumidity(transToNumber(object.getString("closeWindowHumidity")))
                    .openWindowHumidity(transToNumber(object.getString("openWindowHumidity")))
                    .checkedFineDust(transToBoolean(object.getString("checkedFineDust")))
                    .closeWindowFineDust(transToNumber(object.getString("closeWindowFineDust")))
                    .openWindowFineDust(transToNumber(object.getString("openWindowFineDust")))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
