package com.jervies.iread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageViewCollectActivityBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_us);
        initView();
    }

    private void initView() {
        mImageViewCollectActivityBack = (ImageView) findViewById(R.id.imageView_CollectActivity_back);

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
