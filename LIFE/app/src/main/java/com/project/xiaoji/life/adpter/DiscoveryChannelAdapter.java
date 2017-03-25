package com.project.xiaoji.life.adpter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.DiscoveryChannelBean;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class DiscoveryChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<DiscoveryChannelBean.DataBean.ChannelBean> list;
    LayoutInflater inflater;

    public DiscoveryChannelAdapter(Context context, List<DiscoveryChannelBean.DataBean.ChannelBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:{
                ImageView iv = new ImageView(context);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 400);
                lp.topMargin=40;
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setLayoutParams(lp);
                return new FirstViewHolder(iv);}
            case 1:{
                View view = inflater.inflate(R.layout.item_tobe_talent, parent, false);
                return new ToBeTalentViewHolder(view);
            }
            case 2:
            case 3:

                View view = inflater.inflate(R.layout.item_discover_channel, parent, false);
                return new ChannelViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (list.get(position).getCell_type()) {
            case "life_sir_fav_list":{
                FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
                Picasso.with(context).load(list.get(position).getLife_sir_fav_list().getCover_image_url()).into(  firstViewHolder.iv);
            }break;
            case "to_be_talent": {
                ToBeTalentViewHolder toBeTalentViewHolder = (ToBeTalentViewHolder) holder;
                Picasso.with(context).load(list.get(position).getTo_be_talent().getCover_image_url()).into(toBeTalentViewHolder.iv, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

            }
            break;

            case "talent_channel": {
                ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
                List<DiscoveryChannelBean.DataBean.ChannelBean.TalentChannelBean> talent_channel = list.get(position).getTalent_channel();
                channelViewHolder.tv_title.setText("达人专栏");
                channelViewHolder.tv_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "查看全部", Toast.LENGTH_SHORT).show();
                    }
                });
                channelViewHolder.rv.setAdapter(new DiscoveryChannelItemAdapter1(context,talent_channel));


            }
            break;

            case "life_channel": {
                ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
                List<DiscoveryChannelBean.DataBean.ChannelBean.LifeChannelBean> life_channel = list.get(position).getLife_channel();
                channelViewHolder.tv_title.setText("LIFE专栏");
                channelViewHolder.tv_all.setVisibility(View.GONE);
                channelViewHolder.rv.setAdapter(new DiscoveryChannelItemAdapter2(context,life_channel));
            }
            break;
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_all;
        private RecyclerView rv;
        public ChannelViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_all= (TextView) itemView.findViewById(R.id.tv_all);
            rv= (RecyclerView) itemView.findViewById(R.id.rv);
            GridLayoutManager layoutManager=new GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false);
            rv.setLayoutManager(layoutManager);
            rv.addItemDecoration(new SpacesItemDecoration(20));

        }
    }

    class FirstViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public FirstViewHolder(View itemView) {
            super(itemView);
            iv = ((ImageView) itemView);
        }
    }
    class ToBeTalentViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        public ToBeTalentViewHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getCell_type()) {
            case "life_sir_fav_list":
                return  0;
            case "to_be_talent":
                return 1;

            case "talent_channel":
                return 2;

            case "life_channel":
                return 3;
        }
        return super.getItemViewType(position);
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            outRect.top = space;



        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
