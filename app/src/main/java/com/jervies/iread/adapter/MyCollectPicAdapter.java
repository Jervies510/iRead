package com.jervies.iread.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jervies.iread.R;
import com.jervies.iread.bean.CollectBean;

import java.util.ArrayList;

/**
 * Created by Jervies on 2016/11/19.
 */

public class MyCollectPicAdapter extends RecyclerView.Adapter<MyCollectPicAdapter.PicViewHolder> {

    private Context mContext;
    private ArrayList<CollectBean> list = new ArrayList<>();

    public MyCollectPicAdapter(Context context, ArrayList<CollectBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_collect_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PicViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv_title;
        TextView tv_summary;
        public PicViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.imageView_CollectActivity_item);
            tv_title = (TextView) itemView.findViewById(R.id.textView_CollectActivity_title);
            tv_summary = (TextView) itemView.findViewById(R.id.textView_CollectActivity_summary);
        }
    }
}
