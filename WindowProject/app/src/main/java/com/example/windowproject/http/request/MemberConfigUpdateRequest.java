package com.example.windowproject.http.request;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.windowproject.common.Profile;

import org.json.JSONObject;

public class MemberConfigUpdateRequest extends JsonObjectRequest {
    final static private String URL = Profile.BASE_URL + "/user";

    public MemberConfigUpdateRequest(int method, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, URL, jsonRequest, listener, errorListener);
    }
}