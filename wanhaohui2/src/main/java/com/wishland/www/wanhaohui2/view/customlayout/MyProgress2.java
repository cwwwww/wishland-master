package com.wishland.www.wanhaohui2.view.customlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;

public class MyProgress2 extends RelativeLayout {

    private View mProgressBar_layout;
    private RelativeLayout progress_imageview;
    private ProgressBar progressBar;
    private int max = 100;//默认ProgressBar的最大值为100
    private int mProgress = 0;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private TextView tv_ckje;

    public MyProgress2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyProgress2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgress2(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mProgressBar_layout = inflater.inflate(R.layout.progressbar_layout2, null);
        progress_imageview = (RelativeLayout) mProgressBar_layout.findViewById(R.id.progress_imageview);
        tv_ckje = (TextView) mProgressBar_layout.findViewById(R.id.tv_ztzje);
//		AnimationDrawable background = (AnimationDrawable) progress_imageview.getBackground();
//		background.start();
        progressBar = (ProgressBar) mProgressBar_layout.findViewById(R.id.progressBar);
        this.addView(mProgressBar_layout);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        int iv_height = progress_imageview.getMeasuredHeight();
        int iv_width = progress_imageview.getMeasuredWidth();
        int iv_bottom = progress_imageview.getBottom();

        int progress_left = progressBar.getLeft();
        int progress_width = progressBar.getMeasuredWidth();

//        progress_imageview.layout(progress_left + mProgress * (progress_width - iv_width / 2) / max, iv_bottom - iv_height, progress_left + mProgress * (progress_width - iv_width / 2) / max + iv_width, iv_bottom);
        progress_imageview.layout(progress_left + mProgress * (progress_width) / max - progress_width / 9
                , iv_bottom - iv_height,
                progress_left + mProgress * (progress_width) / max + iv_width - progress_width / 9 , iv_bottom);
//        progress_imageview.layout(progressLeft, progressTop, progressRight, progressBottom);
    }

    /**
     * 设置ProgressBar的进度及小人的位置
     *
     * @param progress
     */
    @SuppressLint("WrongCall")
    public void setProgress(int progress, String counts) {
        if (progress > max) {
            this.mProgress = max;
        } else {

            this.mProgress = progress;
        }
        this.onLayout(true, left, top, right, bottom);
        progressBar.setProgress(mProgress);
        if ("".equals(counts)) {
            tv_ckje.setText("0");
        } else {
            tv_ckje.setText(counts);
        }
    }

    /**
     * 获取当前ProgressBar的进度
     *
     * @return
     */
    public int getProgress() {
        return mProgress;
    }

    /**
     * 设置ProgressBar的最大值
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }
}
