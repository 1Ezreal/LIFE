package com.project.xiaoji.life.inter;

import com.project.xiaoji.life.bean.DiscoveryChannelBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public interface IDiscoveryChannelDataListener {
    @GET("v2/special_channel?offset=0&limit=20")

    Call<DiscoveryChannelBean> getDiscoveryChannelData();
}
