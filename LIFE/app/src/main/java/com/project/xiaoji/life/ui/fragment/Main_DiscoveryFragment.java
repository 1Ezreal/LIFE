package com.project.xiaoji.life.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.xiaoji.life.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class Main_DiscoveryFragment extends Fragment {

    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentStatePagerAdapter adapter;
    private View scrollTip;
    private RadioGroup rg;
    private RadioButton rb_column;
    private RadioButton rb_single;
    private int widthPixels;
    private View rootView;
    private View toolBarView;
    private RelativeLayout discovery_toolBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (toolBarView==null) {
            toolBarView = inflater.inflate(R.layout.toolbar_discovery,container,false);
        }

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main_discovery, container, false);
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

        if (adapter==null){
            initViews(view);
            initData();
            initAdapter();
            viewPager.setAdapter(adapter);
            setRgCheckChanged();
            setViewPagerChanged();

        }else {
            adapter.notifyDataSetChanged();
        }



    }

    private void setViewPagerChanged() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                setTipMargin(position);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        rb_column.setChecked(true);
                    }
                    break;
                    case 1: {
                        rb_single.setChecked(true);
                    }
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setRgCheckChanged() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_column: {
                        viewPager.setCurrentItem(0);
                    }
                    break;
                    case R.id.rb_single: {
                        viewPager.setCurrentItem(1);
                    }
                    break;
                }
                int position=viewPager.getCurrentItem();
                setTipMargin(position);
            }

        });


    }

    private void setTipMargin(int position) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) scrollTip.getLayoutParams();
        lp.leftMargin=widthPixels/8+(widthPixels/8)*(position*2)+position*(widthPixels/4);
        scrollTip.setLayoutParams(lp);
    }

    private void initAdapter() {

        adapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
    }

    private void initData() {
        fragments.add(new Main_PersonFragment());
        fragments.add(new Main_PersonFragment());
    }

    private void initViews(View view) {
        discovery_toolBar = (RelativeLayout)view.findViewById(R.id.discovery_toolBar);
        rg = (RadioGroup) view.findViewById(R.id.rg);
        rb_column = (RadioButton) view.findViewById(R.id.rb_column);
        rb_single = (RadioButton) view.findViewById(R.id.rb_single);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        scrollTip = view.findViewById(R.id.scrollTip);
        widthPixels = getResources().getDisplayMetrics().widthPixels;

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(widthPixels /4,5);
        scrollTip.setLayoutParams(lp);
        discovery_toolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "搜索", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
