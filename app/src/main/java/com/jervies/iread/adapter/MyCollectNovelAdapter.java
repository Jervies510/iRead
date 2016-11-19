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

public class MyCollectNovelAdapter extends RecyclerView.Adapter<MyCollectNovelAdapter.NovelViewHolder> {

    private Context mContext;
    private ArrayList<CollectBean> list = new ArrayList<>();

    public MyCollectNovelAdapter(Context context, ArrayList<CollectBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public NovelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NovelViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_collect_item,null));
    }

    @Override
    public void onBindViewHolder(NovelViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NovelViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv_title;
        TextView tv_summary;
        public NovelViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.imageView_CollectActivity_item);
            tv_title = (TextView) itemView.findViewById(R.id.textView_CollectActivity_title);
            tv_summary = (TextView) itemView.findViewById(R.id.textView_CollectActivity_summary);
        }
    }
}
