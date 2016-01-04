package com.fengyu.liveyoukube.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.bean.CommentInfo;

import java.util.ArrayList;


/**
 * Created by Administrator on 2015/11/23.
 */
public class ProgramRecyclerViewCommentsAda extends RecyclerView.Adapter<ProgramRecyclerViewCommentsAda.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList<CommentInfo> comments;

    private LayoutInflater layoutInflater;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ProgramRecyclerViewCommentsAda(ArrayList<CommentInfo> comments, Context context) {
        this.comments = comments;
        this.mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_programrv_comments, parent,
                false);
        CardView cardView = (CardView) view.findViewById(R.id.item_programcv_comment);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        cardView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_content.setText(comments.get(position).getcontent());
        holder.tv_user.setText(comments.get(position).getuserName());
        holder.tv_published.setText(comments.get(position).getpublished());
        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.cv.setTag(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (CommentInfo) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tv_content;
        TextView tv_user;
        TextView tv_published;

        public MyViewHolder(View view) {
            super(view);
            tv_content = (TextView) view.findViewById(R.id.item_programtv_content);
            tv_user = (TextView) view.findViewById(R.id.item_programtv_username);
            tv_published = (TextView) view.findViewById(R.id.item_programtv_published);
            cv = (CardView) view.findViewById(R.id.item_programcv_comment);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, CommentInfo data);
    }
}

