package com.example.windowproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.windowproject.http.request.TwoSecondDateRequest;

import java.util.TimerTask;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DateTask extends TimerTask {

    private MainActivity activity;

    @Override
    public void run() {
        new TwoSecondDateRequest(activity).execute();
    }
}
