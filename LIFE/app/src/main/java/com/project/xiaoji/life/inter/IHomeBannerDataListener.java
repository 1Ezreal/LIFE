package com.project.xiaoji.life.inter;

import com.project.xiaoji.life.bean.HomeBannerBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public interface IHomeBannerDataListener {
    ///v2/collections/14/posts?limit=20&offset=0
    @GET("v2/collections/{id}/posts?limit=20&offset=0")
    Call<HomeBannerBean> getHomeBannerData(@Path("id") String id);
}
