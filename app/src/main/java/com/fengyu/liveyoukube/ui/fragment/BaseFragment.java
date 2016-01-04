package com.fengyu.liveyoukube.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fengyu.liveyoukube.YouKubeApp;
import com.fengyu.liveyoukube.R;
import com.mcxiaoke.volley.RequestManager;

public class BaseFragment extends Fragment {


    private Activity activity;

    //状态码
    private static final int STATE_SUCCESS = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_EMPTY = 2;
    private static final int STATE_ERROR = 3;
    private static final int STATE_UNKNOW = 4;

    private int STATE = STATE_UNKNOW;

    //状态view页面
    private View loadingView;

    private View errorView;

    private View emptyView;

    private View successView;

    private FrameLayout frameLayout;

    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (frameLayout == null) {
            frameLayout = new FrameLayout(getActivity());
            initView();
        } else {
            ViewParent parent = frameLayout.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(frameLayout);
            }
        }
        return frameLayout;
    }

    public Context getContext() {
        if (activity == null) {
            return YouKubeApp.getInstance();
        }
        return activity;
    }

    private void initView() {
        loadingView = View.inflate(getActivity(), R.layout.page_loading, null);
        emptyView = View.inflate(getActivity(), R.layout.page_empty, null);
        errorView = View.inflate(getActivity(), R.layout.page_error, null);

        if (loadingView != null) {
            frameLayout.addView(loadingView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        if (emptyView != null) {
            frameLayout.addView(emptyView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        if (errorView != null) {
            frameLayout.addView(errorView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }

        showPage();
    }

    private void showPage() {
        if (loadingView != null)
            loadingView.setVisibility(STATE == STATE_UNKNOW || STATE == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        if (emptyView != null)
            emptyView.setVisibility(STATE == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        if (errorView != null)
            errorView.setVisibility(STATE == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }

}
