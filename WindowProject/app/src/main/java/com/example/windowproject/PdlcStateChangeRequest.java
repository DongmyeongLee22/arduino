package com.example.windowproject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class PdlcStateChangeRequest extends StringRequest {
    final static private String URL = "http://lehee0430.dothome.co.kr/pdlcStateChange.php";
    private Map<String, String> parameters;

    public PdlcStateChangeRequest(String pdlcState, Response.Listener<String> listener){
        super(Method.POST , URL , listener, null);
        parameters = new HashMap<>();
        parameters.put("pdlcState", pdlcState);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}