package com.project.xiaoji.life.inter;

import com.project.xiaoji.life.bean.HomeItemBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2015/1/1 0001.
 */

public interface IHomeItemDataListener {
//    http://api.mglife.me/v2/home/0?offset=0&limit=20

    @GET("v2/home/{index}?")
    Call<HomeItemBean> getHomeItemData(@Path("index") String index, @Query("offset") String offset, @Query("limit") String limit);
}
