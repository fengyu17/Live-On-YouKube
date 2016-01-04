package com.fengyu.liveyoukube.adapter;


import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.bean.HotNewsInfo;
import com.mcxiaoke.volley.RequestManager;


/**
 * Created by Administrator on 2015/11/23.
 */
public class MainRecyclerViewHotNewsAda extends RecyclerView.Adapter<MainRecyclerViewHotNewsAda.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<HotNewsInfo> mHotNewsInfos;

    private LayoutInflater layoutInflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public MainRecyclerViewHotNewsAda(ArrayList<HotNewsInfo> hotNewsInfos, Context context) {
        this.mHotNewsInfos = hotNewsInfos;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_mainrv_hotnews, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        CardView cardView = (CardView) view.findViewById(R.id.item_maincv_hotnews);
        //将创建的View注册点击事件
        cardView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(mHotNewsInfos.get(position).getTitle());
        holder.tv_viewcount.setText(mHotNewsInfos.get(position).getView_count() + "万");
        holder.tv_upcount.setText(mHotNewsInfos.get(position).getUp_count());
        ImageLoader imageLoader = RequestManager.getImageLoader();
        imageLoader.get(mHotNewsInfos.get(position).getThumbnail(), ImageLoader.getImageListener(holder.iv, R.mipmap.youtube_bg, R.mipmap.youtube_bg), 200, 300);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.cv.setTag(mHotNewsInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return mHotNewsInfos.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (HotNewsInfo) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tv_title, tv_viewcount, tv_upcount;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            cv = (CardView) view.findViewById(R.id.item_maincv_hotnews);
            tv_title = (TextView) view.findViewById(R.id.item_maintv_title);
            tv_viewcount = (TextView) view.findViewById(R.id.item_maintv_viewcount);
            tv_upcount = (TextView) view.findViewById(R.id.item_maintv_upcount);
            iv = (ImageView) view.findViewById(R.id.item_mainiv_thumbnail);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, HotNewsInfo data);
    }

    public int changecolor() {
        Random random = new Random();
        int sum = 255;
        int one = (int) (Math.random() * 100 + 150);
        int other = sum - one;

        int r, g, b;

        int i = random.nextInt(6);
        switch (i) {
            case 1:
                r = one;
                g = other;
                b = 0;
                break;
            case 2:
                r = one;
                g = 0;
                b = other;
                break;
            case 3:
                r = other;
                g = 0;
                b = one;
                break;
            case 4:
                r = other;
                g = one;
                b = 0;
                break;
            case 5:
                r = 0;
                g = one;
                b = other;
                break;
            case 6:
                r = 0;
                g = other;
                b = one;
                break;
            default:
                r = 0;
                g = one;
                b = other;
                break;

        }
        int color = Color.argb(180, r, g, b);
        return color;
    }
}

