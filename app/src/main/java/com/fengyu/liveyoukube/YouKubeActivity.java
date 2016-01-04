package com.fengyu.liveyoukube;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import com.fengyu.liveyoukube.adapter.MainRecyclerViewHotNewsAda;
import com.fengyu.liveyoukube.bean.HotNewsInfo;
import com.fengyu.liveyoukube.ui.activity.BaseActivity;
import com.fengyu.liveyoukube.ui.activity.FavoritesActivity;
import com.fengyu.liveyoukube.ui.activity.LiveActivity;
import com.fengyu.liveyoukube.ui.activity.VodActivity;
import com.fengyu.liveyoukube.ui.widget.AutoScrollViewPager;
import com.fengyu.liveyoukube.ui.widget.CubeTransformer;
import com.fengyu.liveyoukube.ui.widget.DepthPageTransformer;
import com.fengyu.liveyoukube.ui.widget.ZoomOutPageTransformer;
import com.mcxiaoke.volley.RequestManager;
import com.youku.login.util.Youku;
import com.youku.player.ApiManager;
import com.youkube.player.HotspotPlayerActivity;
import com.youkube.player.PlayerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YouKubeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    private AutoScrollViewPager mViewPager;

    private String mHost;

    private MainRecyclerViewHotNewsAda mMainRecyclerViewHotNewsAda;

    private HomeHotPagerAda viewPagerAda;
    //热资讯
    private ArrayList<HotNewsInfo> mTopHots = new ArrayList<>();
    //新资讯
    private ArrayList<HotNewsInfo> mTopNews = new ArrayList<>();


    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youkube);
        mHost = AppConfig.URL_YOUKU_HOTNEWS + "&tag=";
        initView();
        loadData();
    }

    private void loadData() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //--------------最新-----------------
        final RecyclerView rvHotNews = (RecyclerView) findViewById(R.id.main_rv_hotword);
        rvHotNews.setHasFixedSize(false);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//表示两列，并且是竖直方向的瀑布流
        rvHotNews.setLayoutManager(mStaggeredGridLayoutManager);
        mMainRecyclerViewHotNewsAda = new MainRecyclerViewHotNewsAda(mTopNews, mContext);
        rvHotNews.setAdapter(mMainRecyclerViewHotNewsAda);
        String url = String.format(mHost + "%s", "最新");
        executeRequest(new JsonObjectRequest(Request.Method.GET, url, responseListener(), errorListener()));
        //---------------热资讯----------------
        mViewPager = (AutoScrollViewPager) findViewById(R.id.main_vp_hot);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);// 默认在中间，使用户看不到边界
        mViewPager.startAutoScroll();
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        String urlHots = String.format(mHost + "%s", "电视剧");
        executeRequest(new JsonObjectRequest(Request.Method.GET, urlHots, hotsResponseListener(), null));
        mMainRecyclerViewHotNewsAda.setOnItemClickListener(new MainRecyclerViewHotNewsAda.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, HotNewsInfo data) {
                if (data != null) {
                    Intent i = new Intent(YouKubeActivity.this, PlayerActivity.class);
                    i.putExtra("vid", data.getId().toString().trim());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data);
                    i.putExtras(bundle);
                    YouKubeActivity.this.startActivity(i);
                }
            }
        });
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.main_navheader_account);
        ImageView ivAccount = (ImageView) headerView.findViewById(R.id.nav_iv_avatar);
        ivAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Youku.isLogined)
                    ApiManager.doLogin(mContext);
            }
        });

    }

    private Response.Listener<JSONObject> hotsResponseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("----", response.toString());
                if (response != null)
                    try {
                        JSONArray jsonArray = response.getJSONArray("videos");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HotNewsInfo hotWordInfo = new HotNewsInfo(object.getString("id"), object.getString("thumbnail"), object.getString("title"), object.getString("view_count"), object.getString("published"), object.getString("link"), object.getString("tags"), object.getString("up_count"), object.getString("down_count"), object.getString("category"));
                            mTopHots.add(hotWordInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                if (mTopHots.equals("") || mTopHots.size() < 0)
                    return;
                viewPagerAda = new HomeHotPagerAda(mTopHots);
                mViewPager.setAdapter(viewPagerAda);
            }
        };
    }

    private Response.Listener<JSONObject> responseListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("----", response.toString());
                if (response != null)
                    try {
                        JSONArray jsonArray = response.getJSONArray("videos");
                        int length = jsonArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            HotNewsInfo hotWordInfo = new HotNewsInfo(object.getString("id"), object.getString("thumbnail"), object.getString("title"), object.getString("view_count"), object.getString("published"), object.getString("link"), object.getString("tags"), object.getString("up_count"), object.getString("down_count"), object.getString("category"));
                            mTopNews.add(hotWordInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                if (mTopNews.equals("") || mTopNews.size() < 0)
                    return;
                mMainRecyclerViewHotNewsAda.notifyDataSetChanged();
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(BaseActivity.FINISH_ALLACT_ACTION));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_live:
                Intent intent_live = new Intent(YouKubeActivity.this, LiveActivity.class);
                YouKubeActivity.this.startActivity(intent_live);
                break;
            case R.id.nav_vod:
                Intent intent_vod = new Intent(YouKubeActivity.this, VodActivity.class);
                YouKubeActivity.this.startActivity(intent_vod);
                break;
            case R.id.nav_favorites:
                Intent intent_favorites = new Intent(YouKubeActivity.this, FavoritesActivity.class);
                YouKubeActivity.this.startActivity(intent_favorites);
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String url1 = String.format(mHost + "%s", "最热");
                String url2 = String.format(mHost + "%s", "电视剧");
                executeRequest(new JsonObjectRequest(Request.Method.GET, url1, responseListener(), errorListener()));
                executeRequest(new JsonObjectRequest(Request.Method.GET, url2, hotsResponseListener(), errorListener()));
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    private class HomeHotPagerAda extends PagerAdapter {

        private ArrayList<HotNewsInfo> mhotInfos = new ArrayList<>();

        public HomeHotPagerAda(ArrayList<HotNewsInfo> hotInfos) {
            mhotInfos = hotInfos;
        }

        @Override
        public int getCount() {
            //设置成最大，使用户看不到边界
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            //Warning：不要在这里调用removeView
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.item_mainrv_hotnews, null);
//            TextView textView = (TextView) view.findViewById(R.id.item_maintv_title);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_mainiv_thumbnail);
            //对ViewPager页号求模取出View列表中要显示的项
            position %= mhotInfos.size();
            if (position < 0) {
                position = mhotInfos.size() + position;
            }
            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent vp = imageView.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(imageView);
            }
//            textView.setText(mhotInfos.get(position).getTitle().toString().trim());
            ImageLoader imageLoader = RequestManager.getImageLoader();
            imageLoader.get(mhotInfos.get(position).getThumbnail(), ImageLoader.getImageListener(imageView, R.mipmap.youtube_bg, R.mipmap.youtube_bg), 200, 300);
            container.addView(imageView);
//            container.addView(textView);
            //add listeners here if necessary
            return imageView;
        }
    }

}