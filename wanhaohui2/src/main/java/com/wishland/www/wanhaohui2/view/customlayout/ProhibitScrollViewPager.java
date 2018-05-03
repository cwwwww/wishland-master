package com.wishland.www.wanhaohui2.view.customlayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by JayCruz on 2017/12/19.
 */

public class ProhibitScrollViewPager extends ViewPager {

    public ProhibitScrollViewPager(Context context) {
        super(context);
    }

    public ProhibitScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * viewpage的onTouchEvent屏蔽
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }
}