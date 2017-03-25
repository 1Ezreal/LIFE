
        package com.project.xiaoji.life.view;

        import android.content.Context;
        import android.util.AttributeSet;
        import android.view.View;
        import android.view.ViewGroup;

        import java.util.ArrayList;
        import java.util.List;



public class FlowLayout extends ViewGroup {

    private List<List<View>> mAllViews = new ArrayList<>();
    private List<View> mLineViews = new ArrayList<>();
    private List<Integer> mPerLineMaxHeight = new ArrayList<>();

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        //获取容器中子控件的个数
        int childCount = getChildCount();
        //记录容器总高度
        int totalHeight = 0;
        //记录当前正在摆放的行的宽度
        int currentWidth = 0;
        //每行最高控件的高度
        int lineMaxHeight = 0;
        for (int i = 0; i < childCount; i++) {

            View view = getChildAt(i);

            measureChild(view, widthSize, heightSize);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if ((childWidth + currentWidth) > widthSize) {
                //换行
                totalHeight += lineMaxHeight;
                currentWidth = childWidth;
                lineMaxHeight = childHeight;

            } else {
                currentWidth += childWidth;
                lineMaxHeight = Math.max(childHeight, lineMaxHeight);

            }

            totalHeight += lineMaxHeight;

            heightSize = heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight;

            setMeasuredDimension(widthSize, heightSize);


        }


    }

    @Override
    protected void onLayout(boolean b, int i4, int i1, int i2, int i3) {
        mAllViews.clear();
        mLineViews.clear();
        mPerLineMaxHeight.clear();
        /***********************遍历所有子控件,将子控件添加到mAllViews中*****************************/
        int childCount = getChildCount();
        int currentWidth = 0;
        int lineMaxHeight = 0;


        for (int i = 0; i < childCount; i++) {

            View view = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
            int childWidth = view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = view.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if ((childWidth + currentWidth) > getMeasuredWidth()) {

                mAllViews.add(mLineViews);
                mPerLineMaxHeight.add(lineMaxHeight);
                mLineViews = new ArrayList<>();
                currentWidth = 0;
                lineMaxHeight = 0;
            }
            mLineViews.add(view);
            lineMaxHeight = Math.max(lineMaxHeight, childHeight);
            currentWidth += childWidth;
        }
        mAllViews.add(mLineViews);
        mPerLineMaxHeight.add(lineMaxHeight);

        /**************************取出所有子控件,进行摆放计算***************************/

        int mLeft = 0;
        int mTop = 0;
        for (int i = 0; i < mAllViews.size(); i++) {

            List<View> lineViews = mAllViews.get(i);



            for (int j = 0; j < lineViews.size(); j++) {

                View view = lineViews.get(j);

                MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();

                int left = mLeft + lp.leftMargin;
                int top = mTop + lp.topMargin;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);

                mLeft+=lp.leftMargin+lp.rightMargin+view.getMeasuredWidth();



            }
            mLeft=0;
            mTop+=mPerLineMaxHeight.get(i);



        }


    }
}
