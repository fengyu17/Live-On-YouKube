package com.fengyu.liveyoukube.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fengyu.liveyoukube.AppConfig;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.adapter.VodGridViewProgramAda;
import com.fengyu.liveyoukube.bean.ProgramInfo;
import com.fengyu.liveyoukube.ui.activity.ProgramDetailsActivity;
import com.youkube.player.PlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/11.
 */
public class VodViewPagerTabGridViewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;

    private int mNumColumns;

    private String mCategory;

    /* 组件成员*/
    private GridView gridView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private VodGridViewProgramAda gridViewProgramAda;

    private ArrayList<ProgramInfo> mPrograms = new ArrayList<>();
    private int pageNum;
    private boolean isPlayNow;
    private String mHostVod;
    private boolean isFirstToButtom; // 是否是第一次滚动到底部
    private int lastVisiblePositionY = 0; // 显示的最后一个view的位置的Y坐标


    public static VodViewPagerTabGridViewFragment newInstance(int mNumColumns, String category, Boolean isPlayNow) {
        VodViewPagerTabGridViewFragment fragmentDemo = new VodViewPagerTabGridViewFragment();
        Bundle args = new Bundle();
        args.putInt("numColumns", mNumColumns);
        args.putString("category", category);
        args.putBoolean("isPlayNow", isPlayNow);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = 1;
        mNumColumns = getArguments().getInt("numColumns", 2);
        mCategory = getArguments().getString("category", "");
        isPlayNow = getArguments().getBoolean("isPlayNow", true);
        mHostVod = AppConfig.URL_YOUKU_VOD + "&category=" + mCategory + "&count=25" + "&page=";
        String url = String.format(mHostVod + "%s", pageNum);
        executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.vod_srl);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        gridView = (GridView) view.findViewById(R.id.vod_gv_program);
        gridView.setNumColumns(mNumColumns);
        gridViewProgramAda = new VodGridViewProgramAda(mContext, mPrograms, mNumColumns);
        gridView.setAdapter(gridViewProgramAda);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Log.i("parent_children count", parent.getChildCount() + "\n" + parent.getSelectedItemPosition());
                ProgramInfo programInfo = mPrograms.get(position);
                if (isPlayNow) {
                    intent.putExtra("vid", programInfo.getProgramID().toString().trim());
                    intent.setClass(getActivity(), PlayerActivity.class);
                } else {
                    intent.putExtra("ProgramInfo", programInfo);
                    intent.setClass(getActivity(), ProgramDetailsActivity.class);
                }
                getActivity().startActivity(intent);
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) { // 整个滚动时间结束

                    View v = (View) view.getChildAt(view.getChildCount() - 1);
                    int[] location = new int[2];
                    v.getLocationOnScreen(location); // 获取在整个屏幕的绝对坐标
                    int y = location[1];
                    // 滚动到底部
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {

                        if (isFirstToButtom) {
                            pageNum = 2;
                            String url = String.format(mHostVod + "%s", pageNum);
                            executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
                            isFirstToButtom = false;
                            return;
                        } else {
                            if (lastVisiblePositionY != 0 && lastVisiblePositionY - y > 100) {
                                pageNum++;
                                String url = String.format(mHostVod + "%s", pageNum);
                                executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
                            }
                        }

                    }

                    lastVisiblePositionY = y;
                }
            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }
        });
        return view;
    }

    private Response.Listener<JSONObject> responseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null)
                    try {
                        Log.i("VodMovie", response.toString());
                        ProgramInfo programInfo;
                        JSONArray jsonArray = response.getJSONArray("shows");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (mNumColumns == 1)
                                programInfo = new ProgramInfo(object.getString("thumbnail"), object.getString("name"), object.getString("score"), object.getString("id"));
                            else
                                programInfo = new ProgramInfo(object.getString("poster"), object.getString("name"), object.getString("score"), object.getString("id"));
                            mPrograms.add(programInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                if (mPrograms.equals("") || mPrograms.size() < 0)
                    return;
                gridViewProgramAda.notifyDataSetChanged();

            }
        };
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNum = 1;
                String url = String.format(mHostVod + "%s", pageNum);
                executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}
