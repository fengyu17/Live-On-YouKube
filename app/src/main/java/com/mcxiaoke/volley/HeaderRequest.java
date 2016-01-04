package com.mcxiaoke.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2015/11/28.
 * <p/>
 */
public class HeaderRequest extends JsonObjectRequest {


    private static final String TAG = "HeaderHttp";

    public HeaderRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);

    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "application/json");
        headers.put("content-type", "application/json");
        headers.put("apix-key", "f7c0f8fc580745c777a756bd6f67f653");
        return headers;
    }


}
