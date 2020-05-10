package com.example.windowproject.task;

import com.example.windowproject.MainActivity;
import com.example.windowproject.http.request.MeasureValueFindRequest;

import java.util.TimerTask;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MeasureValueTask extends TimerTask {

    private MainActivity activity;

    @Override
    public void run() {
        new MeasureValueFindRequest(activity).execute();
    }
}
