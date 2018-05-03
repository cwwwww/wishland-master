package com.wishland.www.wanhaohui2.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HotGameBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.adapter.HotGameMoreAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/12/14.
 */

public class HotGameMoreActivity extends BaseStyleActivity {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;

    private RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
    private HotGameMoreAdapter hotGameMoreAdapter;
    private List<HotGameBean.DataBean> hotGameList;
    private UserSP userSP;
    private Model instance;
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
    private List<String> mTryModeList = new ArrayList<>();


    @Override
    protected void initVariable() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        hotGameMoreAdapter = new HotGameMoreAdapter(this, instance, userSP, mHandler);
        mTryModeList = new Gson().fromJson(getIntent().getStringExtra("data"), List.class);
    }

    @Override
    protected void initDate() {
        setTitle("火热推荐");
        requestAllData();
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_hot_game_more, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(hotGameMoreAdapter);
        refreshView.setRefreshing(true);
        setRefreshStyle();
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestAllData();
            }
        });
        hotGameMoreAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // 判断游戏
                boolean canTry = false;
                String para = hotGameList.get(position).getPara();
                Map<String, String> paraMap = new Gson().fromJson(para, Map.class);
                String url = paraMap.get("url_m");
                for (int i = 0; i < mTryModeList.size(); i++) {
                    if (hotGameList.get(position).getPlat().equalsIgnoreCase(mTryModeList.get(i))) {
                        canTry = true;
                        break;
                    }
                }
                if (instance.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (!BaseApi.GAME_MODEL_OPEN) {
                        instance.skipLoginActivity(HotGameMoreActivity.this, LoginActivity.class, "HotGameMoreActivity");
                    } else if (BaseApi.GAME_MODEL_OPEN && !canTry) {
                        ToastUtil.showShort(HotGameMoreActivity.this, "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebActivity(HotGameMoreActivity.this, url.replace("[host]", "whh4488.com").replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), paraMap.get("name"));
                    }
                } else {
                    WebUtil.toWebActivity(HotGameMoreActivity.this, url.replace("[host]", "whh4488.com").replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), paraMap.get("name"));
                }
            }
        });
    }

    private void setRefreshStyle() {
        refreshView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright), getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light), getResources().getColor(android.R.color.holo_red_light));
    }

    private void getHotGame() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiRequestHotGame(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                refreshView.setRefreshing(false);
                ToastUtil.showShort(HotGameMoreActivity.this, "热门游戏请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                refreshView.setRefreshing(false);
                hotGameList = new ArrayList<>();
                try {
                    String s = responseBody.string();
                    HotGameBean hotGameBean = new Gson().fromJson(s, HotGameBean.class);
                    hotGameList.addAll(hotGameBean.getData());
                    //热门推荐的游戏设置
                    hotGameMoreAdapter.setData(hotGameList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestAllData() {
        if (JudgeNewWorkUtil.isNetworkAvalible(this)) {
            //热门游戏
            getHotGame();
        } else {
            ToastUtil.showShort(this, "网络异常，请检查网络设置！");
            refreshView.setRefreshing(false);
        }
    }

    @Override
    public void finish() {
        if (hotGameMoreAdapter != null && hotGameMoreAdapter.isNeedRefresh()) {
            setResult(1);
        }
        super.finish();
    }

}
