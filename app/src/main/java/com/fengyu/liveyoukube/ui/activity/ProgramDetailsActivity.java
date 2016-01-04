package com.fengyu.liveyoukube.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.baseproject.utils.Logger;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.bean.ProgramInfo;
import com.fengyu.liveyoukube.ui.fragment.ProgramCommentVPTabListViewFragment;
import com.fengyu.liveyoukube.ui.fragment.ProgramIntroVPTabTextViewFragment;
import com.fengyu.liveyoukube.ui.fragment.ProgramSetVPTabButtonFragment;
import com.youku.player.base.YoukuBasePlayerManager;
import com.youku.player.base.YoukuPlayer;
import com.youku.player.base.YoukuPlayerView;

import java.util.ArrayList;
import java.util.List;

public class ProgramDetailsActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbar;

    public ProgramInfo programInfo;

    private YoukuBasePlayerManager basePlayerManager;
    // 播放器控件
    private YoukuPlayerView mYoukuPlayerView;

    // 需要播放的视频id
    private String vid;

    // 需要播放的本地视频的id
    private String local_vid;

    // 标示是否播放的本地视频
    private boolean isFromLocal = false;

    private String id = "";

    // YoukuPlayer实例，进行视频播放控制
    private YoukuPlayer youkuPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programdetails);
        programInfo = (ProgramInfo) getIntent().getSerializableExtra("ProgramInfo");
        Toolbar toolbar = (Toolbar) findViewById(R.id.program_toolbar);
        setSupportActionBar(toolbar);

        initView();

    }

    private void initView() {

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(programInfo.getName());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.details_viewpager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("简介"));
        tabLayout.addTab(tabLayout.newTab().setText("剧集"));
        tabLayout.addTab(tabLayout.newTab().setText("评论"));
        tabLayout.setupWithViewPager(mViewPager);

        //--------------------------------------------------------
        basePlayerManager = new YoukuBasePlayerManager(this) {

            @Override
            public void setPadHorizontalLayout() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onInitializationSuccess(YoukuPlayer player) {
                // TODO Auto-generated method stub
                // 初始化成功后需要添加该行代码
                addPlugins();

                // 实例化YoukuPlayer实例
                youkuPlayer = player;

                // 进行播放
                goPlay();
            }

            @Override
            public void onSmallscreenListener() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFullscreenListener() {
                // TODO Auto-generated method stub
                collapsingToolbar.setTitle("");
            }
        };

        basePlayerManager.onCreate();

        // 通过上个页面传递过来的Intent获取播放参数
        getIntentData(getIntent());

        if (TextUtils.isEmpty(id)) {
            vid = "XODQwMTY4NDg0"; // 默认视频
        } else {
            vid = id;
        }

        // 播放器控件
        mYoukuPlayerView = (YoukuPlayerView) this
                .findViewById(R.id.program_full_holder);
        //控制竖屏和全屏时候的布局参数。这两句必填。
        mYoukuPlayerView
                .setFullScreenLayoutParams(new CollapsingToolbarLayout.LayoutParams(
                        CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                        CollapsingToolbarLayout.LayoutParams.MATCH_PARENT));
        mYoukuPlayerView
                .setSmallScreenLayoutParams(new CollapsingToolbarLayout.LayoutParams(
                        CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
                        CollapsingToolbarLayout.LayoutParams.WRAP_CONTENT));
        // 初始化播放器相关数据
        mYoukuPlayerView.initialize(basePlayerManager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        ProgramPagerAdapter adapter = new ProgramPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ProgramIntroVPTabTextViewFragment.newInstance(programInfo.getProgramID()), "简介");
        adapter.addFragment(ProgramSetVPTabButtonFragment.newInstance(programInfo.getProgramID()), "剧集");
        adapter.addFragment(ProgramCommentVPTabListViewFragment.newInstance(programInfo.getProgramID()), "评论");
        mViewPager.setAdapter(adapter);
    }

    static class ProgramPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ProgramPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() { // android系统调用
        Logger.d("sgh", "onBackPressed before super");
        super.onBackPressed();
        Logger.d("sgh", "onBackPressed");
        basePlayerManager.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        basePlayerManager.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basePlayerManager.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean managerKeyDown = basePlayerManager.onKeyDown(keyCode, event);
        if (basePlayerManager.shouldCallSuperKeyDown()) {
            return super.onKeyDown(keyCode, event);
        } else {
            return managerKeyDown;
        }

    }

    @Override
    public void onLowMemory() { // android系统调用
        super.onLowMemory();
        basePlayerManager.onLowMemory();
    }

    @Override
    protected void onPause() {
        super.onPause();
        basePlayerManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        basePlayerManager.onResume();
    }

    @Override
    public boolean onSearchRequested() { // android系统调用
        return basePlayerManager.onSearchRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
        basePlayerManager.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        basePlayerManager.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);

        // 通过Intent获取播放需要的相关参数
        getIntentData(intent);

        // 进行播放
        goPlay();
    }

    /**
     * 获取上个页面传递过来的数据
     */
    private void getIntentData(Intent intent) {

        if (intent != null) {
            // 判断是不是本地视频
            isFromLocal = intent.getBooleanExtra("isFromLocal", false);

            if (isFromLocal) { // 播放本地视频
                local_vid = intent.getStringExtra("video_id");
            } else { // 在线播放
                id = programInfo.getProgramID();
            }
        }

    }

    private void goPlay() {
        if (isFromLocal) { // 播放本地视频
            youkuPlayer.playLocalVideo(local_vid);
        } else { // 播放在线视频
            youkuPlayer.playVideo(vid);
        }
    }
}
