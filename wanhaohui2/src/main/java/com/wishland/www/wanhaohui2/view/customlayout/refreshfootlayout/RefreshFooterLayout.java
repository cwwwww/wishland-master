package com.wishland.www.wanhaohui2.view.customlayout.refreshfootlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;

/**
 * Created by admin on 2017/10/12.
 */

public class RefreshFooterLayout extends SpecifyHeightLayout implements Runnable, View.OnClickListener {

    public interface RefreshListener {
        void onRefresh();
    }

    private static final String TXT_LOADING = "正在加载中...";
    private static final String TXT_CLICK_RETRY = "加载失败，请点我重试";

    private boolean hasMore;
    private boolean isLoading;
    private boolean isError;

    private RefreshListener mListener;

    private View mChild;
    private ProgressBar mProgress;
    private TextView mState;

    public RefreshFooterLayout(Context context) {
        super(context);
        init();
    }

    public RefreshFooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mChild = LayoutInflater.from(getContext()).inflate(R.layout.layout_refresh_footer, this, false);
        mChild.setOnClickListener(this);

        mProgress = (ProgressBar) mChild.findViewById(R.id.progress);
        mState = (TextView) mChild.findViewById(R.id.refresh_status);

        int heightSpace = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int widthSpace = View.MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, View.MeasureSpec.EXACTLY);

        mChild.measure(widthSpace, heightSpace);

        int height = mChild.getMeasuredHeight();

        addView(mChild);

        setHeight(height);
        setOriginHeight(height);
    }

    public void setRefreshListener(RefreshListener l) {
        mListener = l;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;

        if (hasMore) {
            resetOrightHeight();
        } else {
            setHeight(0);
        }
    }

    public void setError() {
        hasMore = false;
        isLoading = false;
        isError = true;
        mProgress.setVisibility(View.GONE);
        mState.setText(TXT_CLICK_RETRY);
    }

    public void onLoadComplete() {
        postDelayed(this, 200);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        load();
    }

    public void load() {
        if (!isLoading && hasMore && !isError) {
            isLoading = true;
            resetOrightHeight();
            mState.setText(TXT_LOADING);
            mListener.onRefresh();
        }
    }

    @Override
    public void run() {
        isLoading = false;
    }

    @Override
    public void onClick(View v) {
        if (isError) {
            isLoading = true;
            hasMore = true;
            isError = false;
            mState.setText(TXT_LOADING);
            mListener.onRefresh();
            mProgress.setVisibility(View.VISIBLE);
        }
    }
}

