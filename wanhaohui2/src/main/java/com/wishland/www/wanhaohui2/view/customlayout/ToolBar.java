package com.wishland.www.wanhaohui2.view.customlayout;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;

/**
 * Created by admin on 2017/10/9.
 */

public class ToolBar extends RelativeLayout {

    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    private LinearLayout mLeftLayout;
    private RelativeLayout mMiddleLayout;
    private LinearLayout mRightLayout;

    private static float density;

    public ToolBar(Context context) {
        super(context);
        init();
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        density = getContext().getResources().getDisplayMetrics().density;
    }

    public static int dp2px(float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLeftLayout = (LinearLayout) findViewById(R.id.left_layout);
        mMiddleLayout = (RelativeLayout) findViewById(R.id.middle_layout);
        mRightLayout = (LinearLayout) findViewById(R.id.right_layout);
    }

    public LinearLayout getLeftLayout() {
        return mLeftLayout;
    }

    public RelativeLayout getMiddleLayout() {
        return mMiddleLayout;
    }

    public LinearLayout getRightLayout() {
        return mRightLayout;
    }

    /***************** left ****************/

    public void addLeftView(@LayoutRes int layout) {
        LayoutInflater.from(getContext()).inflate(layout, mLeftLayout, true);
    }

    public void addLeftView(View view) {
        addLeftView(view, null);
    }

    public void addLeftView(View view, LinearLayout.LayoutParams params) {
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        getLeftLayout().addView(view, params);
    }

    public ImageView addDefaultLeftImageView(@DrawableRes int res, @IdRes int id, LinearLayout.LayoutParams params, OnClickListener click) {
        ImageView img = generateDefaultImageView(res, id, click);
        addLeftView(img, params);
        return img;
    }

    /******************* middle ********************/

    public void addMiddleView(@LayoutRes int res) {
        LayoutInflater.from(getContext()).inflate(res, mMiddleLayout, true);
    }

    public void addMiddleView(View view) {
        addMiddleView(view, null);
    }

    public void addMiddleView(View view, RelativeLayout.LayoutParams params) {
        if (params == null) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        getMiddleLayout().addView(view, params);
    }

    public TextView addDefaultStyleTitle(CharSequence titleTxt, int txtSize, int txtColor) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        TextView title = generateDefaultTitle(txtSize, txtColor);
        title.setText(titleTxt);
        addMiddleView(title, params);

        return title;
    }

    /******************* right *******************/

    public void addRightView(@LayoutRes int layout) {
        LayoutInflater.from(getContext()).inflate(layout, mRightLayout, true);
    }

    public void addRightView(View view) {
        addRightView(view, null);
    }

    public void addRightView(View view, LinearLayout.LayoutParams params) {
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        getRightLayout().addView(view, params);
    }

    public ImageView addDefaultRightImageView(@DrawableRes int res, @IdRes int id, LinearLayout.LayoutParams params, OnClickListener click) {
        ImageView img = generateDefaultImageView(res, id, click);
        addRightView(img, params);
        return img;
    }

    /****************** other ******************/

    private ImageView generateDefaultImageView(@DrawableRes int res, @IdRes int id, OnClickListener click) {
        ImageView img = new ImageView(getContext());
        img.setImageResource(res);
        img.setId(id);
        img.setOnClickListener(click);
        return img;
    }

    private TextView generateDefaultTitle(int textSize, @ColorInt int color) {
        TextView title = new TextView(getContext());
        title.setTextSize(textSize);
        title.setTextColor(color);
        return title;
    }

    public static LinearLayout.LayoutParams linearLayoutParams(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    public static RelativeLayout.LayoutParams relativeLayoutParams(int width, int height) {
        return new RelativeLayout.LayoutParams(width, height);
    }
}
