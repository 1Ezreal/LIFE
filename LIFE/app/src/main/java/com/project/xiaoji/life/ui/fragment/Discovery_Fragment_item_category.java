package com.project.xiaoji.life.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.project.xiaoji.life.APIManager;
import com.project.xiaoji.life.Utils.SpacesItemDecoration;
import com.project.xiaoji.life.adpter.DiscoveryCgtegoryAdapter;
import com.project.xiaoji.life.bean.DiscoveryCategoryBean;
import com.project.xiaoji.life.inter.IDiscoveryCategoryDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class Discovery_Fragment_item_category extends MyBaseFragment {


    private List<DiscoveryCategoryBean.DataBean.CategoriesBean> list;
    private DiscoveryCgtegoryAdapter adapter;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.addItemDecoration(new SpacesItemDecoration(0));
        getData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(0);
            }
        });
    }

    public void getData() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIManager.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IDiscoveryCategoryDataListener listener = retrofit.create(IDiscoveryCategoryDataListener.class);
        Call<DiscoveryCategoryBean> data = listener.getDiscoveryCategoryData();
        data.enqueue(new Callback<DiscoveryCategoryBean>() {
            @Override
            public void onResponse(Call<DiscoveryCategoryBean> call, Response<DiscoveryCategoryBean> response) {
                if (response.isSuccess()){
                    list = response.body().getData().getCategories();
                }
                if (null!=getContext()){
                    adapter = new DiscoveryCgtegoryAdapter(getContext(),list);
                    rv.setAdapter(adapter);

                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Call<DiscoveryCategoryBean> call, Throwable t) {
            }
        });

    }

}
