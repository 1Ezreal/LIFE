package com.project.xiaoji.life.adpter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ismaeltoe.FlowLayout;
import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.DiscoveryCategoryBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class DiscoveryCgtegoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<DiscoveryCategoryBean.DataBean.CategoriesBean> list;
    LayoutInflater inflater;

    public DiscoveryCgtegoryAdapter(Context context, List<DiscoveryCategoryBean.DataBean.CategoriesBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_discovery_cgtegory, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Picasso.with(context).load(list.get(position).getIcon_url()).into(myViewHolder.iv);
        List<DiscoveryCategoryBean.DataBean.CategoriesBean.SubcategoriesBean> subcategories = list.get(position).getSubcategories();
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(10, 10, 10, 10);
        Object tag = myViewHolder.fl.getTag();
        if (tag==null|| (int)tag!=position){
            myViewHolder.fl.removeAllViews();
            for (int i = 0; i < subcategories.size(); i++) {

                TextView tv = new TextView(context);
                tv.setClickable(true);
                tv.setPadding(15, 15, 15, 15);
                tv.setTextColor(Color.BLACK);
                tv.setText(subcategories.get(i).getName());
                myViewHolder.fl.addView(tv);
            }
            myViewHolder.fl.setTag(position);
        }


    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private FlowLayout fl;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            fl = (FlowLayout) itemView.findViewById(R.id.flowLayout);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
