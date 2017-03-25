package com.project.xiaoji.life.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.HomeBannerBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class HomeBannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<HomeBannerBean.DataBean.PostsBean> list;
    LayoutInflater inflater;

    public HomeBannerAdapter(Context context, List<HomeBannerBean.DataBean.PostsBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_home_banner_item, parent, false);
        if (viewType == 0) {

            return new MyViewHolder1(view);
        } else {
            return new MyViewHolder2(view);

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        HomeBannerBean.DataBean.PostsBean postsBean = list.get(position);
        if ("" == postsBean.getNew_cover_image_url()) {
            MyViewHolder1 myViewHolder1 = (MyViewHolder1) holder;
            myViewHolder1.tv_count.setText(postsBean.getLikes_count() + "");
            Picasso.with(context).load(postsBean.getCover_image_url()).into(myViewHolder1.iv_max);
        } else {
            MyViewHolder2 myViewHolder2 = (MyViewHolder2) holder;
            myViewHolder2.tv_count.setText(postsBean.getLikes_count() + "");
            myViewHolder2.iv_bg.setVisibility(View.VISIBLE);
            Picasso.with(context).load(postsBean.getChannel_icon()).into(myViewHolder2.iv_logo);
            Picasso.with(context).load(postsBean.getNew_cover_image_url()).into(myViewHolder2.iv_max);
            myViewHolder2.tv_title.setText(postsBean.getTitle());
            myViewHolder2.tv_type.setText("[ " + postsBean.getChannel_title() + " ]");
        }


    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {
        private ImageView iv_max;
        private TextView tv_count;

        public MyViewHolder1(View itemView) {
            super(itemView);
            iv_max = (ImageView) itemView.findViewById(R.id.iv_max);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView iv_max, iv_bg, iv_logo;
        private TextView tv_title, tv_type, tv_count;

        public MyViewHolder2(View itemView) {
            super(itemView);
            iv_max = (ImageView) itemView.findViewById(R.id.iv_max);
            iv_bg = (ImageView) itemView.findViewById(R.id.iv_bg);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            iv_bg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("" == list.get(position).getNew_cover_image_url()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
