package com.fengyu.liveyoukube.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.ui.fragment.ChannelTabRecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public class LiveActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        Toolbar toolbar = (Toolbar) findViewById(R.id.live_toolbar);
        toolbar.setTitle("直播乐");
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        ViewPager mViewPager = (ViewPager) findViewById(R.id.live_viewpager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.live_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("央视"));
        tabLayout.addTab(tabLayout.newTab().setText("卫视"));
        tabLayout.addTab(tabLayout.newTab().setText("港澳台"));
        tabLayout.addTab(tabLayout.newTab().setText("日韩欧美"));
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager mViewPager) {
        ProgramPagerAdapter adapter = new ProgramPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ChannelTabRecyclerViewFragment.newInstance("cctv"), "央视");
        adapter.addFragment(ChannelTabRecyclerViewFragment.newInstance("satellite"), "卫视");
        adapter.addFragment(ChannelTabRecyclerViewFragment.newInstance("hmt"), "港澳台");
        adapter.addFragment(ChannelTabRecyclerViewFragment.newInstance("jkea"), "日韩欧美");
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

}
