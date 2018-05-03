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
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.adapter.MarkSixAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/10/30.
 */

public class MarkSixActivity extends BaseStyleActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private String gameData;
    private MarkSixAdapter markSixAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private Model model;
    private UserSP userSP;
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
        model = Model.getInstance();
        userSP = model.getUserSP();
        Intent intent = getIntent();
        gameData = intent.getStringExtra("data");
        final HomeGameBean.DataBean.GameBean gameBean = new Gson().fromJson(gameData, HomeGameBean.DataBean.GameBean.class);
        markSixAdapter = new MarkSixAdapter(this, model, userSP, mHandler, gameBean.getImg());
        markSixAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String url = gameBean.getItems().get(position).getHref().replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN));
                if (model.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (!BaseApi.GAME_MODEL_OPEN) {
                        model.skipLoginActivity(MarkSixActivity.this, LoginActivity.class, "MarkSixActivity");
                    } else if (BaseApi.GAME_MODEL_OPEN && !gameBean.getItems().get(position).isTrymode()) {
                        ToastUtil.showShort(MarkSixActivity.this, "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebGameActivity(
                                MarkSixActivity.this,
                                url,
                                HomeFragment.GAME_CATEGORY.MARK_SIX_GAME.getTitle(),
                                gameBean.getItems().get(position).getName(),
                                "");
                    }
                } else {
                    WebUtil.toWebGameActivity(
                            MarkSixActivity.this,
                            url,
                            HomeFragment.GAME_CATEGORY.MARK_SIX_GAME.getTitle(),
                            gameBean.getItems().get(position).getName(),
                            "");
                }

            }
        });
        markSixAdapter.setData(gameBean.getItems());
    }

    @Override
    protected void initDate() {
        setTitle("六合彩");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mark_six, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(markSixAdapter);
    }

    @Override
    public void finish() {
        if (markSixAdapter != null && markSixAdapter.isNeedRefresh()) {
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
