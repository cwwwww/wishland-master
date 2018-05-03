package com.wishland.www.wanhaohui2.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.api.BaseRetrofit;
import com.wishland.www.wanhaohui2.api.HttpApiInstance;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.BannerDataBen;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.bean.HotGameBean;
import com.wishland.www.wanhaohui2.bean.MarqueeBean;
import com.wishland.www.wanhaohui2.bean.MindCollectionBean;
import com.wishland.www.wanhaohui2.bean.NewNoticeBean;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.bean.UrlPingInfo;
import com.wishland.www.wanhaohui2.model.MobclickEvent;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.BgAlphaUtil;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.CatchFishActivity;
import com.wishland.www.wanhaohui2.view.activity.EGameDetailActivity;
import com.wishland.www.wanhaohui2.view.activity.HotGameMoreActivity;
import com.wishland.www.wanhaohui2.view.activity.LiveVideoActivity;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.activity.LottoDeluxeActivity;
import com.wishland.www.wanhaohui2.view.activity.MainActivity;
import com.wishland.www.wanhaohui2.view.activity.MarkSixActivity;
import com.wishland.www.wanhaohui2.view.activity.SportsEventActivity;
import com.wishland.www.wanhaohui2.view.activity.WelcomeNewActivity;
import com.wishland.www.wanhaohui2.view.adapter.HomeGameAdapter;
import com.wishland.www.wanhaohui2.view.adapter.HotGameAdapter;
import com.wishland.www.wanhaohui2.view.adapter.MyCollectionAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.MarqueeTextView;
import com.wishland.www.wanhaohui2.view.customlayout.MyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/7.
 */

public class HomeFragment extends UmengFragment {

