package com.jervies.iread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.NovelItemBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NovelItemActivity extends AppCompatActivity {

    private OkHttpClient mOkHttpClient;
    private NovelItemBean mNovelItemBean;

    private TextView mTextViewNovelItemTitle;
    private TextView mTextViewNovelItemAuthor;
    private TextView mTextViewNovelItemTimes;
    private TextView mTextViewNovelItemSummary;
    private TextView mTextViewNovelItemText;
    private TextView mTextViewNovelItemAuthor1;
    private TextView mTextViewNovelItemAuthorBrief;
    private int item_id;
    private int item_type;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mNovelItemBean = (NovelItemBean) msg.obj;

            initDisplayNovel();     //设置布局控件的内容显示
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_novel_item);
        mOkHttpClient = new OkHttpClient();

        //获取从MyFragmentNovel传递的id和type的值
        Intent intent = getIntent();
        item_id = intent.getIntExtra("item_id", 0);
        item_type = intent.getIntExtra("item_type", 0);

        initView();
        initData();
    }

    /**
     * 通过OkHttp加载网络数据
     */
    private void initData() {
        Request request = new Request.Builder()
                .url(UrlUtils.URLNOVELITEM + item_id)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(NovelItemActivity.this, "美文加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                NovelItemBean novelItemBean = new Gson().fromJson(response.body().string(), NovelItemBean.class);
                Message msg = new Message();
                msg.obj = novelItemBean;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 设置布局控件的内容显示
     */
    private void initDisplayNovel(){
        mTextViewNovelItemTitle.setText(mNovelItemBean.getTitle());
        mTextViewNovelItemAuthor.setText("作者:"+mNovelItemBean.getAuthor());
        mTextViewNovelItemTimes.setText("阅读量:"+mNovelItemBean.getTimes());
        mTextViewNovelItemSummary.setText(mNovelItemBean.getSummary());
        mTextViewNovelItemText.setText(mNovelItemBean.getText());
        mTextViewNovelItemAuthor1.setText(mNovelItemBean.getAuthor());
        mTextViewNovelItemAuthorBrief.setText(mNovelItemBean.getAuthorbrief());
    }

    private void initView() {
        mTextViewNovelItemTitle = (TextView) findViewById(R.id.textView_NovelItem_Title);
        mTextViewNovelItemAuthor = (TextView) findViewById(R.id.textView_NovelItem_author);
        mTextViewNovelItemTimes = (TextView) findViewById(R.id.textView_NovelItem_times);
        mTextViewNovelItemSummary = (TextView) findViewById(R.id.textView_NovelItem_summary);
        mTextViewNovelItemText = (TextView) findViewById(R.id.textView_NovelItem_text);
        mTextViewNovelItemAuthor1 = (TextView) findViewById(R.id.textView_NovelItem_author1);
        mTextViewNovelItemAuthorBrief = (TextView) findViewById(R.id.textView_NovelItem_authorBrief);
    }
}
