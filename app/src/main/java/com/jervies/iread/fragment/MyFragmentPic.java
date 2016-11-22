package com.jervies.iread.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jervies.iread.PicItemActivity;
import com.jervies.iread.R;
import com.jervies.iread.UrlUtils.UrlUtils;
import com.jervies.iread.bean.PicBean;
import com.jervies.iread.helpClass.MySQLiteOpenHelder;
import com.squareup.picasso.Picasso;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jervies on 2016/11/16.
 */

public class MyFragmentPic extends Fragment {

    private RecyclerView mRecyclerView;
    private OkHttpClient mOkHttpClient;
    private MySQLiteOpenHelder mMySQLiteOpenHelder;
    private SQLiteDatabase db;
    private TextView title;

    private ArrayList<PicBean.ResultBean> list = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;
    private LoadingDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOkHttpClient = new OkHttpClient();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMySQLiteOpenHelder = new MySQLiteOpenHelder(getActivity(), "iRead", null, 1);
        db = mMySQLiteOpenHelder.getReadableDatabase();

        //设置显示标题栏的显示内容
        title = (TextView) view.findViewById(R.id.textView_TitleBar);
        title.setText("美图精选");

        initRecyclerViewData();
        initReCyclerViewAdapter();
    }

    /**
     * 初始化RecyclerView的适配器
     */
    private void initReCyclerViewAdapter() {
        adapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 通过OkHttp加载网络数据并设置显示
     */
    private void initRecyclerViewData() {
        //网络加载数据时弹出正在加载数据对话框
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.setLoadingText("正在加载...")
                .setSize(80)
                .setShowTime(500)   //加载成功或失败动画后显示的时间
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)    //加载成功或失败动画的速度快慢
                .setInterceptBack(false)    //拦截返回按钮效果
                .show();

        //网络请求
        Request request = new Request.Builder()
                .url(UrlUtils.URLPIC)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.close();
                        Toast.makeText(getActivity(), "内容加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.close();
                    }
                });

                PicBean picBean = new Gson().fromJson(response.body().string(), PicBean.class);
                list.clear();
                list.addAll(picBean.getResult());
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
    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyPicViewHolder> {

        @Override
        public MyPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyPicViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recyclerview_item_pic, parent, false));
        }

        @Override
        public void onBindViewHolder(MyPicViewHolder holder, final int position) {
            holder.tv_title_pic.setText(list.get(position).getTitle());
            Picasso.with(getActivity()).load(UrlUtils.URLPICTURE + list.get(position).getImage()).into(holder.iv_pic);
            holder.tv_summary_Pic.setText(list.get(position).getSummary());

            //格式化并显示发布时间
            Date date = new Date(System.currentTimeMillis() - 24 * 3600 * 1000 * position);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E");
            String format = dateFormat.format(date);
            holder.tv_date_pic.setText(format);

            //实现页面的跳转，用来查看PicItemActivity页面显示的美图信息
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PicItemActivity.class);
                    intent.putExtra("item_id", list.get(position).getId());
                    intent.putExtra("item_type", list.get(position).getType());
                    startActivity(intent);
                }
            });

            //给Item添加长按监听事件，用于显示自定义AlertDialog
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    View customDialog_View = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog, null);
                    final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setView(customDialog_View)
                            .show();
                    //设置Dialog的显示标题
                    TextView dialogTitle = (TextView) customDialog_View.findViewById(R.id.textView_CustomDialog_title);
                    dialogTitle.setText("美图精选");

                    //设置收藏按钮的点击事件
                    customDialog_View.findViewById(R.id.textView_dialog_collect).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ContentValues values = new ContentValues();
                            values.put("type", list.get(position).getType());
                            values.put("item_id", list.get(position).getId());
                            values.put("title", list.get(position).getTitle());
                            values.put("summary", list.get(position).getSummary());
                            values.put("image", list.get(position).getImage());
                            db.insert("content", null, values);
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "美图已收藏", Toast.LENGTH_SHORT).show();

                        }
                    });
                    //设置分享按钮的点击事件
                    customDialog_View.findViewById(R.id.textView_dialog_share).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showShare(position);
                            //Toast.makeText(getActivity(), "美图已分享", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyPicViewHolder extends RecyclerView.ViewHolder {

            TextView tv_title_pic;
            ImageView iv_pic;
            TextView tv_summary_Pic;
            TextView tv_date_pic;

            public MyPicViewHolder(View itemView) {
                super(itemView);
                tv_title_pic = (TextView) itemView.findViewById(R.id.textView_title_item_pic);
                iv_pic = (ImageView) itemView.findViewById(R.id.imageView_item_Pic);
                tv_summary_Pic = (TextView) itemView.findViewById(R.id.textView_summary_item_pic);
                tv_date_pic = (TextView) itemView.findViewById(R.id.textView_data_item_Pic);
            }
        }
    }

    /**
     * 分享时所调用的方法
     */
    private void showShare(int position) {
        ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(list.get(position).getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(list.get(position).getSummary());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("iRead");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(getActivity());
    }
}
