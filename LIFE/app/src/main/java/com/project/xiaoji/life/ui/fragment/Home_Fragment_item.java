package com.project.xiaoji.life.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.project.xiaoji.life.adpter.HomeAdapter;
import com.project.xiaoji.life.bean.HomeItemBean;
import com.project.xiaoji.life.inter.IHomeItemDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/18 0018.
 */

public class Home_Fragment_item extends MyBaseFragment {

    private List<HomeItemBean.DataBean.HomeListBean> list;
    private HomeAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();


    }


    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.mglife.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IHomeItemDataListener listener = retrofit.create(IHomeItemDataListener.class);
        Call<HomeItemBean> data = listener.getHomeItemData(getArguments().getString("index"));
        data.enqueue(new Callback<HomeItemBean>() {
            @Override
            public void onResponse(Call<HomeItemBean> call, Response<HomeItemBean> response) {
                if (response.isSuccess()) {
                    list = response.body().getData().getHome_list();
                }
                if (null!=getContext()){

                    adapter = new HomeAdapter(getContext(), list);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<HomeItemBean> call, Throwable t) {

            }
        });
    }
}
