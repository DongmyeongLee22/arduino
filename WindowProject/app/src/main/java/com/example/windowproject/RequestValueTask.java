package com.example.windowproject;

import com.example.windowproject.http.request.MeasureValueFindRequest;

import java.util.TimerTask;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestValueTask extends TimerTask {

    private MainActivity activity;

    @Override
    public void run() {
        new MeasureValueFindRequest(activity).execute();
    }
}
