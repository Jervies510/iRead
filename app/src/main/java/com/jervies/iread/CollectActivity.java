package com.jervies.iread;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.CollectBean;
import com.jervies.iread.helpClass.MySQLiteOpenHelder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import static com.jervies.iread.helpClass.CollectType.TYPE_MOVIE;
import static com.jervies.iread.helpClass.CollectType.TYPE_NOVEL;
import static com.jervies.iread.helpClass.CollectType.TYPE_PIC;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewCollectActivityBack;
    private RecyclerView mRecyclerViewCollectActivtyMovie;
    private RecyclerView mRecyclerViewCollectActivtyNovel;
    private RecyclerView mRecyclerViewCollectActivtyPic;
    private TextView mTextViewCollectActivitySubTitleMovie;
    private TextView mTextViewCollectActivitySubTitleNovel;
    private TextView mTextViewCollectActivitySubTitlePic;

    private ArrayList<CollectBean> listMovie = new ArrayList<>();
    private ArrayList<CollectBean> listNovel = new ArrayList<>();
    private ArrayList<CollectBean> listPic = new ArrayList<>();
    private MyCollectMovieAdapter movieAdapter;
    private MyCollectNovelAdapter novelAdapter;
    private MyCollectPicAdapter picAdapter;

    private MySQLiteOpenHelder mMySQLiteOpenHelder;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
        initView();
        mRecyclerViewCollectActivtyMovie.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCollectActivtyNovel.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCollectActivtyPic.setLayoutManager(new LinearLayoutManager(this));

        mMySQLiteOpenHelder = new MySQLiteOpenHelder(this, "iRead", null, 1);
        db = mMySQLiteOpenHelder.getWritableDatabase();
        initAdapter();
        initData();
    }

    /**
     * 初始化RecyclerView的适配器
     */
    private void initAdapter() {
        movieAdapter = new MyCollectMovieAdapter();
        mRecyclerViewCollectActivtyMovie.setAdapter(movieAdapter);

        novelAdapter = new MyCollectNovelAdapter();
        mRecyclerViewCollectActivtyNovel.setAdapter(novelAdapter);

        picAdapter = new MyCollectPicAdapter();
        mRecyclerViewCollectActivtyPic.setAdapter(picAdapter);
    }

    /**
     * 1.初始化RecyclerView的显示数据
     * 2.从数据库Content表中进行读取数据
     */
    public void initData() {
        listMovie.clear();
        listNovel.clear();
        listPic.clear();
//        SQLiteDatabase db = mMySQLiteOpenHelder.getReadableDatabase();
        Cursor cursor = db.query("content", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            //int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String summary = cursor.getString(cursor.getColumnIndex("summary"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            switch (type) {
                case TYPE_MOVIE:
                    listMovie.add(new CollectBean(type, item_id, title, summary, image));
                    break;
                case TYPE_NOVEL:
                    listNovel.add(new CollectBean(type, item_id, title, summary, image));
                    break;
                case TYPE_PIC:
                    listPic.add(new CollectBean(type, item_id, title, summary, image));
                    break;
            }
            //用于判断收藏内容上边的小标题是否显示，如果集合中有数据，即有显示列表，小标题显示，否则不显示
            if (listMovie.size() != 0) {
                mTextViewCollectActivitySubTitleMovie.setVisibility(View.VISIBLE);
            } else {
                mTextViewCollectActivitySubTitleMovie.setVisibility(View.GONE);
            }
            if (listNovel.size() != 0) {
                mTextViewCollectActivitySubTitleNovel.setVisibility(View.VISIBLE);
            } else {
                mTextViewCollectActivitySubTitleNovel.setVisibility(View.GONE);
            }
            if (listPic.size() != 0) {
                mTextViewCollectActivitySubTitlePic.setVisibility(View.VISIBLE);
            } else {
                mTextViewCollectActivitySubTitlePic.setVisibility(View.GONE);
            }
            //数据更新后刷新适配器
            movieAdapter.notifyDataSetChanged();
            novelAdapter.notifyDataSetChanged();
            picAdapter.notifyDataSetChanged();
        }
        if (!cursor.moveToNext()) {
            Log.d("tmd", "initData: cursor not null");
            mTextViewCollectActivitySubTitleMovie.setVisibility(View.GONE);
            mTextViewCollectActivitySubTitleNovel.setVisibility(View.GONE);
            mTextViewCollectActivitySubTitlePic.setVisibility(View.GONE);
            //数据更新后刷新适配器
            movieAdapter.notifyDataSetChanged();
            novelAdapter.notifyDataSetChanged();
            picAdapter.notifyDataSetChanged();
        }


    }

    private void initView() {
        mImageViewCollectActivityBack = (ImageView) findViewById(R.id.imageView_CollectActivity_back);
        mRecyclerViewCollectActivtyMovie = (RecyclerView) findViewById(R.id.recyclerView_CollectActivty_movie);
        mTextViewCollectActivitySubTitleMovie = (TextView) findViewById(R.id.textView_CollectActivity_subTitleMovie);
        mRecyclerViewCollectActivtyMovie = (RecyclerView) findViewById(R.id.recyclerView_CollectActivty_movie);
        mTextViewCollectActivitySubTitleNovel = (TextView) findViewById(R.id.textView_CollectActivity_subTitleNovel);
        mRecyclerViewCollectActivtyNovel = (RecyclerView) findViewById(R.id.recyclerView_CollectActivty_novel);
        mTextViewCollectActivitySubTitlePic = (TextView) findViewById(R.id.textView_CollectActivity_subTitlePic);
        mRecyclerViewCollectActivtyPic = (RecyclerView) findViewById(R.id.recyclerView_CollectActivty_pic);

        mImageViewCollectActivityBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_CollectActivity_back:
                finish();
                break;
        }
    }

    /**
     * 自定义用于显示CollecMoive的适配器
     */
    class MyCollectMovieAdapter extends RecyclerView.Adapter<MyCollectMovieAdapter.MovieViewHolder> {

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MovieViewHolder(LayoutInflater.from(CollectActivity.this).inflate(R.layout.activity_collect_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MovieViewHolder holder, final int position) {
            holder.iv.setImageResource(R.mipmap.movie);
            holder.tv_title.setText(listMovie.get(position).getTitle());
            holder.tv_summary.setText(listMovie.get(position).getSummary());

            //设置点击事件跳转到相应界面显示详细内容
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentMovie = new Intent(CollectActivity.this, MovieItemActivity.class);
                    intentMovie.putExtra("item_id", listMovie.get(position).getItem_id());
                    CollectActivity.this.startActivity(intentMovie);
                }
            });

            //设置长按事件弹出对话框显示删除，用于删除已收藏内容
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(CollectActivity.this).inflate(R.layout.custom_dialog_delete, null);
                    final AlertDialog dialog = new AlertDialog.Builder(CollectActivity.this)
                            .setView(view)
                            .show();

                    //进行分享操作
                    view.findViewById(R.id.textView_dialog_share1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showShare(position, listMovie);
                            dialog.dismiss();
                        }
                    });

                    //进行删除操作
                    TextView delete = (TextView) view.findViewById(R.id.textView_dialog_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //根据条件删除相应内容
                            //SQLiteDatabase db = mMySQLiteOpenHelder.getReadableDatabase();
                            db.delete("content", "type = " + listMovie.get(position).getType() + " and item_id = " + listMovie.get(position).getItem_id(), null);
                            initData();
                            dialog.dismiss();
                        }
                    });
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            if (listMovie.size() != 0) {
                mTextViewCollectActivitySubTitleMovie.setVisibility(View.VISIBLE);
            }
            return listMovie.size();
        }