    @BindView(R.id.ll_home_game)
    RecyclerView llHomeGame;
    @BindView(R.id.my_collection)
    RecyclerView myCollection;
    Unbinder unbinder;
    @BindView(R.id.home_banner)
    Banner homeBanner;
    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;
    @BindView(R.id.home_textvhiew_pmd)
    MarqueeTextView homeTextvhiewPmd;
    @BindView(R.id.ll_my_collection)
    LinearLayout llMyCollection;
    @BindView(R.id.ll_hot_game)
    LinearLayout llHotGame;
    @BindView(R.id.recycler_hot_game)
    RecyclerView recyclerHotGame;
    @BindView(R.id.sv_home)
    MyScrollView scrollView;
    @BindView(R.id.rl_footView)
    RelativeLayout rl_footView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_footer)
    TextView tvFooter;
    @BindView(R.id.ll_line_down_collection)
    LinearLayout llLineDownCollection;
    @BindView(R.id.iv_indicate_collection)
    ImageView ivIndicateCollection;
    @BindView(R.id.tv_indicate_collection)
    TextView tvIndicateCollection;
    private HomeGameAdapter homeGameAdapter;
    private MyCollectionAdapter myCollectionAdapter;
    private HotGameAdapter hotGameAdapter;
    private RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
    private RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 4);
    private RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getContext(), 4);

    public enum GAME_CATEGORY {
        LIVE_VIDEO("真人视讯", LiveVideoActivity.class),
        ELECTRIC_GAME("电子游艺", EGameDetailActivity.class),
        LOTTERY_GAME("彩票游戏", LottoDeluxeActivity.class),
        SPORT_GAME("体育赛事", SportsEventActivity.class),
        FISHING_GAME("捕鱼达人", CatchFishActivity.class),
        MARK_SIX_GAME("六合彩", MarkSixActivity.class),
        CARD_GAME("棋牌游戏", null),
        MORE_GAME("更多", null);
        private String title;
        private Class intentTo;

        GAME_CATEGORY(String title, Class intentTo) {
            this.title = title;
            this.intentTo = intentTo;
        }

        public String getTitle() {
            return title;
        }

        public static GAME_CATEGORY findGameByTitle(String title) {
            for (GAME_CATEGORY item : values()) {
                if (item.title.equals(title)) {
                    return item;
                }
            }
            return MORE_GAME;
        }
    }

    private List<String> imgList;
    private List<String> webUrlList;
    private Model instance;
    private UserSP userSP;
    private final static int BANNER = 1;
    private List<HomeGameBean.DataBean.GameBean> homeGameList;
    private List<MindCollectionBean.DataBean> mindCollectionList;
    private List<HotGameBean.DataBean> hotGameList;
    private String gameListData;
    private String caipiaoData;
    private MindCollectionBean mindCollectionBean;
    private List<Integer> viewTypeForCollection = new ArrayList<>();
    private Handler handler;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BANNER: {
                    setBanner(imgList, webUrlList);
                }
                break;
                //取消收藏的游戏
                case 0: {
                    if (myCollectionAdapter.isNeedRefresh()) {
                        getMyCollectionData();
                        getGameListData();
                    }
                }
                break;
            }
        }
    };
    private List<String> mTryModeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        myCollectionAdapter = new MyCollectionAdapter(getActivity(), userSP, instance, mHandler, emptyLayout, viewTypeForCollection);
        layoutManager.setAutoMeasureEnabled(true);
        llHomeGame.setLayoutManager(layoutManager);
        llHomeGame.setAdapter(homeGameAdapter);
        llHomeGame.setHasFixedSize(true);
        llHomeGame.setNestedScrollingEnabled(false);
        layoutManager1.setAutoMeasureEnabled(true);
        myCollection.setLayoutManager(layoutManager1);
        myCollection.setAdapter(myCollectionAdapter);
        myCollection.setHasFixedSize(true);
        myCollection.setNestedScrollingEnabled(false);
        layoutManager2.setAutoMeasureEnabled(true);
        recyclerHotGame.setItemAnimator(new ScaleInTopAnimator());
        recyclerHotGame.getItemAnimator().setAddDuration(1500);
        recyclerHotGame.getItemAnimator().setRemoveDuration(500);
        recyclerHotGame.setLayoutManager(layoutManager2);
        recyclerHotGame.setAdapter(hotGameAdapter);
        recyclerHotGame.setHasFixedSize(true);
        recyclerHotGame.setNestedScrollingEnabled(false);

        initView();
        initData();
        return view;
    }


    private void initView() {
        refreshView.setRefreshing(true);
        setRefreshStyle();

        scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
                int d = view.getBottom();
                d -= (scrollView.getHeight() + scrollView.getScrollY());
                if (d == 0) {
                    rl_footView.setVisibility(View.VISIBLE);
                } else {
                    rl_footView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setRefreshStyle() {
        refreshView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright), getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light), getResources().getColor(android.R.color.holo_red_light));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = Model.getInstance();
        userSP = instance.getUserSP();

        homeGameAdapter = new HomeGameAdapter(getActivity(), getContext());
        hotGameAdapter = new HotGameAdapter(getActivity(), userSP, this, instance);

        homeGameAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                HomeGameBean homeGameBean = new Gson().fromJson(gameListData, HomeGameBean.class);
                if (homeGameBean == null) {
                    return;
                }
                try {
                    if (position == homeGameBean.getData().getGame().size() || "棋牌游戏".equals(homeGameBean.getData().getGame().get(position).getTitle())) {
                        ToastUtil.showShort(getContext(), "即将上线，敬请期待！");
                        return;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Log.e("ArrayIndexOutOfBounds", "ArrayIndexoutOf");
                }
                HomeGameBean.DataBean.GameBean gameBean = homeGameBean.getData().getGame().get(position);
                String gameBeanJson = new Gson().toJson(gameBean);
                String tag = itemView.getTag().toString();
                GAME_CATEGORY type = GAME_CATEGORY.findGameByTitle(tag);

                if (userSP.getInt(UserSP.LOGIN_SUCCESS) != -1) {
                    MobclickAgent.onEvent(getActivity(), MobclickEvent.GAME_CATEGORY, tag);
                }

                if (type.intentTo != null && !gameBean.getTitle().equals("彩票游戏")) {
                    Intent intent = new Intent(getContext(), type.intentTo);
                    intent.putExtra("data", gameBeanJson);
                    startActivityForResult(intent, position);
                } else if (type.intentTo != null) {
                    Intent intent = new Intent(getContext(), type.intentTo);
                    intent.putExtra("data", caipiaoData);
                    startActivityForResult(intent, 2);
                }

            }
        });

        hotGameAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (position == 7) {
                    Intent intent = new Intent(getContext(), HotGameMoreActivity.class);
                    String data = new Gson().toJson(mTryModeList);
                    intent.putExtra("data", data);
                    startActivityForResult(intent, 6);
                    return;
                }
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
                        instance.skipLoginActivity(getActivity(), LoginActivity.class, "HomeFragment");
                    } else if (BaseApi.GAME_MODEL_OPEN && !canTry) {
                        ToastUtil.showShort(getContext(), "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebActivity(getContext(), url.replace("[host]", "whh4488.com").replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), paraMap.get("name"));
                    }
                } else {
                    WebUtil.toWebActivity(getContext(), url.replace("[host]", "whh4488.com").replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), paraMap.get("name"));
                }
            }
        });
    }

    private void initData() {
        //新通知
        getNewNotice();
        requestAllData();
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestAllData();
            }
        });
    }

    private void requestAllData() {
        if (JudgeNewWorkUtil.isNetworkAvalible(getContext())) {
            //轮播图
            getBannerData();
            //跑马灯
            getMarqueeData();
            //游戏列表
            getGameListData();
            //我的收藏
            getMyCollectionData();
            //热门游戏
            getHotGame();
        } else {
            ToastUtil.showShort(getContext(), "网络异常，请检查网络设置！");
            refreshView.setRefreshing(false);
        }
    }


    private void getHotGame() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiRequestHotGame(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastUtil.showShort(getContext(), "热门游戏请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                hotGameList = new ArrayList<>();
                try {
                    String s = responseBody.string();
                    HotGameBean hotGameBean = new Gson().fromJson(s, HotGameBean.class);
                    //热门推荐的游戏设置
                    if (hotGameBean.getData().size() > 0) {
                        llHotGame.setVisibility(View.VISIBLE);
                        if (hotGameBean.getData().size() >= 8) {
                            for (int i = 0; i < 8; i++) {
                                hotGameList.add(hotGameBean.getData().get(i));
                            }
                            hotGameAdapter.setData(hotGameList);
                        } else {
                            hotGameAdapter.setData(hotGameBean.getData());
                        }
                    } else {
                        llHotGame.setVisibility(View.GONE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getMarqueeData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAds(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                ToastUtil.showShort(getContext(), "跑马灯请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String marqueeData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(marqueeData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        MarqueeBean marqueeBean = new Gson().fromJson(marqueeData, MarqueeBean.class);
                        homeTextvhiewPmd.setText(marqueeBean.getData().getContent());
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "HomeFragment");
                        } else {
                            ToastUtil.showShort(getContext(), "跑马灯请求异常！");
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    //  ToastUtil.showLong(getContext(), "请求数据异常！");
                }

            }
        });
    }

    private void getNewNotice() {
        Map<String, String> map = new HashMap<>();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        map.clear();
        map.put("type", "app");
        instance.apiNewNotice("app", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String mindCollectionData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(mindCollectionData);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        NewNoticeBean newNoticeBean = new Gson().fromJson(mindCollectionData, NewNoticeBean.class);
                        if (newNoticeBean.getData().size() != 0 && newNoticeBean.getData() != null) {
                            showDialogNews(newNoticeBean.getData().get(0).getMsg());
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    //  ToastUtil.showLong(getContext(), "请求数据异常！");
                }
            }
        });
    }

    private void showDialogNews(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);

        RelativeLayout loginDialog = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.main_news_dialog, null);
        Button submit = (Button) loginDialog.findViewById(R.id.bt_submit_discount);
        TextView et_discount_pt = (TextView) loginDialog.findViewById(R.id.tv_news_content);
        et_discount_pt.setText(msg);

        alertDialogBuilder.setView(loginDialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void getMyCollectionData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiGetMindCollectionList(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
//                ToastUtil.showShort(getContext(), "我的收藏请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mindCollectionList = new ArrayList<>();
                try {
                    String mindCollectionData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(mindCollectionData);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        mindCollectionBean = new Gson().fromJson(mindCollectionData, MindCollectionBean.class);
                        if (mindCollectionBean.getData().size() == 0 || "".equals(mindCollectionBean.getData().get(0).getPara())) {
                            //没有收藏游戏
                            myCollectionAdapter.clearDate();
                            viewTypeForCollection.clear();
                            llMyCollection.setVisibility(View.GONE);
                            tvEdit.setText("编辑");
                        } else {
                            //有收藏游戏
                            llMyCollection.setVisibility(View.VISIBLE);
                            mindCollectionList.addAll(mindCollectionBean.getData());
                            if (mindCollectionList.size() <= 4) {
                                llLineDownCollection.setVisibility(View.GONE);
                                myCollectionAdapter.setData(mindCollectionList);
                            } else {
                                llLineDownCollection.setVisibility(View.VISIBLE);
                                if (tvEdit.getText().equals("编辑")) {
                                    //收藏游戏超过4个
                                    List<MindCollectionBean.DataBean> fourData = new ArrayList<MindCollectionBean.DataBean>();
                                    for (int i = 0; i < 4; i++) {
                                        fourData.add(mindCollectionList.get(i));
                                    }
                                    myCollectionAdapter.setData(fourData);
                                    ivIndicateCollection.setImageDrawable(getResources().getDrawable(R.drawable.drop_down));
                                    tvIndicateCollection.setText("展开");
                                } else {
                                    ivIndicateCollection.setImageDrawable(getResources().getDrawable(R.drawable.drop_up));
                                    tvIndicateCollection.setText("缩回");
                                    myCollectionAdapter.setData(mindCollectionList);
                                }
                            }

                            if (tvEdit.getText().equals("编辑")) {
                                for (int i = 0; i < mindCollectionBean.getData().size(); i++) {
                                    viewTypeForCollection.add(0);
                                }
                            } else {
                                for (int i = 0; i < mindCollectionBean.getData().size(); i++) {
                                    viewTypeForCollection.add(1);
                                }
                            }
                            myCollectionAdapter.setViewType(viewTypeForCollection);
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            llHotGame.setVisibility(View.VISIBLE);
                            llMyCollection.setVisibility(View.GONE);
                            UserSP.getSPInstance().setSuccess(UserSP.LOGIN_SUCCESS, -1);
                        } else {
                            ToastUtil.showShort(getContext(), "我的收藏请求异常！");
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    // ToastUtil.showLong(getContext(), "请求数据异常！");
                }
            }
        });

    }

    private void getGameListData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiGame(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                refreshView.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
//                ToastUtil.showShort(getContext(), "游戏列表请求异常！");
                refreshView.setRefreshing(false);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                refreshView.setRefreshing(false);
                try {
                    gameListData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(gameListData);
                    String mode = jsonObject.optJSONObject("data").optString("mode");
                    Message message = handler.obtainMessage();
                    if (mode.equals("try")) {
                        BaseApi.GAME_MODEL_OPEN = true;
                        message.what = 1;
                    } else {
                        BaseApi.GAME_MODEL_OPEN = false;
                        message.what = 0;
                    }
                    handler.sendMessage(message);
                    JSONArray jsonarr = jsonObject.getJSONObject("data").getJSONArray("game");
                    caipiaoData = jsonarr.get(2).toString();
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        HomeGameBean homeGameBean = new Gson().fromJson(gameListData, HomeGameBean.class);
                        homeGameList = new ArrayList<>();
                        homeGameList.addAll(homeGameBean.getData().getGame());

                        //。。。。。
                        HomeGameBean.DataBean.GameBean gameBean = new HomeGameBean.DataBean.GameBean();
                        gameBean.setTitle("更多");
                        homeGameList.add(gameBean);
                        homeGameAdapter.setData(homeGameList);

                        //试玩
                        mTryModeList.clear();
                        for (int i = 0; i < homeGameBean.getData().getGame().size(); i++) {
                            for (int j = 0; j < homeGameBean.getData().getGame().get(i).getItems().size(); j++) {
                                if (homeGameBean.getData().getGame().get(i).getItems().get(j).isTrymode()) {
                                    mTryModeList.add(homeGameBean.getData().getGame().get(i).getItems().get(j).getKey());
                                }
                            }
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "HomeFragment");
                        } else {
                            ToastUtil.showShort(getContext(), "游戏列表请求异常！");
                        }
                    }
