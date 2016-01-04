package com.fengyu.liveyoukube.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.YouKubeActivity;
import com.fengyu.liveyoukube.ui.interf.ILoadingActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends BaseActivity implements ILoadingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);
        initView();
    }

    @Override
    public void initView() {
        if (isFirst()) {
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            app.getPreferenceUtils().saveBooleanValue("isFirst", true);
            finish();
        } else {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    directTo();
                }
            };
            timer.schedule(task, 2500);
        }
    }

    private boolean isFirst() {

        boolean isFirst = app.getPreferenceUtils().getBooleanValue("isFirst");
        if (isFirst)
            return false;
        else
            return true;

    }

    @Override
    public void directTo() {
        Intent intent = new Intent(this, YouKubeActivity.class);
        startActivity(intent);
        finish();
    }
}
