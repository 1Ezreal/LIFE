package com.project.xiaoji.life.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.HomeItemBean;
import com.squareup.picasso.Picasso;

import org.lenve.customshapeimageview.CustomShapeImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/1/1 0001.
 */

public class PostItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<HomeItemBean.DataBean.HomeListBean.PostBean.PostItemsBean> list;
    LayoutInflater inflater;

    public PostItemAdapter(Context context, List<HomeItemBean.DataBean.HomeListBean.PostBean.PostItemsBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_post_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        HomeItemBean.DataBean.HomeListBean.PostBean.PostItemsBean bean = list.get(position);

        Picasso.with(context)
                .load(bean.getCover_image_url())
                //设置加载出错图片
                .error(R.mipmap.ic_launcher)
                .into(viewHolder.iv);
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.tv_money.setText("¥"+bean.getPrice());



    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private CustomShapeImageView iv;
        private TextView tv_name,tv_money;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv= (CustomShapeImageView) itemView.findViewById(R.id.iv_item1_sub);
            tv_name= (TextView) itemView.findViewById(R.id.name );
            tv_money= (TextView) itemView.findViewById(R.id.money );
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
