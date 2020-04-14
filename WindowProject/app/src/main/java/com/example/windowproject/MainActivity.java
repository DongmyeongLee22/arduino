package com.example.windowproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Item> itemList;
    private ListView itemListView;
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemListView = findViewById(R.id.itemListView);
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this.getApplicationContext(), itemList);
        itemListView.setAdapter(itemAdapter);
        itemRequest();
    }

    class ItemRequest extends AsyncTask<Void, Void, String> {

        private String my_url;

        @Override
        protected void onPreExecute() {
            try {
                my_url = "http://lehee0430.dothome.co.kr/iot.php";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(my_url);
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
                itemList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                JSONObject object = jsonArray.getJSONObject(0);
                String temperature, airQuality, fineDust, windowState, pdlcState;

                temperature = object.getString("temperature");
                airQuality = object.getString("airQuality");
                fineDust = object.getString("fineDust");
                windowState = object.getString("windowState");
                pdlcState = object.getString("pdlcState");

                Item item = new Item(temperature, airQuality, fineDust, windowState, pdlcState);
                itemList.add(item);

                itemAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void itemRequest(){
        new ItemRequest().execute();
    }
}
