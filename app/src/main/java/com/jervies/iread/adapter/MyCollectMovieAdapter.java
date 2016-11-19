package com.jervies.iread.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jervies.iread.CollectActivity;
import com.jervies.iread.MovieItemActivity;
import com.jervies.iread.NovelItemActivity;
import com.jervies.iread.PicItemActivity;
import com.jervies.iread.R;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.CollectBean;
import com.jervies.iread.helpClass.CollectType;
import com.jervies.iread.helpClass.MySQLiteOpenHelder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Jervies on 2016/11/19.
 */

public class MyCollectMovieAdapter extends RecyclerView.Adapter<MyCollectMovieAdapter.MovieViewHolder>{

    private Context mContext;
    private ArrayList<CollectBean> list = new ArrayList<>();
    private MySQLiteOpenHelder mMySQLiteOpenHelder;
    private SQLiteDatabase db;
    private CollectActivity mCollectActivity;

    public MyCollectMovieAdapter(Context context, ArrayList<CollectBean> list, MySQLiteOpenHelder mySQLiteOpenHelder) {
        mContext = context;
        this.list = list;
        mMySQLiteOpenHelder = mySQLiteOpenHelder;
        mCollectActivity = new CollectActivity();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(mContext).inflate(R.layout.activity_collect_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        Picasso.with(mContext).load(UrlUtils.URLPICTURE + list.get(position).getImage()).into(holder.iv);
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_summary.setText(list.get(position).getSummary());

        //设置点击事件跳转到相应界面显示详细内容
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (list.get(position).getType()) {
                    case CollectType.TYPE_MOVIE:
                        Intent intentMovie = new Intent(mContext, MovieItemActivity.class);
                        intentMovie.putExtra("item_id", list.get(position).getItem_id());
                        mContext.startActivity(intentMovie);
                        break;
                    case CollectType.TYPE_NOVEL:
                        Intent intentNovel = new Intent(mContext, NovelItemActivity.class);
                        intentNovel.putExtra("item_id", list.get(position).getItem_id());
                        mContext.startActivity(intentNovel);
                        break;
                    case CollectType.TYPE_PIC:
                        Intent intentPic = new Intent(mContext, PicItemActivity.class);
                        intentPic.putExtra("item_id", list.get(position).getItem_id());
                        mContext.startActivity(intentPic);
                        break;
                }
            }
        });

        //设置长按事件弹出对话框显示删除，用于删除已收藏内容
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View view_delete = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog_delete, null);
                final AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setView(view_delete)
                        .show();
                TextView delete = (TextView) view_delete.findViewById(R.id.textView_dialog_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //根据条件删除相应内容
                        db = mMySQLiteOpenHelder.getReadableDatabase();
                        db.delete("content", "type = " + list.get(position).getType() + " and item_id = " + list.get(position).getItem_id(), null);
                        mCollectActivity.initData();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv_title;
        TextView tv_summary;
        public MovieViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.imageView_CollectActivity_item);
            tv_title = (TextView) itemView.findViewById(R.id.textView_CollectActivity_title);
            tv_summary = (TextView) itemView.findViewById(R.id.textView_CollectActivity_summary);
        }
    }

}
