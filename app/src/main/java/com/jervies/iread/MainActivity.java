package com.jervies.iread;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.jervies.iread.adapter.MyViewPagerAdapterMain;
import com.jervies.iread.fragment.MyFragmentMovie;
import com.jervies.iread.fragment.MyFragmentNovel;
import com.jervies.iread.fragment.MyFragmentPersonal;
import com.jervies.iread.fragment.MyFragmentPic;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewPagerMain;
    private TabLayout mTabLayout;
    private MyViewPagerAdapterMain viewPagerAdapter;

    private String[] tabs = {"影评", "美文", "美图", "我的"};
    private int[] icons = {R.drawable.home_movie, R.drawable.home_novel, R.drawable.home_pic, R.drawable.home_personal};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initViewPagerData();
        initViewPagerAdapter();
        initTabLayout();
    }

    /**
     * 设置TabLayout的选择器显示
     */
    private void initTabLayout() {
        int iconCount = mTabLayout.getTabCount();
        for (int i = 0; i < iconCount; i++) {
            mTabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }

    /**
     * 设置ViewPager适配器
     */
    private void initViewPagerAdapter() {
        viewPagerAdapter = new MyViewPagerAdapterMain(getSupportFragmentManager(), fragments, tabs);
        mViewPagerMain.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPagerMain);  //绑定TabLayout与ViewPager的显示
    }

    /**
     * 初始化ViewPager显示的Fragment数据
     */
    private void initViewPagerData() {
        //影评Fragment
        MyFragmentMovie myFragmentMovie = new MyFragmentMovie();
        fragments.add(myFragmentMovie);
        //美文Fragment
        MyFragmentNovel myFragmentNovel = new MyFragmentNovel();
        fragments.add(myFragmentNovel);
        //美图Fragment
        MyFragmentPic myFragmentPic = new MyFragmentPic();
        fragments.add(myFragmentPic);
        //个人中心Fragment
        MyFragmentPersonal myFragmentPersonal = new MyFragmentPersonal();
        fragments.add(myFragmentPersonal);
    }

    /**
     * 初始化布局显示控件
     */
    private void initView() {
        mViewPagerMain = (ViewPager) findViewById(R.id.viewPager_main);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }
}
