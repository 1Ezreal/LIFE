package com.project.xiaoji.life.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.project.xiaoji.life.APIManager;
import com.project.xiaoji.life.Utils.SpacesItemDecoration;
import com.project.xiaoji.life.adpter.HomeAdapter;
import com.project.xiaoji.life.bean.HomeItemBean;
import com.project.xiaoji.life.inter.IHomeItemDataListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

   private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    if (adapter!=null){
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                }
                break;
                case 1: {
                    swipeRefresh.setRefreshing(false);
                }
                break;
            }
        }
    };

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


    public void getData() {
        OkHttpClient httpClient = new OkHttpClient.Builder()

                .addInterceptor(new Interceptor() {

                    @Override

                    public okhttp3.Response intercept(Chain chain) throws IOException {

                        /*GET /v2/home/0?offset=0&limit=20 HTTP/1.1
                        X-BUILD: 27
                        X-CHAN: meizu
                        X-NETCLS: WIFI
                        X-OS: Android 23
                        X-UDID: c9a1255e4cb79837
                        X-VER: 3.0.1
                        X-APP: me.mglife.android
                        X-DEV: SM-C5000

                        User-Agent: mglife/27 (SM-C5000; Android 6.0.1) Mobile
                        Accept: application/json;versions=1
                        Cache-Control: public, no-cache
                        Host: api.mglife.me
                        Connection: Keep-Alive
                        Accept-Encoding: gzip
                        Cookie: session=a72885cf-a707-4dcf-9f41-103d399418c9*/
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("X-BUILD", "27")
                                .addHeader("X-CHAN", "meizu")
                                .addHeader("X-NETCLS", "WIFI")
                                .addHeader("X-OS", "Android 23")
                                .addHeader("X-UDID", "c9a1255e4cb79837")
                                .addHeader("X-VER", "3.0.1")
                                .addHeader("X-APP", "me.mglife.android")
                                .addHeader("X-DEV", "SM-C5000")
                                .addHeader("User-Agent", "mglife/27 (SM-C5000; Android 6.0.1) Mobile")
                                .addHeader("Accept", "application/json;versions=1")
                                .addHeader("Cache-Control", "public, no-cache")
                                .addHeader("Connection", "Keep-Alive")
                                .addHeader("Cookie", "session=a72885cf-a707-4dcf-9f41-103d399418c9")
                                .addHeader("Accept-Encoding", "gzip")
                                .build();

                        return chain.proceed(request);

                    }


                })

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIManager.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient)
                .build();
        IHomeItemDataListener listener = retrofit.create(IHomeItemDataListener.class);
        Call<HomeItemBean> data = listener.getHomeItemData(getArguments().getString("index"),"20", "20");
        data.enqueue(new Callback<HomeItemBean>() {
            @Override
            public void onResponse(Call<HomeItemBean> call, Response<HomeItemBean> response) {
                if (response.isSuccess()) {
                    list = response.body().getData().getHome_list();
                }
                if (null != getContext()&&list.size()>0) {

                    adapter = new HomeAdapter(getContext(), list);
                }
                rv.setAdapter(adapter);

                handler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Call<HomeItemBean> call, Throwable t) {
                handler.sendEmptyMessage(1);


            }
        });
    }
}
