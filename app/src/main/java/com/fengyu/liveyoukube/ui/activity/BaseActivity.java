package com.fengyu.liveyoukube.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.YouKubeApp;
import com.mcxiaoke.volley.RequestManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/16.
 */
public class BaseActivity extends AppCompatActivity {

    static List<BaseActivity> activityList = new LinkedList<>();

    private FinishAllActivityRecevier recevier;

    protected Context mContext;

    protected YouKubeApp app;

    public static final String FINISH_ALLACT_ACTION = "com.fengyu.hmttv.FINISH_ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = getApplicationContext();
        app = (YouKubeApp) getApplication();
        recevier = new FinishAllActivityRecevier();
        IntentFilter filter = new IntentFilter(FINISH_ALLACT_ACTION);
        registerReceiver(recevier, filter);
        synchronized (activityList) {
            activityList.add(this);
        }
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }

    private void finishAllActivity() {
        List<BaseActivity> allList;
        synchronized (activityList) {
            allList = new LinkedList<>(activityList);
        }
        for (BaseActivity activity : allList)
            activity.finish();
    }

    class FinishAllActivityRecevier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (activityList) {
            activityList.remove(this);
        }

        if (recevier != null)
            unregisterReceiver(recevier);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}