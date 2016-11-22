package com.jervies.iread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.PicItemBean;
import com.squareup.picasso.Picasso;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PicItemActivity extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    private PicItemBean mPicItemBean;
    private LoadingDialog mLoadingDialog;

    private int item_type;
    private int item_id;
    private ImageView mImageViewPicItemImageforplay;
    private TextView mTextViewPicItemTitle;
    private TextView mTextViewPicItemAuthorBrief;
    private TextView mTextViewPicItemText1;
    private TextView mTextViewPicItemText2;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mPicItemBean = (PicItemBean) msg.obj;
            initDisplayPic();   //设置布局中控件的显示内容
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pic_item);
        mOkHttpClient = new OkHttpClient();

        //获取MyFragmentPic跳转页面时传递的pic对应的id值和type的值
        Intent intent = getIntent();
        item_id = intent.getIntExtra("item_id", 0);
        item_type = intent.getIntExtra("item_type", 0);

        initView();
        initData();
    }

    /**
     * 通过OkHttp网络请求加载数据
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
                .url(UrlUtils.URLPICITEM + item_id)
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
                        Toast.makeText(PicItemActivity.this, "无网络嘞，你查查", Toast.LENGTH_SHORT).show();
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
                PicItemBean picItemBean = new Gson().fromJson(response.body().string(), PicItemBean.class);

                Message msg = new Message();
                msg.obj = picItemBean;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 设置布局中控件的显示内容
     */
    private void initDisplayPic(){
        Picasso.with(this).load(UrlUtils.URLPICTURE + mPicItemBean.getImage1()).into(mImageViewPicItemImageforplay);
        mTextViewPicItemTitle.setText(mPicItemBean.getTitle());
        mTextViewPicItemAuthorBrief.setText(mPicItemBean.getAuthorbrief());
        mTextViewPicItemText1.setText(mPicItemBean.getText1());
        mTextViewPicItemText2.setText(mPicItemBean.getText2());
    }

    private void initView() {
        mImageViewPicItemImageforplay = (ImageView) findViewById(R.id.imageView_PicItem_Imageforplay);
        mTextViewPicItemTitle = (TextView) findViewById(R.id.textView_PicItem_Title);
        mTextViewPicItemAuthorBrief = (TextView) findViewById(R.id.textView_PicItem_authorBrief);
        mTextViewPicItemText1 = (TextView) findViewById(R.id.textView_PicItem_text1);
        mTextViewPicItemText2 = (TextView) findViewById(R.id.textView_PicItem_text2);
    }
}
