package com.fengyu.liveyoukube.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fengyu.liveyoukube.R;
import com.youkube.player.PlayerActivity;


/**
 * Created by Administrator on 2015/12/1.
 */
public class ProgramSetVPTabButtonFragment extends BaseFragment {

    /*组件类成员*/
    private Button mbtnPlay;
    /*数据类成员*/
    private String mprogramId;

    public ProgramSetVPTabButtonFragment() {
    }


    public static ProgramSetVPTabButtonFragment newInstance(String programId) {
        ProgramSetVPTabButtonFragment fragmentDemo = new ProgramSetVPTabButtonFragment();
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
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        mbtnPlay = (Button) view.findViewById(R.id.btn_play);
        mbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mprogramId != null && !mprogramId.equals("")) {
                    Intent i = new Intent(getActivity(), PlayerActivity.class);
                    i.putExtra("vid", mprogramId.trim());
                    getActivity().startActivity(i);
                }
            }
        });
        return view;
    }
}
