package com.example.windowproject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> itemList;
    TextView windowText, pdlcText, temperature, airQuality, findDust, windowState, pdlcState, timerTextView;
    Button windowButton, pdlcButton, rainButton;
    RadioButton timer_10, timer_20, timer_30, timer_off, rain_on, rain_off;
    RadioGroup timer_group;
    CountDownTimer countDownTimer;
    boolean timerReady;
    int count;
    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item, null);
        windowText = (TextView) v.findViewById(R.id.window_text);
        pdlcText = (TextView) v.findViewById(R.id.pdlc_text);
        temperature = (TextView) v.findViewById(R.id.temperature);
        airQuality = (TextView) v.findViewById(R.id.air_quality);
        findDust = (TextView) v.findViewById(R.id.fine_dust);
        windowState= (TextView) v.findViewById(R.id.window_state);
        pdlcState = (TextView) v.findViewById(R.id.pdlc_state);
        timerTextView = (TextView) v.findViewById(R.id.timerTextView);
        windowButton = (Button) v.findViewById(R.id.window_button);
        pdlcButton = (Button) v.findViewById(R.id.pdlc_button);
        timer_10 = (RadioButton) v.findViewById(R.id.timer_10);
        timer_20 = (RadioButton) v.findViewById(R.id.timer_20);
        timer_off = (RadioButton) v.findViewById(R.id.timer_off);
        timer_group = (RadioGroup) v.findViewById(R.id.timer_group);
        rainButton = (Button) v.findViewById(R.id.rainButton);
        timerReady = false;
        timerTextView.setText("");
        final MainActivity mainActivity = new MainActivity();

        Item item = itemList.get(position);

        temperature.setText(item.getTemperature());
        airQuality.setText(item.getAirQuality());
        findDust.setText(item.getFineDust());
        windowState.setText(item.getWindowState());
        pdlcState.setText(item.getPdlcState());

        setWindow(item.getWindowState());
        setPdlc(item.getPdlcState());

        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                mainActivity.itemRequest();
                                Toast.makeText(context, "성공!", Toast.LENGTH_SHORT).show();
                                String windowStateText = chagneState(
                                        ItemAdapter.this.windowState.getText().toString());
                                setWindow(windowStateText);
                                windowState.setText(windowStateText);
                            } else {
                                Toast.makeText(context, "다시 한번 시도해주세요!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                WindowStateChangeRequest changeRequest = new WindowStateChangeRequest(windowState.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(v.getContext()); //Request를 실질적으로 보내는 Queue
                queue.add(changeRequest);
            }
        });

        pdlcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                mainActivity.itemRequest();
                                Toast.makeText(context, "성공!", Toast.LENGTH_SHORT).show();
                                String pdlcStateText = chagneState(ItemAdapter.this.pdlcState.getText().toString());
                                setPdlc(pdlcStateText);
                                pdlcState.setText(pdlcStateText);

                            } else {
                                Toast.makeText(context, "다시 한번 시도해주세요!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                PdlcStateChangeRequest changeRequest = new PdlcStateChangeRequest(pdlcState.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(v.getContext()); //Request를 실질적으로 보내는 Queue
                queue.add(changeRequest);
            }
        });

        rainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rainInfo = rainButton.getText().toString();
                if ("ON".equals(rainInfo)){
                    rainButton.setText("OFF");
                }else{
                    rainButton.setText("ON");
                }
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
        return v;
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
                timerTextView.setText(String.valueOf("Finish"));
            }
        };
    }

    private void setPdlc(String pdlcState) {
        if (pdlcState.equals("OFF")) {
            pdlcText.setText("PDLC 꺼짐");
            pdlcButton.setText("PDLC 켜기");
        } else {
            pdlcText.setText("PDLC 켜짐");
            pdlcButton.setText("PDLC 끄기");
        }
    }

    private void setWindow(String windowState) {
        if (windowState.equals("OFF")) {
            windowText.setText("창문 닫힘");
            windowButton.setText("창문 열기");
        } else {
            windowText.setText("창문 열림");
            windowButton.setText("창문 닫기");
        }
    }

    private String chagneState(String state) {
        return state.equals("ON") ? "OFF" : "ON";
    }

}
