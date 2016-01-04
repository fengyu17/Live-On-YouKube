package com.fengyu.liveyoukube.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fengyu.liveyoukube.AppConfig;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.adapter.VodGridViewProgramAda;
import com.fengyu.liveyoukube.bean.ProgramInfo;
import com.youku.login.service.ILogin;
import com.youku.login.service.LoginException;
import com.youku.login.service.LoginManagerImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/28.
 */
public class FavoritesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    /*组件类成员*/
    private GridView gridView;
    private SwipeRefreshLayout swipeRefreshLayout;
    /*数据类成员*/
    private VodGridViewProgramAda vodGridViewProgramAda;
    final ArrayList<ProgramInfo> mPrograms = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.favorite_toolbar);
        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        LoginManagerImpl.getInstance().authorize(new ILogin.ICallBack() {
            @Override
            public void onSuccess() {
                LoginManagerImpl.getInstance().accesstoken(new ILogin.ICallBack() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailed(LoginException e) {

                    }
                });
            }

            @Override
            public void onFailed(LoginException e) {

            }
        });
        initView();
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.vod_srl);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        gridView = (GridView) findViewById(R.id.vod_gv_program);
        gridView.setNumColumns(3);
        String url = AppConfig.URL_YOUKU_VOD_FAVORITES;
        executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
        vodGridViewProgramAda = new VodGridViewProgramAda(mContext, mPrograms, 3);
        gridView.setAdapter(vodGridViewProgramAda);
    }

    private Response.Listener<JSONObject> responseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null)
                    try {
                        Log.i("VodMovie", response.toString());
                        JSONArray jsonArray = response.getJSONArray("videos");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ProgramInfo programInfo = new ProgramInfo(object.getString("thumbnail"), object.getString("title"), object.getString("published"), object.getString("link"), object.getString("id"));
                            mPrograms.add(programInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                if (mPrograms.equals("") || mPrograms.size() < 0)
                    return;
                vodGridViewProgramAda.notifyDataSetChanged();

            }
        };
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String url = AppConfig.URL_YOUKU_VOD_FAVORITES;
                executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}
