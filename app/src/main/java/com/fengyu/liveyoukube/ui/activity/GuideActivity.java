package com.fengyu.liveyoukube.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.YouKubeActivity;
import com.fengyu.liveyoukube.ui.interf.IGuideActivity;

/**
 * Created by Administrator on 2015/10/29.
 */
public class GuideActivity extends BaseActivity implements IGuideActivity {

    private int[] mImages = {
            R.mipmap.activity_guide_viewpager_start_0,
            R.mipmap.activity_guide_viewpager_start_1,
            R.mipmap.activity_guide_viewpager_start_2,
            R.mipmap.activity_guide_viewpager_start_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    @Override
    public void initView() {
        final ViewPager vp_guide = (ViewPager) findViewById(R.id.activity_guide_vp);
        vp_guide.setAdapter(new GuideViewPagerAdapter());
    }

    @Override
    public void directTo() {
        Intent intent = new Intent(this, YouKubeActivity.class);
        startActivity(intent);
        finish();
    }

    private class GuideViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View imageLayout = layoutInflater.inflate(R.layout.item_loading_viewpager, null);
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.loading_iv_start);
            imageView.setImageResource(mImages[position]);
            if (position == mImages.length - 1) {
                ImageView ivDirect = (ImageView) imageLayout.findViewById(R.id.loading_iv_direct);
                ivDirect.setVisibility(View.VISIBLE);
                ivDirect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        directTo();
                    }
                });
            }
            ((ViewPager) container).addView(imageLayout);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
