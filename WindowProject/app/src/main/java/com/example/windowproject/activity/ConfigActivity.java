package com.example.windowproject.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.windowproject.MainActivity;
import com.example.windowproject.R;
import com.example.windowproject.http.request.MemberConfigRequest;

public class ConfigActivity extends AppCompatActivity {


    private EditText openTemperature, closeTemperature, openHumidity, closeHumidity, openFineDust, closeFineDust;
    private CheckBox checkedTemperature, checkedHumidity, checkedFineDust;
    private Button submitBtn, homeBtn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        context = this.getApplicationContext();

        openTemperature = (EditText) findViewById(R.id.openTemperature);
        closeTemperature = (EditText) findViewById(R.id.closeTemperature);
        openHumidity = (EditText) findViewById(R.id.openHumidity);
        closeHumidity = (EditText) findViewById(R.id.closeHumidity);
        openFineDust = (EditText) findViewById(R.id.openFineDust);
        closeFineDust = (EditText) findViewById(R.id.closeFineDust);

        checkedTemperature = (CheckBox) findViewById(R.id.checkedTemperature);
        checkedHumidity = (CheckBox) findViewById(R.id.checkedHumidity);
        checkedFineDust = (CheckBox) findViewById(R.id.checkedFineDust);

        submitBtn = (Button) findViewById(R.id.submitBtn);
        homeBtn = (Button) findViewById(R.id.homeBtn);

        new MemberConfigRequest("이혜은").execute();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int openTemp = validateNumber(openTemperature.getText().toString());
                    int closeTemp = validateNumber(closeTemperature.getText().toString());
                    int openHumi = validateNumber(openHumidity.getText().toString());
                    int closeHumi = validateNumber(closeHumidity.getText().toString());
                    int openFD = validateNumber(openFineDust.getText().toString());
                    int closeFD = validateNumber(closeFineDust.getText().toString());

                    boolean checkedTemp = checkedTemperature.isChecked();
                    boolean checkedHumi = checkedHumidity.isChecked();
                    boolean checkedFD = checkedFineDust.isChecked();

                    System.out.println(openTemp);
                    System.out.println(closeTemp);
                    System.out.println(openHumi);
                    System.out.println(closeHumi);
                    System.out.println(openFD);
                    System.out.println(closeFD);

                    System.out.println("checkedTemp = " + checkedTemp);
                    System.out.println("checkedHumi = " + checkedHumi);
                    System.out.println("checkedFD = " + checkedFD);

                }catch (IllegalAccessException e){
                    Toast.makeText(context,"입력값을 확인해주세요. 숫자만 가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private int validateNumber(String string) throws IllegalAccessException {
        try{
            return Integer.parseInt(string);
        }catch (NumberFormatException e){
            throw new IllegalAccessException();
        }
    }
}
