package com.wishland.www.wanhaohui2.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.StatusBarHightUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;

/**
 * Created by admin on 2017/10/9.
 */

public abstract class BaseStyleActivity extends BaseActivity {
    private FrameLayout content;
    private LinearLayout root;
    private TextView tvTitle;
    private ImageButton btnBack;
    private TextView tvBtn;
    public View toolBar;
    public boolean isSetTitle = false;


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.base_content_layout);
        root = (LinearLayout) findViewById(R.id.root);
        content = (FrameLayout) findViewById(R.id.content);
        View inflate = getLayoutInflater().inflate(R.layout.base_toolbar, root, false);
        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        btnBack = (ImageButton) inflate.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        root.addView(inflate, 0);
        content.addView(getLayoutInflater().inflate(layoutResID, content, false));
        initViewState();
    }

    protected void initViewState() {
        int padding = StatusBarHightUtil.getStatusBarHeight();
        tvTitle.setPadding(padding, padding, padding, padding / 3);
        if (btnBack != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) btnBack.getLayoutParams();
            lp.setMargins(0, padding / 3, 0, 0);
            btnBack.setLayoutParams(lp);
        }
        if (tvBtn != null) {
            tvBtn.setPadding(padding, padding, padding, padding / 3);
        }
    }

    public void setContentView(@LayoutRes int layoutResID, @LayoutRes int toolbarResID) {
        super.setContentView(R.layout.base_content_layout);
        root = (LinearLayout) findViewById(R.id.root);
        content = (FrameLayout) findViewById(R.id.content);
        toolBar = getLayoutInflater().inflate(toolbarResID, root, false);
        tvTitle = (TextView) toolBar.findViewById(R.id.tv_title);
        btnBack = (ImageButton) toolBar.findViewById(R.id.btn_back);
        tvBtn = (TextView) toolBar.findViewById(R.id.tv_btn);

        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


        root.addView(toolBar, 0);
        content.addView(getLayoutInflater().inflate(layoutResID, content, false));
        initViewState();
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (tvTitle != null && !isSetTitle)
            tvTitle.setText(title);
    }


    public void setTitle(String title) {
        if (tvTitle != null) {
            isSetTitle = true;
            tvTitle.setText(title);
        }
    }

    public void setTvTitle(String title) {
        if (tvBtn != null) {
            tvBtn.setText(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!JudgeNewWorkUtil.isNetworkAvalible(this)) {
            ToastUtil.showShort(this, "网络异常，请检查网络设置！");
        }
    }


//    @Override
//    public View findViewById(int id) {
//        View v = super.findViewById(id);
//        if (v == null && mHelper != null)
//            return mHelper.findViewById(id);
//        return v;
//    }
}
