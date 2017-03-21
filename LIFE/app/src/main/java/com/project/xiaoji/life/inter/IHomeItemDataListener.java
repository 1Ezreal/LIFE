package com.project.xiaoji.life.inter;

import com.project.xiaoji.life.bean.HomeItemBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2015/1/1 0001.
 */

public interface IHomeItemDataListener {

    @GET("v2/home/{id}?offset=20&limit=40")
    Call<HomeItemBean> getHomeItemData(@Path("id") String index );
}
