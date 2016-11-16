package com.jervies.iread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.MovieItemBean;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieItemActivity extends AppCompatActivity {

    private MovieItemBean mMovieItemBean;
    private OkHttpClient mOkHttpClient;
    private int item_id;    //每篇影评所对应的id值

    private ArrayList<MovieItemBean> list = new ArrayList<>();

    private ImageView mImageViewMovieItemImageforplay;
    private TextView mTextViewMovieItemTitle;
    private TextView mTextViewMovieItemAuthor;
    private TextView mTextViewMovieItemTimes;
    private TextView mTextViewMovieItemText1;
    private ImageView mImageViewMoiveImageView1;
    private TextView mTextViewMovieItemText2;
    private TextView mTextViewMovieItemRealTitle;
    private ImageView mImageViewMoiveImageView2;
    private TextView mTextViewMovieItemText3;
    private ImageView mImageViewMoiveImageView3;
    private TextView mTextViewMovieItemText4;
    private ImageView mImageViewMoiveImageView4;
    private TextView mTextViewMovieItemText5;
    private TextView mTextViewMovieItemAuthor1;
    private TextView mTextViewMovieItemAuthorBrief;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        initView();


        item_id = getIntent().getIntExtra("item_id", 0);    //获取从Fragment中传递来的数据-每篇影评所对应的id值
        //Log.d("tmd", "onCreate: item_id:" + item_id);
        mOkHttpClient = new OkHttpClient();

        initData();     //从网络中加载数据
//        initDisplayContent();
    }

    private void initDisplayContent() {
        mMovieItemBean = list.get(0);
        Log.d("tmd", "initDisplayContent: mMovieItemBean :" +mMovieItemBean.toString());
//        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImageforplay()).into(mImageViewMovieItemImageforplay);
//        mTextViewMovieItemTitle.setText(mMovieItemBean.getTitle());
//        mTextViewMovieItemAuthor.setText(mMovieItemBean.getAuthor());
//        mTextViewMovieItemTimes.setText(mMovieItemBean.getTimes());
//        mTextViewMovieItemText1.setText(mMovieItemBean.getText1());
//        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage1()).into(mImageViewMoiveImageView1);
//        mTextViewMovieItemText2.setText(mMovieItemBean.getText2());
//        mTextViewMovieItemRealTitle.setText(mMovieItemBean.getRealtitle());
//        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage2()).into(mImageViewMoiveImageView2);
//        mTextViewMovieItemText3.setText(mMovieItemBean.getText3());
//        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage3()).into(mImageViewMoiveImageView3);
//        mTextViewMovieItemText4.setText(mMovieItemBean.getText4());
//        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage4()).into(mImageViewMoiveImageView4);
//        mTextViewMovieItemText5.setText(mMovieItemBean.getText5());
//        mTextViewMovieItemAuthor1.setText(mMovieItemBean.getAuthor());
//        mTextViewMovieItemAuthorBrief.setText(mMovieItemBean.getAuthorbrief());
    }

    /**
     * 通过OkHttp加载网络数据，并添加到list集合中
     */
    private void initData() {
        Request request = new Request.Builder()
                .url(UrlUtils.URLMOVIEITEM + item_id)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MovieItemActivity.this, "内容加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d("tmd", "onResponse: response:" + response.body().string());
                MovieItemBean movieItemBean = new Gson().fromJson(response.body().string(), MovieItemBean.class);
                list.add(movieItemBean);
                //Log.d("tmd", "onResponse: listString: " +list.get(0).toString());
            }
        });

    }

    private void initView() {
        mImageViewMovieItemImageforplay = (ImageView) findViewById(R.id.imageView_MovieItem_Imageforplay);
        mTextViewMovieItemTitle = (TextView) findViewById(R.id.textView_MovieItem_Title);
        mTextViewMovieItemAuthor = (TextView) findViewById(R.id.textView_MovieItem_author);
        mTextViewMovieItemTimes = (TextView) findViewById(R.id.textView_MovieItem_times);
        mTextViewMovieItemText1 = (TextView) findViewById(R.id.textView_MovieItem_text1);
        mImageViewMoiveImageView1 = (ImageView) findViewById(R.id.imageView_Moive_imageView1);
        mTextViewMovieItemText2 = (TextView) findViewById(R.id.textView_MovieItem_text2);
        mTextViewMovieItemRealTitle = (TextView) findViewById(R.id.textView_MovieItem_realTitle);
        mImageViewMoiveImageView2 = (ImageView) findViewById(R.id.imageView_Moive_imageView2);
        mTextViewMovieItemText3 = (TextView) findViewById(R.id.textView_MovieItem_text3);
        mImageViewMoiveImageView3 = (ImageView) findViewById(R.id.imageView_Moive_imageView3);
        mTextViewMovieItemText4 = (TextView) findViewById(R.id.textView_MovieItem_text4);
        mImageViewMoiveImageView4 = (ImageView) findViewById(R.id.imageView_Moive_imageView4);
        mTextViewMovieItemText5 = (TextView) findViewById(R.id.textView_MovieItem_text5);
        mTextViewMovieItemAuthor1 = (TextView) findViewById(R.id.textView_MovieItem_author1);
        mTextViewMovieItemAuthorBrief = (TextView) findViewById(R.id.textView_MovieItem_authorBrief);
    }
}
