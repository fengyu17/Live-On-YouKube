package com.fengyu.liveyoukube.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.bean.ChannelInfo;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/11/23.
 */
public class LiveRecyclerViewChannelAda extends RecyclerView.Adapter<LiveRecyclerViewChannelAda.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<ChannelInfo> channelInfoes;

    private LayoutInflater layoutInflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public LiveRecyclerViewChannelAda(ArrayList<ChannelInfo> channelInfoes, Context context) {
        this.channelInfoes = channelInfoes;
        this.mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_liverv_channel, parent,
                false);
        CardView cardView = (CardView) view.findViewById(R.id.item_livecv_cv);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        cardView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(channelInfoes.get(position).getChannelName());
        holder.iv.setBackgroundResource(R.drawable.cctv);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.cv.setTag(channelInfoes.get(position));
    }

    @Override
    public int getItemCount() {
        return channelInfoes.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (ChannelInfo) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tv;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.item_livetv_channelname);
            iv = (ImageView) view.findViewById(R.id.item_liveiv_icon);
            cv = (CardView) view.findViewById(R.id.item_livecv_cv);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, ChannelInfo data);
    }
}

