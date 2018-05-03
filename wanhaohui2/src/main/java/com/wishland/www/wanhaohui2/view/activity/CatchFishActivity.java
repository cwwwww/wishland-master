package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.adapter.CatchFishAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/10/10.
 */

public class CatchFishActivity extends BaseStyleActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private CatchFishAdapter catchFishAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private HomeGameBean.DataBean.GameBean gameBean;
    private String data;
    private Model instance;
    private UserSP userSP;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    emptyLayout.showLoading();
                    break;
                case 1:
                    emptyLayout.hide();
                    break;
            }
        }
    };

    @Override
    protected void initVariable() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        gameBean = new Gson().fromJson(data, HomeGameBean.DataBean.GameBean.class);
        catchFishAdapter = new CatchFishAdapter(this, instance, userSP, mHandler, gameBean.getImg());
    }

    @Override
    protected void initDate() {
        setTitle("捕鱼达人");
        catchFishAdapter.setData(gameBean.getItems());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(catchFishAdapter);
        catchFishAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String url = gameBean.getItems().get(position).getHref().replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN));
                if (instance.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (!BaseApi.GAME_MODEL_OPEN) {
                        instance.skipLoginActivity(CatchFishActivity.this, LoginActivity.class, "CatchFishActivity");
                    } else if (BaseApi.GAME_MODEL_OPEN && !gameBean.getItems().get(position).isTrymode()) {
                        ToastUtil.showShort(CatchFishActivity.this, "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebGameActivity(
                                CatchFishActivity.this,
                                url,
                                HomeFragment.GAME_CATEGORY.FISHING_GAME.getTitle(),
                                gameBean.getItems().get(position).getName(),
                                "");
                    }
                } else {
                    WebUtil.toWebGameActivity(
                            CatchFishActivity.this,
                            url,
                            HomeFragment.GAME_CATEGORY.FISHING_GAME.getTitle(),
                            gameBean.getItems().get(position).getName(),
                            "");
                }

            }
        });
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_catch_fish, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
    }

    @Override
    public void finish() {
        if (catchFishAdapter != null && catchFishAdapter.isNeedRefresh()) {
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
        mHandler.removeCallbacksAndMessages(null);
    }
}
