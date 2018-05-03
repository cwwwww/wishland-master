package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.adapter.LiveVideoAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/10/10.
 */

public class LiveVideoActivity extends BaseStyleActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private LiveVideoAdapter liveVideoAdapter;
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    private String data;
    private HomeGameBean.DataBean.GameBean gameBean;
    private Model instance;
    private UserSP userSP;
    private Intent intent;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    emptyLayout.showLoading();
                }
                break;
                case 1: {
                    emptyLayout.hide();
                }
                break;
            }
        }
    };

    @Override
    protected void initVariable() {
        intent = getIntent();
        data = intent.getStringExtra("data");
        gameBean = new Gson().fromJson(data, HomeGameBean.DataBean.GameBean.class);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        liveVideoAdapter = new LiveVideoAdapter(this, instance, userSP, mHandler, gameBean.getImg());
    }

    @Override
    protected void initDate() {
        setTitle("真人视讯");
        liveVideoAdapter.addData(gameBean.getItems());
        liveVideoAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                HomeGameBean.DataBean.GameBean.ItemsBean item = gameBean.getItems().get(position);
                String url = item.getHref().replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN));
                if (instance.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (!BaseApi.GAME_MODEL_OPEN) {
                        instance.skipLoginActivity(LiveVideoActivity.this, LoginActivity.class, "LiveVideoActivity");
                    } else if (BaseApi.GAME_MODEL_OPEN && !item.isTrymode()) {
                        ToastUtil.showShort(LiveVideoActivity.this, "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebGameActivity(
                                LiveVideoActivity.this,
                                url,
                                HomeFragment.GAME_CATEGORY.LIVE_VIDEO.getTitle(),
                                item.getName(),
                                "");
                    }
                } else {
                    WebUtil.toWebGameActivity(
                            LiveVideoActivity.this,
                            url,
                            HomeFragment.GAME_CATEGORY.LIVE_VIDEO.getTitle(),
                            item.getName(),
                            "");
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(liveVideoAdapter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_live_video, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
    }

    @Override
    public void finish() {
        if (liveVideoAdapter != null && liveVideoAdapter.isNeedRefresh()) {
            setResult(1);
        }
        super.finish();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
