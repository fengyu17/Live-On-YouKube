package com.fengyu.liveyoukube.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fengyu.liveyoukube.AppConfig;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.adapter.ProgramRecyclerViewCommentsAda;
import com.fengyu.liveyoukube.bean.CommentInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1.
 */
public class ProgramCommentVPTabListViewFragment extends BaseFragment {


    private ProgramRecyclerViewCommentsAda programRecyclerViewCommentsAda;
    private ArrayList<CommentInfo> comments = new ArrayList<>();

    private String mprogramId;

    public ProgramCommentVPTabListViewFragment() {
    }

    public static ProgramCommentVPTabListViewFragment newInstance(String programId) {
        ProgramCommentVPTabListViewFragment fragmentDemo = new ProgramCommentVPTabListViewFragment();
        Bundle args = new Bundle();
        args.putString("programId", programId);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mprogramId = getArguments().getString("programId", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.vod_rv_program);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);

        programRecyclerViewCommentsAda = new ProgramRecyclerViewCommentsAda(comments, getContext());
        recyclerView.setAdapter(programRecyclerViewCommentsAda);
        String url = AppConfig.URL_YOUKU_VOD_COMMENTS + "&video_id=" + "XMjg1MTcyNDQ0";
        executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));

        return view;
    }

    private Response.Listener<JSONObject> responseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null)
                    try {
                        Log.i("VodMovie", response.toString());
                        JSONArray jsonArray = response.getJSONArray("comments");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            CommentInfo commentInfo = new CommentInfo(object.getString("id"), object.getString("content"), object.getJSONObject("user").getString("name"), object.getJSONObject("source").getString("name"), object.getString("published"));
                            comments.add(commentInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                if (comments.equals("") || comments.size() < 0)
                    return;
                programRecyclerViewCommentsAda.notifyDataSetChanged();
            }
        };
    }
}