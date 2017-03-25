package com.project.xiaoji.life.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xiaoji.life.APIManager;
import com.project.xiaoji.life.R;
import com.project.xiaoji.life.adpter.HomeBannerAdapter;
import com.project.xiaoji.life.bean.HomeBannerBean;
import com.project.xiaoji.life.inter.IHomeBannerDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class Home_Banner_Item_Activity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private TextView tv_title;
    private RecyclerView rv;
    private List<HomeBannerBean.DataBean.PostsBean> list;
    private HomeBannerAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (null!=adapter){
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 沉浸 状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_banner_item);
        initViews();

        getData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(0);
            }
        });

    }

    private void initViews() {
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rv = (RecyclerView) findViewById(R.id.rv);
    }


    public void backClick(View view) {
        finish();
    }

    public void share(View view) {
        Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    public void getData() {
        String id = getIntent().getStringExtra("urlId");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIManager.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IHomeBannerDataListener listener = retrofit.create(IHomeBannerDataListener.class);
        Call<HomeBannerBean> data = listener.getHomeBannerData(id);
        data.enqueue(new Callback<HomeBannerBean>() {
            @Override
            public void onResponse(Call<HomeBannerBean> call, Response<HomeBannerBean> response) {
                if (response.isSuccess()) {
                    tv_title.setText(response.body().getData().getTitle());
                    list = response.body().getData().getPosts();
                }
                adapter = new HomeBannerAdapter(Home_Banner_Item_Activity.this, list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Home_Banner_Item_Activity.this, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Call<HomeBannerBean> call, Throwable t) {
                Toast.makeText(Home_Banner_Item_Activity.this, "数据加载异常,请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
