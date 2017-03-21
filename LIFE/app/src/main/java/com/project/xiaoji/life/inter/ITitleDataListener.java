package com.project.xiaoji.life.inter;

import com.project.xiaoji.life.bean.TittleBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/3/18 0018.
 */
public interface ITitleDataListener {

    @GET("v2/time_axis")
    Call<TittleBean> getTitleData();

}
