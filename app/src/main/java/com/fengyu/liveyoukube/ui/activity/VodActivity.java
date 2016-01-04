package com.fengyu.liveyoukube.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.ui.fragment.VodProgramSearchFragment;
import com.fengyu.liveyoukube.ui.fragment.VodViewPagerTabGridViewFragment;

import java.util.ArrayList;
import java.util.List;

public class VodActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod);
        initView();
    }

    private void initView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.vod_toolbar);
        toolbar.setTitle("点播乐");
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.vod_viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.vod_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(VodViewPagerTabGridViewFragment.newInstance(2, "电视剧", false), "电视剧");
        adapter.addFragment(VodViewPagerTabGridViewFragment.newInstance(3, "电影", false), "电影");
        adapter.addFragment(VodViewPagerTabGridViewFragment.newInstance(1, "综艺", true), "综艺");
        adapter.addFragment(VodViewPagerTabGridViewFragment.newInstance(2, "动漫", true), "动漫");
        adapter.addFragment(VodViewPagerTabGridViewFragment.newInstance(3, "教育", true), "教育");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vod_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.vod_program_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(VodActivity.this, query, Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(android.R.id.content, VodProgramSearchFragment.newInstance(query));
                ft.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
