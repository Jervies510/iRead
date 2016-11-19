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
import com.jervies.iread.adapter.MyCollectMovieAdapter;
import com.jervies.iread.bean.CollectBean;
import com.jervies.iread.helpClass.MySQLiteOpenHelder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewCollectActivityBack;
    private RecyclerView mRecyclerViewCollectActivtyMovie;
    private MySQLiteOpenHelder mMySQLiteOpenHelder;

    private ArrayList<CollectBean> list = new ArrayList<>();
    
    private SQLiteDatabase db;
    private TextView mTextViewCollectActivitySubTitleMovie;
    private TextView mTextViewCollectActivitySubTitleNovel;
    private RecyclerView mRecyclerViewCollectActivtyNovel;
    private TextView mTextViewCollectActivitySubTitlePic;
    private RecyclerView mRecyclerViewCollectActivtyPic;
    private MyCollectMovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
        initView();
        mRecyclerViewCollectActivtyMovie.setLayoutManager(new LinearLayoutManager(this));
        initData();
        initAdapter();
    }

    /**
     * 初始化RecyclerView的适配器
     */
    private void initAdapter() {
        adapter = new MyCollectMovieAdapter(this,list,mMySQLiteOpenHelder);
        mRecyclerViewCollectActivtyMovie.setAdapter(adapter);
    }

    /**
     * 1.初始化RecyclerView的显示数据
     * 2.从数据库Content表中进行读取数据
     */
    public void initData() {
        list.clear();
        mMySQLiteOpenHelder = new MySQLiteOpenHelder(this,"iRead",null,1);
        db = mMySQLiteOpenHelder.getReadableDatabase();
        Cursor cursor = db.query("content", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int type = cursor.getInt(cursor.getColumnIndex("type"));
            int item_id = cursor.getInt(cursor.getColumnIndex("item_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String summary = cursor.getString(cursor.getColumnIndex("summary"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            list.add(new CollectBean(_id, type, item_id, title, summary, image));
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
}