//                    Log.e("cww", gameListData);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    //   ToastUtil.showLong(getContext(), "请求数据异常！");
                }

            }
        });

    }

    private void getBannerData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiBanner(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
//                ToastUtil.showShort(getContext(), "轮播图请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String bannerData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(bannerData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        imgList = new ArrayList<>();
                        webUrlList = new ArrayList<>();
                        BannerDataBen bannerDataBen = new Gson().fromJson(bannerData, BannerDataBen.class);
                        for (int i = 0; i < bannerDataBen.getData().size(); i++) {
                            imgList.add(bannerDataBen.getData().get(i).getImg());
                            webUrlList.add(bannerDataBen.getData().get(i).getUrl());
                        }
                        mHandler.sendEmptyMessage(BANNER);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "HomeFragment");
                        } else {
                            ToastUtil.showShort(getContext(), "轮播图请求异常");
                        }

                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    // ToastUtil.showLong(getContext(), "请求数据异常！");
                }

            }
        });

    }

    private void setBanner(List<String> imgList, final List<String> webList) {
        List<String> mList1 = new ArrayList<>();
        mList1.add("https://vns95777.com/m/./data/templates/v.3/images/banner/0.jpg");
        mList1.add("https://vns95777.com/m/./data/templates/v.3/images/banner/9.jpg");
        mList1.add("https://vns95777.com/m/./data/templates/v.3/images/banner/1.jpg");
        mList1.add("https://vns95777.com/m/./data/templates/v.3/images/banner/2.jpg");
        mList1.add("https://vns95777.com/m/./data/templates/v.3/images/banner/4.jpg");
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(imgList);
        //设置banner动画效果
        homeBanner.setBannerAnimation(Transformer.Default);
        //设置2自动轮播，默认为true
        homeBanner.isAutoPlay(true);
        //设置轮播时间
        homeBanner.setDelayTime(4500);
        //设置指示器位置（当banner模式中有指示器时）
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);

        homeBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int i) {
                WebUtil.toWebDiscountActivity(getContext(), webList.get(i).replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)), "");
            }
        });

        homeBanner.start();
    }

    @OnClick({R.id.tv_edit, R.id.ll_line_down_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_edit: {
                if (mindCollectionBean != null) {
                    viewTypeForCollection = new ArrayList<>();
                    if (tvEdit.getText().equals("编辑")) {
                        tvEdit.setText("完成");
                        for (int i = 0; i < mindCollectionBean.getData().size(); i++) {
                            viewTypeForCollection.add(1);
                        }
                        myCollectionAdapter.setViewType(viewTypeForCollection);
                    } else {
                        tvEdit.setText("编辑");
                        for (int i = 0; i < mindCollectionBean.getData().size(); i++) {
                            viewTypeForCollection.add(0);
                        }
                        myCollectionAdapter.setViewType(viewTypeForCollection);
                    }
                }
            }
            break;
            case R.id.ll_line_down_collection: {
                if ("展开".equals(tvIndicateCollection.getText().toString())) {
                    tvIndicateCollection.setText("缩回");
                    ivIndicateCollection.setImageDrawable(getResources().getDrawable(R.drawable.drop_up));
                    myCollectionAdapter.setData(mindCollectionList);
                    scrollviewToBottom();
                } else {
                    tvIndicateCollection.setText("展开");
                    ivIndicateCollection.setImageDrawable(getResources().getDrawable(R.drawable.drop_down));
                    List<MindCollectionBean.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        data.add(mindCollectionList.get(i));
                    }
                    myCollectionAdapter.setData(data);
                }

            }
            break;
        }
    }

    private void scrollviewToBottom() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, scrollView.getChildAt(0).getHeight());
            }
        });
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
//            FrescoUtil.loadAllGifPicOnNet(simpleDraweeView, (String) path);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .crossFade()
                    .fitCenter()
                    .placeholder(getResources().getDrawable(R.drawable.default_img2))
                    .into(imageView);

        }

//        @Override
//        public ImageView createImageView(Context context) {
////            使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
//            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
//            return simpleDraweeView;
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                //真人视讯
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
            case 1: {
                //电子游艺
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
            case 2: {
                //彩票游戏
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
            case 3: {
                //体育赛事
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
            case 4: {
                //捕鱼达人
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
            case 5: {
                //六合彩
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
            case 6: {
                //热门推荐
                if (resultCode == 1) {
                    getMyCollectionData();
                    getGameListData();
                }
            }
            break;
        }
    }

    public void setRefreshCollection() {
        getMyCollectionData();
        getGameListData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuccessBean ls) {
        if ("success".equals(ls.getStatus()) && "login".equals(ls.getType())) {
            getMyCollectionData();
            getGameListData();
            getNewNotice();
        } else if ("success".equals(ls.getStatus()) && "loginOut".equals(ls.getType())) {
            getMyCollectionData();
            getGameListData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && userSP.getInt(UserSP.LOGIN_SUCCESS) != -1) {
            getMyCollectionData();
            getGameListData();
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
