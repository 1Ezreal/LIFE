package com.project.xiaoji.life.inter;

import com.project.xiaoji.life.bean.DiscoveryCategoryBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public interface IDiscoveryCategoryDataListener {
    @GET("v2/item_categories/tree")
    Call<DiscoveryCategoryBean> getDiscoveryCategoryData();
}
