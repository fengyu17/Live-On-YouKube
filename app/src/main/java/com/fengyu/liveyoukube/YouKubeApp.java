package com.fengyu.liveyoukube;

import android.app.Activity;
import android.app.Application;

import com.fengyu.liveyoukube.util.SharedPreferencesUtil;
import com.mcxiaoke.volley.RequestManager;
import com.youku.player.YoukuPlayerBaseConfiguration;
import com.youkube.player.CachedActivity;
import com.youkube.player.CachingActivity;

import io.vov.vitamio.Vitamio;


/**
 * Created by Administrator on 2015/12/17.
 */
public class YouKubeApp extends Application {

    private static YouKubeApp application;
    public static YoukuPlayerBaseConfiguration configuration;
    private SharedPreferencesUtil sharedPreferencesutil;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }

    private void init() {
        RequestManager.init(this);
        Vitamio.isInitialized(getApplicationContext());
        sharedPreferencesutil = SharedPreferencesUtil.getInstance(this);
        configuration = new YoukuPlayerBaseConfiguration(this) {


            /**
             * 通过覆写该方法，返回“正在缓存视频信息的界面”，
             * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
             * 用户需要定义自己的缓存界面
             */
            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                // TODO Auto-generated method stub
                return CachingActivity.class;
            }

            /**
             * 通过覆写该方法，返回“已经缓存视频信息的界面”，
             * 则在状态栏点击下载信息时可以自动跳转到所设定的界面.
             * 用户需要定义自己的已缓存界面
             */

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                // TODO Auto-generated method stub
                return CachedActivity.class;
            }

            /**
             * 配置视频的缓存路径，格式举例： /appname/videocache/
             * 如果返回空，则视频默认缓存路径为： /应用程序包名/videocache/
             *
             */
            @Override
            public String configDownloadPath() {
                // TODO Auto-generated method stub

                //return "/myapp/videocache/";			//举例
                return null;
            }
        };
    }

    public static synchronized YouKubeApp getInstance() {
        return application;
    }

    public SharedPreferencesUtil getPreferenceUtils() {
        return sharedPreferencesutil;
    }
}