//        @Override
//        public int getItemViewType(int position) {
//            return super.getItemViewType(position);
//        }

        class MovieViewHolder extends RecyclerView.ViewHolder {
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

    /**
     * 自定义用于显示CollectNovel的适配器
     */
    class MyCollectNovelAdapter extends RecyclerView.Adapter<MyCollectNovelAdapter.NovelViewHolder> {

        @Override
        public NovelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NovelViewHolder(LayoutInflater.from(CollectActivity.this).inflate(R.layout.activity_collect_item, null));
        }

        @Override
        public void onBindViewHolder(NovelViewHolder holder, final int position) {

            holder.iv.setImageResource(R.mipmap.novel);
            holder.tv_title.setText(listNovel.get(position).getTitle());
            holder.tv_summary.setText(listNovel.get(position).getSummary());

            //设置点击事件跳转到相应界面显示详细内容
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNovel = new Intent(CollectActivity.this, NovelItemActivity.class);
                    intentNovel.putExtra("item_id", listNovel.get(position).getItem_id());
                    startActivity(intentNovel);
                }
            });

            //设置长按事件弹出对话框显示删除，用于删除已收藏内容
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(CollectActivity.this).inflate(R.layout.custom_dialog_delete, null);
                    final AlertDialog dialog = new AlertDialog.Builder(CollectActivity.this)
                            .setView(view)
                            .show();

                    //进行分享操作
                    view.findViewById(R.id.textView_dialog_share1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showShare(position, listNovel);
                            dialog.dismiss();
                        }
                    });
                    //进行删除操作
                    TextView delete = (TextView) view.findViewById(R.id.textView_dialog_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //根据条件删除相应内容
                            db.delete("content", "type = " + listNovel.get(position).getType() + " and item_id = " + listNovel.get(position).getItem_id(), null);
                            initData();
                            dialog.dismiss();
                        }
                    });
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            if (listNovel.size() != 0) {
                mTextViewCollectActivitySubTitleNovel.setVisibility(View.VISIBLE);
            }
            return listNovel.size();
        }

        class NovelViewHolder extends RecyclerView.ViewHolder {
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

    /**
     * 自定义用于显示CollectPic的适配器
     */
    class MyCollectPicAdapter extends RecyclerView.Adapter<MyCollectPicAdapter.PicViewHolder> {

        @Override
        public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PicViewHolder(LayoutInflater.from(CollectActivity.this).inflate(R.layout.activity_collect_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PicViewHolder holder, final int position) {

            holder.iv.setImageResource(R.mipmap.pic);
            holder.tv_title.setText(listPic.get(position).getTitle());
            holder.tv_summary.setText(listPic.get(position).getSummary());

            //设置点击事件跳转到相应界面显示详细内容
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentPic = new Intent(CollectActivity.this, PicItemActivity.class);
                    intentPic.putExtra("item_id", listPic.get(position).getItem_id());
                    startActivity(intentPic);
                }
            });

            //设置长按事件弹出对话框显示删除，用于删除已收藏内容
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View view = LayoutInflater.from(CollectActivity.this).inflate(R.layout.custom_dialog_delete, null);
                    final AlertDialog dialog = new AlertDialog.Builder(CollectActivity.this)
                            .setView(view)
                            .show();
                    //进行分享操作
                    view.findViewById(R.id.textView_dialog_share1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            showShare(position, listPic);
                        }
                    });

                    //进行删除操作
                    TextView delete = (TextView) view.findViewById(R.id.textView_dialog_delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //根据条件删除相应内容
                            db.delete("content", "type = " + listPic.get(position).getType() + " and item_id = " + listPic.get(position).getItem_id(), null);
                            initData();
                            dialog.dismiss();
                        }
                    });
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            if (listPic.size() != 0) {
                mTextViewCollectActivitySubTitlePic.setVisibility(View.VISIBLE);
            }
            return listPic.size();
        }

        class PicViewHolder extends RecyclerView.ViewHolder {
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

    /**
     * 分享时所调用的方法
     */
    private void showShare(int position, ArrayList<CollectBean> list) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(list.get(position).getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(list.get(position).getSummary());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("iRead");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}
