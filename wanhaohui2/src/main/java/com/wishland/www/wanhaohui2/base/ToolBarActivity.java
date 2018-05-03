package com.wishland.www.wanhaohui2.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.utils.PxUtil;
import com.wishland.www.wanhaohui2.view.customlayout.ToolBar;

/**
 * Created by admin on 2017/10/9.
 */

public abstract class ToolBarActivity extends BaseActivity {
    private ToolBar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportBack();
    }

    public ToolBar getToolbar() {
        return mToolbar;
    }

    public void addDefautlRightImageView(@DrawableRes int res, int id, View.OnClickListener l) {
        if (mToolbar == null) return;
        mToolbar.addDefaultRightImageView(res, id, new LinearLayout.LayoutParams(PxUtil.dp2px(50), ViewGroup.LayoutParams.MATCH_PARENT), l);
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitle(title, 17, Color.WHITE);
    }

    public void setTitle(CharSequence title, int txtSize, @ColorInt int color) {
        if (mToolbar != null) {
            mToolbar.addDefaultStyleTitle(title, txtSize, color);
        }
    }

    public void addMiddleLayout(@LayoutRes int layout) {
        if (mToolbar != null) {
            mToolbar.addMiddleView(layout);
        }
    }

    public void addMiddleLayout(View view) {
        addMiddleLayout(view, null);
    }

    public void addMiddleLayout(View view, RelativeLayout.LayoutParams params) {
        if (mToolbar != null) {
            mToolbar.addMiddleView(view, params);
        }
    }

    public void addRightLayout(@LayoutRes int layout) {
        if (mToolbar != null) {
            mToolbar.addRightView(layout);
        }
    }

    public void addLeftLayout(@LayoutRes int layout) {
        if (mToolbar != null) {
            mToolbar.addLeftView(layout);
        }
    }

    public void setSupportBack() {
        setSupportBack(true);
    }

    public void setSupportBack(boolean isSupport) {
        setSupportBack(isSupport, null, 0, 0);
    }

    public void setSupportBack(boolean isSupport, CharSequence txt) {
        setSupportBack(isSupport, txt, 14, Color.WHITE);
    }

    /**
     * 图片加文字样式的的返回键
     */
    public void setSupportBack(boolean isSupport, CharSequence txt, int txtSize, @ColorInt int txtColor) {
        if (mToolbar == null) return;

        if (!isSupport) {
            View view = mToolbar.findViewById(R.id.back);
            if (view == null) return;
            mToolbar.getLeftLayout().removeView(view);
            return;
        }

        if (txt == null) {
            mToolbar.addDefaultLeftImageView(
                    R.drawable.ic_action_back,
                    R.id.back,
                    ToolBar.linearLayoutParams(PxUtil.dp2px(50), ToolBar.MATCH_PARENT),
                    mBackClick);
        } else {
            View back = generateImageTxtStyleLayout(R.drawable.ic_action_back, txt, txtSize, Color.WHITE);
            back.setOnClickListener(mBackClick);
            mToolbar.addLeftView(back);
        }
    }

    private View.OnClickListener mBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        checkToolbar();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        checkToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        checkToolbar();
    }

    private void checkToolbar() {
        mToolbar = (ToolBar) findViewById(R.id.toolbar);
    }


    private View generateImageTxtStyleLayout(int res, CharSequence t, int txtSize, int txtColor) {
        View layout = getLayoutInflater().inflate(R.layout.layout_back_img_txt, null, false);
        ((ImageView) layout.findViewById(R.id.img)).setImageResource(res);
        TextView txt = (TextView) layout.findViewById(R.id.txt);
        txt.setTextSize(txtSize);
        txt.setTextColor(txtColor);
        txt.setText(t);
        return layout;
    }
}
