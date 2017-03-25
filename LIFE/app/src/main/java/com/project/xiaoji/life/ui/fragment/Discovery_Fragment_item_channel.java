package com.project.xiaoji.life.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.project.xiaoji.life.APIManager;
import com.project.xiaoji.life.Utils.SpacesItemDecoration;
import com.project.xiaoji.life.adpter.DiscoveryChannelAdapter;
import com.project.xiaoji.life.bean.DiscoveryChannelBean;
import com.project.xiaoji.life.inter.IDiscoveryChannelDataListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class Discovery_Fragment_item_channel extends MyBaseFragment{

    private List<DiscoveryChannelBean.DataBean.ChannelBean> list=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (null!=adapter){
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        }
    };
    private DiscoveryChannelAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(0);
            }
        });
        rv.addItemDecoration(new SpacesItemDecoration(40));
    }

    private void getData() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIManager.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IDiscoveryChannelDataListener listener = retrofit.create(IDiscoveryChannelDataListener.class);
        Call<DiscoveryChannelBean> data = listener.getDiscoveryChannelData();
        data.enqueue(new Callback<DiscoveryChannelBean>() {
            @Override
            public void onResponse(Call<DiscoveryChannelBean> call, Response<DiscoveryChannelBean> response) {
                if (response.isSuccess()){
                    list = response.body().getData().getChannel();
                }
                if (null!=getContext()){
                    adapter = new DiscoveryChannelAdapter(getContext(),list);
                    rv.setAdapter(adapter);
                    handler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onFailure(Call<DiscoveryChannelBean> call, Throwable t) {

            }
        });
    }


}
