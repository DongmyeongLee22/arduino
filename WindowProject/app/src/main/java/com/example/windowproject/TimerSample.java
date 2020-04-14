package com.example.windowproject;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TimerSample extends AppCompatActivity {

    private static final int MILLISINFUTURE = 10 * 60 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    private int count = 10 * 60;
    private TextView countTxt ;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_sample);

        countTxt = (TextView)findViewById(R.id.count_txt);
        countDownTimer();
        countDownTimer.start();
    }

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                // 600 -> 540 -> 480 ->   599
                String second = String.valueOf((count % 60));
                String minute = String.valueOf((count / 60));
                countTxt.setText(minute + ":" + second);
                count --;
            }
            public void onFinish() {
                countTxt.setText(String.valueOf("Finish ."));
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
            countDownTimer.cancel();
        } catch (Exception e) {}
        countDownTimer=null;
    }

}
