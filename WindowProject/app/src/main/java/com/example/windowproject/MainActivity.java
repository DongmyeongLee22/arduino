package com.example.windowproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windowproject.activity.ConfigActivity;
import com.example.windowproject.domain.MeasureValue;
import com.example.windowproject.domain.WindowState;
import com.example.windowproject.http.request.MemberConfigFindRequest;
import com.example.windowproject.http.request.WindowSetRequest;
import com.example.windowproject.task.MeasureValueTask;
import com.example.windowproject.task.WindowStateTask;

import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.windowproject.domain.WindowState.CLOSE;
import static com.example.windowproject.domain.WindowState.CLOSING;
import static com.example.windowproject.domain.WindowState.OPEN;
import static com.example.windowproject.domain.WindowState.OPENING;

public class MainActivity extends AppCompatActivity {

    private Button configBtn, windowButton;
    private Context context;
    private TextView temperature, humidity, fineDust, measureTime, isRain, timerTextView, windowText;
    private RadioGroup timer_group;
    private CountDownTimer countDownTimer;
    private TimerTask windowStateTask, measureValueTask;

    private boolean timerReady;
    private int count;

    private Timer timer;

    @Override
    protected void onStart() {
        windowStateTask = new WindowStateTask(this);
        measureValueTask = new MeasureValueTask(this);
        doLoop(windowStateTask, 3000L);
        doLoop(measureValueTask, 10000L);
        super.onStart();
    }

    @Override
    protected void onStop() {
        windowStateTask.cancel();
        measureValueTask.cancel();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();

        context = this.getApplicationContext();
        temperature = (TextView) findViewById(R.id.temperature);
        humidity = (TextView) findViewById(R.id.humidity);
        fineDust = (TextView) findViewById(R.id.fineDust);
        measureTime = (TextView) findViewById(R.id.measureTime);
        isRain = (TextView) findViewById(R.id.isRain);
        configBtn = (Button) findViewById(R.id.configBtn);
        timer_group = (RadioGroup) findViewById(R.id.timer_group);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timer_group = (RadioGroup) findViewById(R.id.timer_group);

        windowText = (TextView) findViewById(R.id.window_text);
        windowButton = (Button) findViewById(R.id.window_button);

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

        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WindowState currentWindowState = WindowState.findBy(windowText.getText().toString());
                    String result = new WindowSetRequest(currentWindowState).execute().get();
                    JSONObject jsonObject = new JSONObject(result);
                    setWindowState(jsonObject.getString("windowMessage"));
                } catch (Exception e) {
                    Toast.makeText(context, "다시 한 번더 시도해주세요", Toast.LENGTH_SHORT).show();
                }
            }

            private String parse(String prevState) {
                if ("창문 열림".equals(prevState)) {
                    return "CLOSING";
                } else if ("창문 닫힘".equals(prevState)) {
                    return "OPENING";
                } else {
                    return "ERROR";
                }
            }
        });

        timer_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.timer_10) {
                    if (timerReady) countDownTimer.cancel();
                    int time = 10 * 60 * 1000;
                    count = time / 1000;
                    timerReady = true;
                    initTimer(time);
                    countDownTimer.start();
                } else if (i == R.id.timer_20) {
                    if (timerReady) countDownTimer.cancel();
                    int time = 10 * 1000;
                    count = time / 1000;
                    timerReady = true;
                    initTimer(time);
                    countDownTimer.start();
                } else {
                    timerReady = false;
                    countDownTimer.cancel();
                    timerTextView.setText("");
                }
            }
        });
    }

    private void doLoop(TimerTask timerTask, long period) {
        timer.schedule(timerTask, 0, period);
    }


    private void initTimer(int time) {
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                // 600 -> 540 -> 480 ->   599
                String second = String.valueOf((count % 60));
                String minute = String.valueOf((count / 60));
                timerTextView.setText(minute + ":" + second);
                count--;
            }

            public void onFinish() {
                try {
                    WindowState currentWindowState = WindowState.findBy(windowText.getText().toString());
                    String result = new WindowSetRequest(currentWindowState).execute().get();
                    JSONObject jsonObject = new JSONObject(result);
                    setWindowState(jsonObject.getString("windowMessage"));
                    timerTextView.setText("정상적으로 처리되었습니다.");
                }catch (Exception e) {
                    timerTextView.setText("에러가 발생하여 동작되지 않았습니다.");
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    public void reLanding(List<MeasureValue> measureValues) {
        MeasureValue latestData = measureValues.get(0);
        temperature.setText(latestData.getTemperature() + "도");
        humidity.setText(latestData.getHumidity() + "%");
        fineDust.setText(latestData.getFineDust() + "μm");
        measureTime.setText(latestData.getCreateDate());
        isRain.setText(latestData.isRain() ? "내리는 중" : "내리지 않음");
    }

    public void setWindowState(String windowMessage) {
        windowText.setText(windowMessage);
        if (OPENING.getMessage().equals(windowMessage) || CLOSING.getMessage().equals(windowMessage)) {
            windowButton.setText("취소");
        } else if (OPEN.getMessage().equals(windowMessage)) {
            windowButton.setText("창문 닫기");
        } else if (CLOSE.getMessage().equals(windowMessage)) {
            windowButton.setText("창문 열기");
        }
    }
}
