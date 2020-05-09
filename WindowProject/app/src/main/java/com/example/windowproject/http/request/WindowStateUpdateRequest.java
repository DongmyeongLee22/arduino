package com.example.windowproject.http.request;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.windowproject.common.Profile;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WindowStateUpdateRequest extends JsonObjectRequest {
    final static private String URL = Profile.BASE_URL + "/window/set";

    public WindowStateUpdateRequest(JSONObject jsonRequest, Response.Listener<JSONObject> listener){
        super(Method.GET , URL, jsonRequest, listener, null);
    }



}