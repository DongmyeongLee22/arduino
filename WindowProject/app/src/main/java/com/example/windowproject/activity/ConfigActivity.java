package com.example.windowproject.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.windowproject.MainActivity;
import com.example.windowproject.R;
import com.example.windowproject.common.StringUtils;
import com.example.windowproject.domain.User;
import com.example.windowproject.http.request.MemberConfigUpdateRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.windowproject.common.StringUtils.transToBoolean;
import static com.example.windowproject.common.StringUtils.transToNumber;

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


        final User user = buildUser(getIntent().getStringExtra("result"));

        landingConfigActivity(user);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int openTemp = StringUtils.transToNumber(openTemperature.getText().toString());
                    int closeTemp = StringUtils.transToNumber(closeTemperature.getText().toString());
                    int openHumi = StringUtils.transToNumber(openHumidity.getText().toString());
                    int closeHumi = StringUtils.transToNumber(closeHumidity.getText().toString());
                    int openFD = StringUtils.transToNumber(openFineDust.getText().toString());
                    int closeFD = StringUtils.transToNumber(closeFineDust.getText().toString());
                    boolean checkedTemp = checkedTemperature.isChecked();
                    boolean checkedHumi = checkedHumidity.isChecked();
                    boolean checkedFD = checkedFineDust.isChecked();

                    User updateUser = User.builder()
                            .name(user.getName())
                            .checkedTemp(checkedTemp)
                            .closeWindowTemp(closeTemp)
                            .openWindowTemp(openTemp)
                            .checkedHumidity(checkedHumi)
                            .closeWindowHumidity(closeHumi)
                            .openWindowHumidity(openHumi)
                            .checkedFineDust(checkedFD)
                            .closeWindowFineDust(closeFD)
                            .openWindowFineDust(openFD)
                            .build();



                    Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            User updatedUser = buildUser(response);
                            Toast.makeText(context, "성공!", Toast.LENGTH_SHORT).show();
                            landingConfigActivity(updatedUser);
                        }
                    };

                    MemberConfigUpdateRequest memberConfigUpdateRequest = new MemberConfigUpdateRequest(Request.Method.PUT, new JSONObject(updateUser.toMap()) , responseListener, null);
                    RequestQueue queue = Volley.newRequestQueue(v.getContext()); //Request를 실질적으로 보내는 Queue
                    queue.add(memberConfigUpdateRequest);


                } catch (IllegalAccessException e) {
                    Toast.makeText(context, "입력값을 확인해주세요. 숫자만 가능합니다.", Toast.LENGTH_SHORT).show();
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

    private User buildUser(JSONObject jsonObject) {
        User user = null;
        try {
            user = User.builder()
                    .name(jsonObject.getString("name"))
                    .checkedTemp(transToBoolean(jsonObject.getString("checkedTemp")))
                    .closeWindowTemp(transToNumber(jsonObject.getString("closeWindowTemp")))
                    .openWindowTemp(transToNumber(jsonObject.getString("openWindowTemp")))
                    .checkedHumidity(transToBoolean(jsonObject.getString("checkedHumidity")))
                    .closeWindowHumidity(transToNumber(jsonObject.getString("closeWindowHumidity")))
                    .openWindowHumidity(transToNumber(jsonObject.getString("openWindowHumidity")))
                    .checkedFineDust(transToBoolean(jsonObject.getString("checkedFineDust")))
                    .closeWindowFineDust(transToNumber(jsonObject.getString("closeWindowFineDust")))
                    .openWindowFineDust(transToNumber(jsonObject.getString("openWindowFineDust")))
                    .build();
    }catch (JSONException | IllegalAccessException e) {

            e.printStackTrace();
        }
        return user;
    }

    private User buildUser(String result){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return buildUser(jsonObject);
    }

    private void landingConfigActivity(User user) {
        openTemperature.setText(String.valueOf(user.getOpenWindowTemp()));
        closeTemperature.setText(String.valueOf(user.getCloseWindowTemp()));
        openHumidity.setText(String.valueOf(user.getOpenWindowHumidity()));
        closeHumidity.setText(String.valueOf(user.getCloseWindowHumidity()));
        openFineDust.setText(String.valueOf(user.getOpenWindowFineDust()));
        closeFineDust.setText(String.valueOf(user.getCloseWindowFineDust()));

        checkedTemperature.setChecked(user.isCheckedTemp());
        checkedHumidity.setChecked(user.isCheckedHumidity());
        checkedFineDust.setChecked(user.isCheckedFineDust());
    }


}
