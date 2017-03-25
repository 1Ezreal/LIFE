package com.project.xiaoji.life.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.DiscoveryChannelBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class DiscoveryChannelItemAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<DiscoveryChannelBean.DataBean.ChannelBean.TalentChannelBean> list;
    LayoutInflater inflater;

    public DiscoveryChannelItemAdapter1(Context context, List<DiscoveryChannelBean.DataBean.ChannelBean.TalentChannelBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_discovery_channel_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        DiscoveryChannelBean.DataBean.ChannelBean.TalentChannelBean bean = list.get(position);
        Picasso.with(context).load(bean.getCover_image_url()).into(myViewHolder.iv_max);
        Picasso.with(context).load(bean.getAuthor_icon()).into(myViewHolder.iv_biglogo);
        myViewHolder.tv_title.setText(bean.getName());
        myViewHolder.tv_sub_title.setText(bean.getSlogan());
        myViewHolder.tv_count.setText(bean.getItems_count() + "篇攻略");
        myViewHolder.tv_logo_title.setText(bean.getAuthor_name());

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_max, iv_biglogo;
        private TextView tv_title, tv_sub_title, tv_count, tv_logo_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_max = (ImageView) itemView.findViewById(R.id.iv_max);
            iv_biglogo = (ImageView) itemView.findViewById(R.id.iv_biglogo);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_sub_title = (TextView) itemView.findViewById(R.id.tv_sub_title);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_logo_title = (TextView) itemView.findViewById(R.id.tv_logo_title);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
