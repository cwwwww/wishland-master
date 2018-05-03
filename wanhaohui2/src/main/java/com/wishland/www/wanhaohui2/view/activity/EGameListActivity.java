package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.EGameListBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.EGameListAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.refresh.RefreshFooterLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/18.
 */

public class EGameListActivity extends BaseStyleActivity {

    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.et_search)
    EditText etSearch;


    private String name;
    private Model instance;
    private UserSP userSP;
    private EGameListAdapter eGameListAdapter;
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
    private String plat;
    private String search;
    private int page = 1;
    private int count = 14;
    private int gameCount = 0;
    RefreshFooterLayout footer;
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
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        Intent intent = getIntent();
        name = intent.getStringExtra("platName");
        //平台名称
        plat = intent.getStringExtra("plat");
    }

    @Override
    protected void initDate() {
        setTitle(name);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_egame_list, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViewData();
                initGameData(page, count, search);
            }
        });
        setRefreshStyle();
        eGameListAdapter = new EGameListAdapter(this, instance, userSP, mHandler, name);
        eGameListAdapter.addFooterView(createFooterView());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(eGameListAdapter);
        initGameData(page, count, search);
    }

    private void initViewData() {
        etSearch.setText("");
        search = "";
        gameCount = 0;
        page = 1;
        count = 14;
        hideEtSearch();
    }

    private void setRefreshStyle() {
        refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright), getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light), getResources().getColor(android.R.color.holo_red_light));
    }


    private void initGameData(int page, int count, String search) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("plat", plat);
        if (!TextUtils.isEmpty(search)) {
            map.put("search", search);
        }
        map.put("page", String.valueOf(page));
        map.put("count", String.valueOf(count));
        instance.apiRequestEGame(plat, this.search, String.valueOf(page), String.valueOf(count), token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                footer.onLoadComplete();
                refreshLayout.setRefreshing(false);
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(EGameListActivity.this, "获取电子游戏列表失败！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                footer.onLoadComplete();
                refreshLayout.setRefreshing(false);
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        EGameListBean eGameListBean = new Gson().fromJson(s, EGameListBean.class);
                        List<EGameListBean.DataBean> gameBeen = eGameListBean.getData();
                        if (gameBeen.size() == 0) {
                            footer.setHasMore(false);
                            ToastUtil.showShort(EGameListActivity.this, "没有该游戏！");
                        } else if (gameCount >= gameBeen.size()) {
                            footer.setHasMore(false);
                            ToastUtil.showShort(EGameListActivity.this, "暂无更多游戏！");
                        } else {
                            if (gameBeen.size() < 14) {
                                footer.setHasMore(false);
                            } else {
                                footer.setHasMore(true);
                            }
                        }

                        eGameListAdapter.setData(gameBeen);
                        gameCount = eGameListAdapter.getCount();
                    } else {
                        ToastUtil.showShort(EGameListActivity.this, "请求游戏列表异常！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void finish() {
        if (eGameListAdapter != null && eGameListAdapter.isNeedRefresh()) {
            setResult(1);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        //隐藏输入键盘
        hideEtSearch();
        gameCount = 0;
        search = etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(search)) {
            ToastUtil.showShort(EGameListActivity.this, "请输入游戏名称！");
            return;
        }
        emptyLayout.showLoading();
        initGameData(1, count, search);
    }

    private void hideEtSearch() {
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }

    private View createFooterView() {
        footer = new RefreshFooterLayout(this);
        footer.setRefreshListener(mLoadMoreListener);
        footer.setHasMore(false);
        return footer;
    }

    private RefreshFooterLayout.RefreshListener mLoadMoreListener = new RefreshFooterLayout.RefreshListener() {
        @Override
        public void onRefresh() {
            count += 14;
            initGameData(page, count, search);
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
