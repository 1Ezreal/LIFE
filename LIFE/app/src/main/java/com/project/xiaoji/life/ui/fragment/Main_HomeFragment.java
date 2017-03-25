package com.project.xiaoji.life.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.xiaoji.life.APIManager;
import com.project.xiaoji.life.R;
import com.project.xiaoji.life.bean.TittleBean;
import com.project.xiaoji.life.inter.ITitleDataListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class Main_HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private FragmentStatePagerAdapter adapter;
    private Toolbar toolbar;
    private View toolBarView;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        toolBarView = inflater.inflate(R.layout.toolbar_home, container, false);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (adapter == null) {
            initViews(view);
            initToolBarView();
            toolbar.addView(toolBarView);
            getTitleData();


        } else {
            adapter.notifyDataSetChanged();
        }


    }

    private void getTitleData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIManager.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITitleDataListener listener = retrofit.create(ITitleDataListener.class);
        Call<TittleBean> data = listener.getTitleData();
        data.enqueue(new Callback<TittleBean>() {
            @Override
            public void onResponse(Call<TittleBean> call, Response<TittleBean> response) {
                if (response.isSuccess()) {

                    List<TittleBean.DataBean.TimeAxisBean> axis = response.body().getData().getTime_axis();

                    for (int i = 0; i < axis.size(); i++) {
                        titles.add(axis.get(i).getName());
                    }
                    initData();
                    initAdapter();
                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                    tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.myYellow));
                    tabLayout.setTabTextColors(Color.GRAY, Color.BLACK);
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                }
            }

            @Override
            public void onFailure(Call<TittleBean> call, Throwable t) {

            }
        });
    }

    private void initToolBarView() {
        TextView tv = (TextView) toolBarView.findViewById(R.id.tv_qiandao);
        ImageView iv = (ImageView) toolBarView.findViewById(R.id.search);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "签到", Toast.LENGTH_SHORT).show();
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "签到", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void initAdapter() {
        adapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        };
    }

    private void initViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolBar);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = ((ViewPager) view.findViewById(R.id.viewPager));

    }

    private void initData() {
        for (int i = 0; i < titles.size(); i++) {
            Home_Fragment_item hf = new Home_Fragment_item();
            Bundle bundle = new Bundle();
            bundle.putString("index", i + "");
            hf.setArguments(bundle);
            fragments.add(hf);
        }

    }
}
