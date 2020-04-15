package com.example.windowproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.windowproject.activity.ConfigActivity;
import com.example.windowproject.domain.MeasureValue;
import com.example.windowproject.http.request.MeasureValueFindRequest;
import com.example.windowproject.http.request.MemberConfigFindRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button configBtn, refreshBtn;
    private Context context;
    private TextView temperature, humidity, fineDust, measureTime, isRain,timerTextView;
    private RadioGroup timer_group;
    private CountDownTimer countDownTimer;

    private boolean timerReady;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();
        temperature = (TextView) findViewById(R.id.temperature);
        humidity = (TextView) findViewById(R.id.humidity);
        fineDust = (TextView) findViewById(R.id.fineDust);
        measureTime = (TextView) findViewById(R.id.measureTime);
        isRain = (TextView) findViewById(R.id.isRain);
        configBtn = (Button) findViewById(R.id.configBtn);
        refreshBtn = (Button) findViewById(R.id.refreshBtn);
        timer_group = (RadioGroup) findViewById(R.id.timer_group);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timer_group = (RadioGroup) findViewById(R.id.timer_group);

        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    MemberConfigFindRequest memberConfigFindRequest = new MemberConfigFindRequest("이혜은");
                    String result = memberConfigFindRequest.execute().get();

                    Intent intent = new Intent(context, ConfigActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "다시 한 번더 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestValues();
            }
        });


        timer_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.timer_10){
                    if (timerReady) countDownTimer.cancel();
                    count = 10 * 60;
                    timerReady = true;
                    initTimer(10 * 60 * 1000);
                    countDownTimer.start();
                }else if(i == R.id.timer_20){
                    if (timerReady) countDownTimer.cancel();
                    count = 20 * 60;
                    timerReady = true;
                    initTimer(20 * 60 * 1000);
                    countDownTimer.start();
                }else{
                    timerReady = false;
                    countDownTimer.cancel();
                    timerTextView.setText("");
                }
            }
        });

        requestValues();
    }

    private void initTimer(int time){
        countDownTimer = new CountDownTimer( time, 1000) {
            public void onTick(long millisUntilFinished) {
                // 600 -> 540 -> 480 ->   599
                String second = String.valueOf((count % 60));
                String minute = String.valueOf((count / 60));
                timerTextView.setText(minute + ":" + second);
                count --;
            }
            public void onFinish() {
                timerTextView.setText("Finish");
            }
        };
    }

    private void requestValues() {
        new MeasureValueFindRequest(this).execute();
    }

    @SuppressLint("SetTextI18n")
    public void reLanding(List<MeasureValue> measureValues) {
        MeasureValue latestData = measureValues.get(0);
        temperature.setText(latestData.getTemperature() + "도");
        humidity.setText(latestData.getHumidity() +  "%");
        fineDust.setText(latestData.getFineDust() + "μm");
        measureTime.setText(latestData.getCreateDate());
        isRain.setText(latestData.isRain() ? "내리는 중" : "내리지 않음");
    }


}
