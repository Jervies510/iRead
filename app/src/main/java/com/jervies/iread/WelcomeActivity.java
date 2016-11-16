package com.jervies.iread;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    private Button mButtonClickRead;
    private ViewPager mViewPagerWelcome;
    private ArrayList<ImageView> list = new ArrayList<>();
    private MyWelcomeAdapter adapter;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        mSharedPreferences = getSharedPreferences("firstRun",MODE_PRIVATE);
        isJudgeFirstRun();  //判断该应用是否第一次运行
        initView();
        initData();
        initAdapter();

        //给ViewPager设置监听事件，当滑到第三页时显示点击阅读按钮
        mViewPagerWelcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size()-1) {
                    mButtonClickRead.setVisibility(View.VISIBLE);
                    mButtonClickRead.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                            WelcomeActivity.this.finish();
                        }
                    });
                }else {
                    mButtonClickRead.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //该程序第一运行后将key的值置为true
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean("key",true);
        edit.commit();
    }

    /**
     * 用来判断该程序是否第一次在该设备上运行
     */
    private void isJudgeFirstRun() {
        boolean key = mSharedPreferences.getBoolean("key", false);
        if (key) {
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            WelcomeActivity.this.finish();
        }
    }

    private void initAdapter() {
        adapter = new MyWelcomeAdapter();
        mViewPagerWelcome.setAdapter(adapter);
    }

    /**
     * 初始化ViewPager显示数据
     */
    private void initData() {
        ImageView iv1 = new ImageView(this);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        iv1.setImageResource(R.mipmap.welcomepager1);
        list.add(iv1);

        ImageView iv2 = new ImageView(this);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        iv2.setImageResource(R.mipmap.welcomepager1);
        list.add(iv2);

        ImageView iv3 = new ImageView(this);
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);
        iv3.setImageResource(R.mipmap.welcomepager1);
        list.add(iv3);
    }

    private void initView() {
        mViewPagerWelcome = (ViewPager) findViewById(R.id.viewPager_welcome);
        mButtonClickRead = (Button) findViewById(R.id.button_clickRead);
    }

    /**
     * 定义ViewPager的适配器
     */
    class MyWelcomeAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }
}
