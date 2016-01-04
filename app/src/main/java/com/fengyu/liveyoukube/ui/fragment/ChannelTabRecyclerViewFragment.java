package com.fengyu.liveyoukube.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.adapter.LiveRecyclerViewChannelAda;
import com.fengyu.liveyoukube.bean.ChannelInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vitamio.player.PlayerActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1.
 */
public class ChannelTabRecyclerViewFragment extends BaseFragment {

    /*组件类成员*/
    private RecyclerView recyclerView;
    /*数据类成员*/
    private LiveRecyclerViewChannelAda recyclerViewChannelAda;
    final ArrayList<ChannelInfo> channelInfoes = new ArrayList<>();

    private String categoryType;

    public ChannelTabRecyclerViewFragment() {
    }

    public static ChannelTabRecyclerViewFragment newInstance(String categoryType) {
        ChannelTabRecyclerViewFragment fragmentDemo = new ChannelTabRecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString("categoryType", categoryType);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryType = getArguments().getString("categoryType", "");
        //获取直播节目列表
        String channels = getFromAssets(categoryType + ".json");
        try {
            JSONObject result = new JSONObject(channels);
            setChannelData(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.vod_rv_program);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerViewChannelAda = new LiveRecyclerViewChannelAda(channelInfoes, getContext());
        recyclerView.setAdapter(recyclerViewChannelAda);
        recyclerViewChannelAda.notifyDataSetChanged();
        recyclerViewChannelAda.setOnItemClickListener(new LiveRecyclerViewChannelAda.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, ChannelInfo data) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("mediaAddr", data.getMediaAddr().toString());
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    private void setChannelData(JSONObject result) {
        if (result != null)
            try {
                Log.i("LiveAct", result.toString());
                JSONArray content = result.getJSONArray("content");
                int length = content.length();
                for (int i = 0; i < length; i++) {
                    JSONObject object = content.getJSONObject(i);
                    ChannelInfo channelInfo = new ChannelInfo(object.getString("ChannelID"), object.getString("ChannelName"), object.getString("ChannelNum"), object.getString("ChannelSC"), object.getString("Mediaaddr"));
                    channelInfoes.add(channelInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (channelInfoes.equals("") || channelInfoes.size() < 0)
            return;
    }

    private String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}