package com.project.xiaoji.life.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = 0;
        outRect.top = space;

        // Add top margin only for the first item to avoid double space between items
        //设置第一个Item的MarginTop 属性
        if (parent.getChildPosition(view) == 0)
            outRect.top = 0;
    }
}