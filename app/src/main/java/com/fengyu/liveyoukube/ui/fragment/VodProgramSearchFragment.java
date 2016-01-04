package com.fengyu.liveyoukube.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fengyu.liveyoukube.AppConfig;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.adapter.VodGridViewProgramAda;
import com.fengyu.liveyoukube.bean.ProgramInfo;
import com.mcxiaoke.volley.HeaderRequest;
import com.youkube.player.HotspotPlayerActivity;
import com.youkube.player.PlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 */
public class VodProgramSearchFragment extends BaseFragment {

    private Context mContext;

    /*组件类成员*/
    private GridView gridView;
    /*数据类成员*/
    private VodGridViewProgramAda vodGridViewProgramAda;
    final ArrayList<ProgramInfo> mPrograms = new ArrayList<>();
    private String mLable;

    public static VodProgramSearchFragment newInstance(String lable) {
        VodProgramSearchFragment fragmentDemo = new VodProgramSearchFragment();
        Bundle args = new Bundle();
        args.putString("lable", lable);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLable = getArguments().getString("lable", "");
        String url = AppConfig.URL_YOUKU_VOD_SEARCHE + "&keyword=" + mLable + "&page=1&count=20";
        executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gridview, container, false);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.vod_fragment);
        mContext = getContext();
        gridView = (GridView) view.findViewById(R.id.vod_gv_program);
        gridView.setNumColumns(2);
        vodGridViewProgramAda = new VodGridViewProgramAda(mContext, mPrograms, 2);
        gridView.setAdapter(vodGridViewProgramAda);
        SearchView searchView = new SearchView(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        searchView.setQueryHint("搜索...");
        searchView.setLayoutParams(layoutParams);
        int color = Color.argb(180, 255, 255, 255);
        searchView.setBackgroundColor(color);
        frameLayout.addView(searchView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProgramInfo programInfo = mPrograms.get(position);
                Intent i = new Intent(getActivity(), PlayerActivity.class);
                i.putExtra("vid", programInfo.getProgramID().toString().trim());
                getActivity().startActivity(i);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, VodProgramSearchFragment.newInstance(query));
                ft.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
}
