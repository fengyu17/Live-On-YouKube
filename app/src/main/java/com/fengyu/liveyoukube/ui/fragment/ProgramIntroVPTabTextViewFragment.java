package com.fengyu.liveyoukube.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fengyu.liveyoukube.AppConfig;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.bean.ProgramDetails;
import com.fengyu.liveyoukube.bean.ProgramInfo;
import com.mcxiaoke.volley.RequestManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/1.
 */
public class ProgramIntroVPTabTextViewFragment extends BaseFragment {

    /*组件类成员*/
    private TextView mtvName;
    private TextView mtvTime;
    private TextView mtvRating;
    private TextView mtvCategory;
    private TextView mtvArea;
    private TextView mtvDirector;
    private TextView mtvActor;
    private TextView mtvIntro;
    /*数据类成员*/
    private ProgramDetails mProgramDetails;
    public static String mediaAddr = null;
    private String mprogramId;

    //Avoid non-default constructors in fragments: use a default constructor plus Fragment#setArguments(Bundle) instead
    public ProgramIntroVPTabTextViewFragment() {
    }

    public static ProgramIntroVPTabTextViewFragment newInstance(String programId) {
        ProgramIntroVPTabTextViewFragment fragmentDemo = new ProgramIntroVPTabTextViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_textview, container, false);
        mtvName = (TextView) view.findViewById(R.id.intro_name);
        mtvTime = (TextView) view.findViewById(R.id.intro_time);
        mtvRating = (TextView) view.findViewById(R.id.intro_rating);
        mtvCategory = (TextView) view.findViewById(R.id.intro_category);
        mtvArea = (TextView) view.findViewById(R.id.intro_area);
        mtvDirector = (TextView) view.findViewById(R.id.intro_director);
        mtvActor = (TextView) view.findViewById(R.id.intro_actor);
        mtvIntro = (TextView) view.findViewById(R.id.intro_intro);

        String url = AppConfig.URL_YOUKU_VOD_INFO + "&show_id=" + mprogramId;
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
                        JSONObject attr = response.getJSONObject("attr");
                        JSONArray directorsArray = attr.getJSONArray("director");
                        JSONArray actorsArray = attr.getJSONArray("performer");
                        StringBuffer actors = new StringBuffer();
                        StringBuffer directors = new StringBuffer();
                        for (int i = 0; i < directorsArray.length(); i++) {
                            directors.append(directorsArray.getJSONObject(i).getString("name"));
                            directors.append("/");
                        }
                        for (int i = 0; i < actorsArray.length(); i++) {
                            actors.append(actorsArray.getJSONObject(i).getString("name"));
                            actors.append("/");
                        }
                        mProgramDetails = new ProgramDetails(response.getString("thumbnail_large"), response.getString("name"), response.getString("released"), response.getString("score"), response.getString("genre"), response.getString("area"), directors.toString(), actors.toString(), response.getString("description"), response.getString("id"));
                        mediaAddr = response.getString("play_link");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                mtvName.setText(mProgramDetails.getName());
                mtvRating.setText(mProgramDetails.getRating());
                mtvTime.setText(mProgramDetails.getReleased());
                mtvCategory.setText(mProgramDetails.getGenre());
                mtvArea.setText(mProgramDetails.getArea());
                mtvDirector.setText(mProgramDetails.getDirector());
                mtvActor.setText(mProgramDetails.getActors());
                mtvIntro.setText(mProgramDetails.getIntro());
            }
        };
    }
}
