package com.jervies.iread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.R;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.NovelBean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jervies on 2016/11/15.
 */

public class MyFragmentNovel extends Fragment {

    private RecyclerView mRecyclerView;
    private OkHttpClient mOkHttpClient;

    private ArrayList<NovelBean.ResultBean> list = new ArrayList<>();
    private MyRecyclerViewNovelAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_content,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOkHttpClient = new OkHttpClient();
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initRecyclerViewData();
        initRecyclerViewAdapter();
    }

    /**
     * 设置适配器
     */
    private void initRecyclerViewAdapter() {
        adapter = new MyRecyclerViewNovelAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 通过OkHttp框架加载网络数据并初始化显示数据
     */
    private void initRecyclerViewData() {
        final Request request = new Request.Builder()
                .url(UrlUtils.URLNOVEL)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "内容加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//              Log.d("tmd", "onResponse: response的加载结果是：" +response.body().string());
                NovelBean novelBean = new Gson().fromJson(response.body().string(), NovelBean.class);
                list.addAll(novelBean.getResult());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * 定义RecyclerView的适配器
     */
    class MyRecyclerViewNovelAdapter extends RecyclerView.Adapter<MyRecyclerViewNovelAdapter.MyNovelViewHolder>{

        @Override
        public MyNovelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyNovelViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recyclerview_item_novel,parent,false));
        }

        @Override
        public void onBindViewHolder(MyNovelViewHolder holder, int position) {
            holder.tv_title_novel.setText(list.get(position).getTitle());
            holder.tv_summary_novel.setText(list.get(position).getSummary());

            //格式化显示内容更新日期
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat();
            String format = dateFormat.format(date);
            holder.tv_date_novel.setText(format);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyNovelViewHolder extends RecyclerView.ViewHolder{
            TextView tv_title_novel;
            TextView tv_summary_novel;
            TextView tv_date_novel;
            public MyNovelViewHolder(View itemView) {
                super(itemView);
                tv_title_novel = (TextView)itemView.findViewById(R.id.textView_title_item_novel);
                tv_summary_novel = (TextView)itemView.findViewById(R.id.textView_summary_item_novel);
                tv_date_novel = (TextView)itemView.findViewById(R.id.textView_date_item_novel);
            }
        }
    }
}
