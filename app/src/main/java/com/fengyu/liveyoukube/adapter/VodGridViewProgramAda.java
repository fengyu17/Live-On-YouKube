package com.fengyu.liveyoukube.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.fengyu.liveyoukube.R;
import com.fengyu.liveyoukube.bean.ProgramInfo;
import com.mcxiaoke.volley.RequestManager;

public class VodGridViewProgramAda extends BaseAdapter {

    private Context context;
    private ArrayList<ProgramInfo> programInfoes;
    private int itemWidthDivNum;

    public VodGridViewProgramAda(Context context, ArrayList<ProgramInfo> programInfoes, int itemWidthDivNum) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.programInfoes = programInfoes;
        this.itemWidthDivNum = itemWidthDivNum;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return programInfoes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View frameLayout = convertView;
        ViewHolder holder;

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int windowWidth = dm.widthPixels;
        int itemWidth = windowWidth / itemWidthDivNum;
        int itemHeight = itemWidthDivNum == 1 ? (itemWidth / 2) : ((9 * itemWidth) / 5);

        if (convertView == null) {
            frameLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_vodgv_program, parent, false);
            frameLayout.setLayoutParams(new GridView.LayoutParams(itemWidth, itemHeight));

            holder = new ViewHolder();
            holder.iv = (ImageView) frameLayout.findViewById(R.id.item_vodiv_img);
            holder.tvName = (TextView) frameLayout.findViewById(R.id.item_vodtv_name);
            holder.tvRating = (TextView) frameLayout.findViewById(R.id.item_vodtv_rating);
            frameLayout.setTag(holder);
        } else {
            holder = (ViewHolder) frameLayout.getTag();
        }

        // 请求网络数据加载封面图片
        ImageLoader imageLoader = RequestManager.getImageLoader();
        imageLoader.get(programInfoes.get(position).getCoverImg(), ImageLoader.getImageListener(holder.iv, R.mipmap.youtube_bg, R.mipmap.youtube_bg), 200, 300);

        holder.tvName.setText(programInfoes.get(position).getName());
        holder.tvRating.setText(programInfoes.get(position).getRating());
        return frameLayout;
    }

    class ViewHolder {
        ImageView iv;
        TextView tvName;
        TextView tvRating;
    }

}
