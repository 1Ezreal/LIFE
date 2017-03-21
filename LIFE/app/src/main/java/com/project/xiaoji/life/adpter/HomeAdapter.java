package com.project.xiaoji.life.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.HomeItemBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.project.xiaoji.life.R.id.sub_rv;

/**
 * Created by Administrator on 2015/1/1 0001.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<HomeItemBean.DataBean.HomeListBean> list = new ArrayList<>();
    LayoutInflater inflater;



    public HomeAdapter(Context context, List<HomeItemBean.DataBean.HomeListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0: {
                View view=inflater.inflate(R.layout.item_banners,parent,false);
                return new BannersViewHolder(view);
            }
            case 1: {
                View view = inflater.inflate(R.layout.item_fav_list, parent, false);
                return new FavListViewHolder(view);
            }
            case 2: {
                View view = inflater.inflate(R.layout.item_focus_product, parent, false);
                return  new FocusProductViewHolder(view);
            }
            case 3: {
                View view = inflater.inflate(R.layout.item_post_have_item, parent, false);
                return new PostHaveItemViewHolder(view);
            }
            case 4: {
                View view = inflater.inflate(R.layout.item_post_have_item, parent, false);
                return new PostNoItemViewHolder(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (list.get(position).getCell_type()) {
            case "banners": {
                BannersViewHolder bannersViewHolder = (BannersViewHolder) holder;
                final List<HomeItemBean.DataBean.HomeListBean.BannersBean> banners = list.get(position).getBanners();
                bannersViewHolder.viewPager.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return banners.size();
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view==object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        ImageView iv=new ImageView(context);
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Picasso.with(context).load(banners.get(position).getImage_url()).into(iv);
                        container.addView(iv);
                        return iv;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView((View) object);
                    }
                });

            }
            break;
            case "fav_list": {
                FavListViewHolder favListViewHolder = (FavListViewHolder) holder;
                HomeItemBean.DataBean.HomeListBean.FavListBean fav_list = list.get(position).getFav_list();
                favListViewHolder.tv_title.setText(fav_list.getName());
                favListViewHolder.tv_count.setText(fav_list.getItems_count()+"单品");
                HomeItemBean.DataBean.HomeListBean.FavListBean.UserInfoBean user_info = fav_list.getUser_info();
                Picasso.with(context).load(user_info.getAvatar_url()).into(favListViewHolder.iv);

                favListViewHolder.tv_tip.setText(user_info.getNickname());

                LinearLayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                favListViewHolder.rv.setLayoutManager(layoutManager);
                favListViewHolder.rv.setAdapter(new FavListItemAdapter(context,fav_list.getItems_info()));


            }
            break;
            case "focus_product": {
                FocusProductViewHolder focusProductViewHolder = (FocusProductViewHolder) holder;
                HomeItemBean.DataBean.HomeListBean.FocusProductBean focus_product = list.get(position).getFocus_product();
                Picasso.with(context).load(focus_product.getImage_url()).into(focusProductViewHolder.iv);
                focusProductViewHolder.tv_title.setText(focus_product.getTitle());
                focusProductViewHolder.tv_tip.setText(focus_product.getIntroduction());
                focusProductViewHolder.tv_money.setText(focus_product.getSub_title());
            }
            break;
            case "post_have_item": {
                PostHaveItemViewHolder postHaveItemViewHolder = (PostHaveItemViewHolder) holder;
                HomeItemBean.DataBean.HomeListBean.PostBean post = list.get(position).getPost();
                Picasso.with(context).load(post.getNew_cover_image_url()).into(postHaveItemViewHolder.iv_max);
                Picasso.with(context).load(post.getChannel_icon()).into(postHaveItemViewHolder.iv_logo);

                postHaveItemViewHolder.tv_title.setText(post.getTitle());
                postHaveItemViewHolder.tv_type.setText("[ "+post.getChannel_title()+" ]");
                postHaveItemViewHolder.tv_count.setText(post.getLikes_count()+"");

                List<HomeItemBean.DataBean.HomeListBean.PostBean.PostItemsBean> items = post.getPost_items();
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                postHaveItemViewHolder.sub_rv.setLayoutManager(layoutManager);
                PostItemAdapter adapter = new PostItemAdapter(context, items);
                postHaveItemViewHolder.sub_rv.setAdapter(adapter);
            }
            break;
            case "post_no_item": {
                PostNoItemViewHolder postNoItemViewHolder = (PostNoItemViewHolder) holder;
                HomeItemBean.DataBean.HomeListBean.PostBean post = list.get(position).getPost();
                Picasso.with(context).load(post.getNew_cover_image_url()).into(postNoItemViewHolder.iv_max);
                Picasso.with(context).load(post.getChannel_icon()).into(postNoItemViewHolder.iv_logo);

                postNoItemViewHolder.tv_title.setText(post.getTitle());
                postNoItemViewHolder.tv_type.setText("[ "+post.getChannel_title()+"]");
                postNoItemViewHolder.tv_count.setText(post.getLikes_count()+"");



            }
            break;
        }



    }
    class BannersViewHolder extends RecyclerView.ViewHolder{
        private ViewPager viewPager;
        private LinearLayout ll;
        public BannersViewHolder(View itemView) {
            super(itemView);
             viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            ll= (LinearLayout) itemView.findViewById(R.id.ll);
        }
    }
    class FavListViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_count,tv_tip;
        private ImageView iv;
        private RecyclerView rv;
        public FavListViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_count= (TextView) itemView.findViewById(R.id.tv_count);
            tv_tip= (TextView) itemView.findViewById(R.id.tv_tip);
            iv= (ImageView) itemView.findViewById(R.id.iv);
            rv= (RecyclerView) itemView.findViewById(sub_rv);
        }
    }
    class FocusProductViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_tip,tv_money;
        private ImageView iv;
        public FocusProductViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_tip= (TextView) itemView.findViewById(R.id.tv_tip);
            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
            iv= (ImageView) itemView.findViewById(R.id.iv);
        }
    }
    class PostHaveItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_max, iv_logo;
        private TextView tv_title, tv_type,tv_count;
        private RecyclerView sub_rv;

        public PostHaveItemViewHolder(View itemView) {
            super(itemView);
            iv_max = (ImageView) itemView.findViewById(R.id.iv_max);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            sub_rv = (RecyclerView) itemView.findViewById(R.id.sub_rv);
            sub_rv.setVisibility(View.VISIBLE);
        }
    }
    class PostNoItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_max, iv_logo,iv_bg;
        private TextView tv_title, tv_type,tv_count;
        public PostNoItemViewHolder(View itemView) {
            super(itemView);
            iv_max = (ImageView) itemView.findViewById(R.id.iv_max);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            iv_bg = (ImageView) itemView.findViewById(R.id.iv_bg);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            iv_bg.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getCell_type()) {
            case "banners": {
                return 0;
            }
            case "fav_list": {
                return 1;
            }
            case "focus_product": {
                return 2;
            }
            case "post_have_item": {
                return 3;
            }
            case "post_no_item": {
                return 4;
            }
        }
        return super.getItemViewType(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
