package com.jervies.iread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.MovieItemBean;
import com.squareup.picasso.Picasso;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

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
    private LoadingDialog mLoadingDialog;
    private int item_id;    //每篇影评所对应的id值
    private int item_type;  //用于区别是影评，美文，美图的类型

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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mMovieItemBean = (MovieItemBean) msg.obj;
            initDisplayMovie();   //调用该方法用于设置空间显示数据信息
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_movie_item);
        Intent intent = getIntent();
        item_id = intent.getIntExtra("item_id", 0);    //获取从Fragment中传递来的数据-每篇影评所对应的id值
        item_type = intent.getIntExtra("item_type",0);

        mOkHttpClient = new OkHttpClient();
        initView();
        initData();     //从网络中加载数据
    }

    /**
     * 通过OkHttp加载网络数据，并添加到list集合中
     */
    private void initData() {

        //网络加载数据时弹出正在加载数据对话框
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setLoadingText("正在加载...")
                .setSize(80)
                .setShowTime(500)   //加载成功或失败动画后显示的时间
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)    //加载成功或失败动画的速度快慢
                .setInterceptBack(false)    //拦截返回按钮效果
                .show();

        //网络加载数据
        Request request = new Request.Builder()
                .url(UrlUtils.URLMOVIEITEM + item_id)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.close();
                        finish();
                        Toast.makeText(MovieItemActivity.this, "无网络嘞，你查查", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.close();
                    }
                });
                final MovieItemBean movieItemBean = new Gson().fromJson(response.body().string(), MovieItemBean.class);
                //Log.d("tmd", "onResponse: movieItemBean :" + movieItemBean.toString());
                Message msg = new Message();
                msg.obj = movieItemBean;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 设置布局控件显示信息
     */
    private void initDisplayMovie() {
        Log.d("tmd", "验证initDisplayContent: 是否运行");
        Log.d("tmd", "initDisplayContent: mMovieItemBean显示 :" +mMovieItemBean.toString());

        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImageforplay()).into(mImageViewMovieItemImageforplay);
        mTextViewMovieItemTitle.setText(mMovieItemBean.getTitle());
        mTextViewMovieItemAuthor.setText("作者:"+mMovieItemBean.getAuthor());
        mTextViewMovieItemTimes.setText("阅读量:"+mMovieItemBean.getTimes());
        mTextViewMovieItemText1.setText(mMovieItemBean.getText1());
        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage1()).into(mImageViewMoiveImageView1);
        mTextViewMovieItemText2.setText(mMovieItemBean.getText2());
        mTextViewMovieItemRealTitle.setText(mMovieItemBean.getRealtitle());
        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage2()).into(mImageViewMoiveImageView2);
        mTextViewMovieItemText3.setText(mMovieItemBean.getText3());
        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage3()).into(mImageViewMoiveImageView3);
        mTextViewMovieItemText4.setText(mMovieItemBean.getText4());
        Picasso.with(this).load(UrlUtils.URLPICTURE + mMovieItemBean.getImage4()).into(mImageViewMoiveImageView4);
        mTextViewMovieItemText5.setText(mMovieItemBean.getText5());
        mTextViewMovieItemAuthor1.setText(mMovieItemBean.getAuthor());
        mTextViewMovieItemAuthorBrief.setText(mMovieItemBean.getAuthorbrief());
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
