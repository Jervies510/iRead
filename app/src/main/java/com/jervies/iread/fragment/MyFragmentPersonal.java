package com.jervies.iread.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jervies.iread.AboutUsActivity;
import com.jervies.iread.CollectActivity;
import com.jervies.iread.FeedBackActivity;
import com.jervies.iread.R;
import com.jervies.iread.bean.PersonalListViewBean;

import java.util.ArrayList;

/**
 * Created by Jervies on 2016/11/16.
 */

public class MyFragmentPersonal extends Fragment {

    private ListView mListView;
    private ImageView mImageViewLogin1;
    private TextView mTextViewLoginUser;

    private ArrayList<PersonalListViewBean> list = new ArrayList<>();
    private int[] listViewItemPics = {R.mipmap.setting_favorite, R.mipmap.setting_font, R.mipmap.setting_feedback, R.mipmap.setting_aboutus};
    private String[] listViewItemTitles = {"我的收藏", "字体设置", "反馈意见", "关于iRead"};
    private MyListViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListViewData();
        initListViewAdapter();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), CollectActivity.class));
                        break;
                    case 1:

                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), FeedBackActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), AboutUsActivity.class));
                        break;
                }
            }
        });
    }

    /**
     * 初始化ListView适配器并设置
     */
    private void initListViewAdapter() {
        adapter = new MyListViewAdapter();
        mListView.setAdapter(adapter);
    }

    /**
     * 初始化ListView显示数据
     */
    private void initListViewData() {
        list.clear();   //为了修改在滑动ViewPager是自动添加数据的bug
        for (int i = 0; i < listViewItemTitles.length; i++) {
            PersonalListViewBean personalListViewBean = new PersonalListViewBean(listViewItemPics[i], listViewItemTitles[i]);
            list.add(personalListViewBean);
        }
    }

    private void initView(View view) {
        mImageViewLogin1 = (ImageView) view.findViewById(R.id.imageView_login1);
        mTextViewLoginUser = (TextView) view.findViewById(R.id.textView_LoginUser);
        mListView = (ListView) view.findViewById(R.id.listView);
    }

    /**
     * 定义ListView的适配器
     */
    class MyListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_listview_item_personal, parent, false);
                holder = new ViewHolder();
                holder.iv_left = (ImageView) convertView.findViewById(R.id.ImageView_listView_item_left);
                holder.tv_title = (TextView) convertView.findViewById(R.id.textView_listView_item);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.iv_left.setImageResource(listViewItemPics[position]);
            holder.tv_title.setText(listViewItemTitles[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_left;
            TextView tv_title;
        }
    }
}