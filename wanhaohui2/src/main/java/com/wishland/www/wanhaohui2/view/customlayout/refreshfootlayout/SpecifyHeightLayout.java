package com.wishland.www.wanhaohui2.view.customlayout.refreshfootlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017/10/12.
 */

public class SpecifyHeightLayout extends ViewGroup {

    private int mOright;
    private int mHeight = Integer.MIN_VALUE;
    private int mGraity = Gravity.TOP;

    public SpecifyHeightLayout(Context context) {
        super(context);
    }

    public SpecifyHeightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpecifyHeightLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginHeight(int height) {
        mOright = height;
    }

    public void resetOrightHeight() {
        setHeight(mOright);
    }

    public void setHeight(int height) {
        mHeight = height;
        requestLayout();
    }

    public int getHeightX() {
        return mHeight;
    }

    public void setGravity(int gravity) {
        mGraity = gravity;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int size = MeasureSpec.getSize(widthMeasureSpec);

        getChildAt(0).measure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY), heightMeasureSpec);

        setMeasuredDimension(size, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (getChildCount() > 0) {
            View view = getChildAt(0);
            int height = bottom - top;

            if (mGraity == Gravity.BOTTOM)
                view.layout(0, height - view.getMeasuredHeight(), right - left, height);
            else if (mGraity == Gravity.TOP)
                view.layout(0, 0, right - left, view.getMeasuredHeight());
        }
    }
}
