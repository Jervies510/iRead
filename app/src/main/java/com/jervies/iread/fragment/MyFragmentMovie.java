package com.jervies.iread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.R;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.MovieBean;
import com.squareup.picasso.Picasso;

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

public class MyFragmentMovie extends Fragment {

    private RecyclerView mRecyclerView;
    private OkHttpClient mOkHttpClient;

    private ArrayList<MovieBean.ResultBean> list = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content,container,false);
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
        adapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 通过OkHttp框架加载网络数据
     */
    private void initRecyclerViewData() {
        final Request request = new Request.Builder()
                .url(UrlUtils.URLMOVIE)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(), "内容加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log.d("tmd", "onResponse: response的加载结果是：" +response.body().string());
                MovieBean movieBean = new Gson().fromJson(response.body().string(), MovieBean.class);
                list.clear();
                list.addAll(movieBean.getResult());
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
    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recyclerview_item_movie,parent,false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_title.setText(list.get(position).getTitle());
            Picasso.with(getActivity()).load(UrlUtils.URLPICTURE + list.get(position).getImage()).into(holder.iv);  //通过Picasso设置显示图片
            holder.tv_summary.setText(list.get(position).getSummary());

            //格式化显示内容更新日期
            Date date = new Date(System.currentTimeMillis()-24*3600*1000*position);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E");
            String format = dateFormat.format(date);
            holder.tv_date_movie.setText(format);

            //添加点击事件，并跳转页面显示全文
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv_title;
            ImageView iv;
            TextView tv_summary;
            TextView tv_date_movie;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv_title = (TextView)itemView.findViewById(R.id.textView_title_item);
                iv = (ImageView)itemView.findViewById(R.id.imageView_item);
                tv_summary = (TextView)itemView.findViewById(R.id.textView_summary_item);
                tv_date_movie =(TextView)itemView.findViewById(R.id.textView_data_item_movie);
            }
        }
    }
}
