package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.bean.AboutUsBean2;
import com.wishland.www.wanhaohui2.bean.AppUpdateBean;
import com.wishland.www.wanhaohui2.bean.NewNoticeBean;
import com.wishland.www.wanhaohui2.bean.QuestionBean2;
import com.wishland.www.wanhaohui2.bean.ServiceBean;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.service.AlarmService;
import com.wishland.www.wanhaohui2.utils.AnimationUtil;
import com.wishland.www.wanhaohui2.utils.BgAlphaUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.MD5Utils;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.PhoneInfoUtils;
import com.wishland.www.wanhaohui2.utils.StatusBarHightUtil;
import com.wishland.www.wanhaohui2.utils.TabEntity;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.LeftPopupWindows;
import com.wishland.www.wanhaohui2.view.fragment.DiscountFragment;
import com.wishland.www.wanhaohui2.view.fragment.HomeFragment;
import com.wishland.www.wanhaohui2.view.fragment.LinearFragment;
import com.wishland.www.wanhaohui2.view.fragment.MindFragment;
import com.wishland.www.wanhaohui2.view.fragment.SavingFragment;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.wishland.www.wanhaohui2.model.UserSP.LOGIN_USERNAME;

public class MainActivity extends AutoLayoutActivity {

    private static final int HOMEFRAGMENT = 0;
    private static final int DISCOUNTFRAGMENT = 1;
    private static final int SAVINGFRAGMENT = 2;
    private static final int LINEFRAGMENT = 3;
    private static final int MINDFRAGMENT = 4;

    @BindView(R.id.tl_1)
    CommonTabLayout tl1;
    @BindView(R.id.ll_main_top)
    LinearLayout llMainTop;
    @BindView(R.id.iv_function)
    ImageView leftPopup;
    @BindView(R.id.main_linearLayout)
    FrameLayout main_linearLayout;
    @BindView(R.id.ll_register)
    LinearLayout llRegister;
    @BindView(R.id.ll_play_try)
    LinearLayout llPlayTry;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.iv_hongbao)
    ImageView ivHongbao;
    @BindView(R.id.fl_hongbao_money_miss)
    FrameLayout flHongbaoMoneyMiss;
    @BindView(R.id.iv_hongbao_top_miss)
    ImageView ivHongbaoTopMiss;
    @BindView(R.id.tv_hongbao_miss)
    TextView tvHongbaoMiss;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"首页", "优惠", "存款", "额度转换", "我的"};
    private int nowFragment = 0;
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private DiscountFragment discountFragment;
    private SavingFragment savingFragment;
    private LinearFragment linearFragment;
    private MindFragment mindFragment;
    private LeftPopupWindows leftPopupWindows;
    private Model instance;
    private UserSP userSP;
    private int[] mIconUnselectIds = {R.drawable.icon_home_normal, R.drawable.icon_coupon_normal,
            R.drawable.icon_deposit_normal, R.drawable.icon_line_normal, R.drawable.icon_user_normal};
    private int[] mIconSelectIds = {R.drawable.icon_home_pressed, R.drawable.icon_coupon_pressed,
            R.drawable.icon_deposit_pressed, R.drawable.icon_line_pressed, R.drawable.icon_user_pressed};
    private String questionUrl;
    private String aboutUsUrl;
    private String mdPhoneInfo;
    private Map<String, Object> phone;
    @SuppressLint("HandlerLeak")
    private Handler modeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    tvModel.setText("试玩");
                }
                break;
                case 1: {
                    tvModel.setText("取消");
                }
                break;
            }

        }
    };
    private PopupWindow pop;
    private boolean isGetHongbao = false;
    private int whickAv = 0;

//    int[] locationHongBao = new int[2];
//    int[] locationHongBaoMoney = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //处理popupwind背景透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        initStatusBar();
        EventBus.getDefault().register(this);
        init();

    }

    private void initStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llMainTop.getLayoutParams();
        lp.setMargins(0, StatusBarHightUtil.getStatusBarHeight(), 0, 0);
        llMainTop.setLayoutParams(lp);
    }


    private void init() {
        PhoneInfoUtils phoneInfoUtils = new PhoneInfoUtils();
        phone = phoneInfoUtils.getPhone(this);
        String stringBuffer = phoneInfoUtils.getString(phone).toString();
        mdPhoneInfo = MD5Utils.toMD5(stringBuffer);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        initTabLayout();
        showFragment(HOMEFRAGMENT);
//        requestGlobal();
        requestService();
        checkUpdateApp();
        setPopupWindows();
        getQuestionList();
        getAboutUsList();
        startService();

        checkCanGetHongBao("5");
    }

    private void checkCanGetHongBao(String activityID) {
        Map<String, String> map = new HashMap<>();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map != null) {
            map.clear();
        }
        map.put("activity_id", activityID);
        instance.apiInitHongBao(activityID, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("cww", e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String mindCollectionData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(mindCollectionData);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        isGetHongbao = false;
                        initHongBao(false, 0);
                    } else {
                        ivHongbao.setVisibility(View.GONE);

                        /***
                         * 当普通红包已领取时 才真正的开始处理AV 女优红包
                         */
                        checkCanGetHongBaoFromAV("6", "123");
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    /***
                     * 当普通红包无法领取时 才真正的开始处理AV 女优红包
                     */
                    checkCanGetHongBaoFromAV("6", "123");
                    //  ToastUtil.showLong(getContext(), "请求数据异常！");
                }
            }
        });
    }

    private void checkCanGetHongBaoFromAV(String activityID, String anyString) {
        Map<String, String> map = new HashMap<>();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map != null) {
            map.clear();
        }
        map.put("activity_id", activityID);
        map.put("dayonce", anyString);
        instance.apiInitHongBaoFromAV(activityID, anyString, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            //        map.put("test", "test");
//        instance.apiInitHongBaoFromAVTEST(activityID, anyString,"test", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
//        instance.apiInitHongBaoFromAV(activityID, anyString, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
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
                    Log.e("FromAV", mindCollectionData);
                    JSONObject jsonObject = instance.getJsonObject(mindCollectionData);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        isGetHongbao = false;
                        JSONObject jsobject = jsonObject.optJSONObject("data");
                        int whichAV = jsobject.optInt("seq");
                        initHongBao(true, whichAV);
                    } else {
                        ivHongbao.setVisibility(View.GONE);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    //  ToastUtil.showLong(getContext(), "请求数据异常！");
                }
            }
        });
    }

    /**
     * 一般红包 还是 女优红包
     *
     * @param contentView
     */
    private void getHongBaoAmount(View contentView, final boolean isFromAV, int whichPicture) {
        if (contentView == null) {
            return;
        }

        /***
         * 女优红包 的 图片背景
         */
        final Button btUseHongbao = (Button) contentView.findViewById(R.id.bt_use_hongbao);
        final FrameLayout flHongbaoMoney = (FrameLayout) contentView.findViewById(R.id.fl_hongbao_money);
        final ImageView ivHongbaoCelLeft = (ImageView) contentView.findViewById(R.id.iv_hongbao_cel_left);
        final ImageView ivHongbaoCelRight = (ImageView) contentView.findViewById(R.id.iv_hongbao_cel_right);
        final TextView tx_hongbao_money = (TextView) contentView.findViewById(R.id.tx_hongbao_money);
        final Button btGetHongbao = (Button) contentView.findViewById(R.id.bt_get_hongbao);
        final FrameLayout flHongbao = (FrameLayout) contentView.findViewById(R.id.fl_pb_hongbao);
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();

        /***
         * 女优节 红包
         */
        if (isFromAV) {
            map.put("activity_id", "6");
            map.put("dayonce", "123");
            instance.apiGetHongBaoFromAV("6", "123", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    flHongbao.setVisibility(View.GONE);
                    btGetHongbao.setClickable(true);
                    Log.e("cww", e.getMessage());
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    flHongbao.setVisibility(View.GONE);
                    btGetHongbao.setClickable(true);
                    try {
                        String mindCollectionData = responseBody.string();
                        JSONObject jsonObject = instance.getJsonObject(mindCollectionData);
                        instance.setToken_SP(jsonObject.optString("token"));
                        int status = jsonObject.optInt("status");
                        String message = jsonObject.optString("errorMsg");
                        if (status == 200) {
                            if (jsonObject.optJSONObject("data") != null) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data.optString("amount") != null) {
                                    btUseHongbao.setVisibility(View.VISIBLE);
                                    btGetHongbao.setVisibility(View.GONE);
                                    AnimationUtil.Translate(flHongbaoMoney, 0.0f, 0.0f, 0.0f, -0.9f, 500, 100);
                                    AnimationUtil.Alpha_Translate(ivHongbaoCelLeft, 0.0f, 0.0f, -0.9f, -0.85f, 200, 600);
                                    AnimationUtil.Alpha_Translate(ivHongbaoCelRight, 0.0f, 0.0f, 0.9f, -0.85f, 200, 600);
                                    tx_hongbao_money.setText(data.optString("amount"));
                                }
                            }
                        } else {
                            ToastUtil.showShort(MainActivity.this, message);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        //  ToastUtil.showLong(getContext(), "请求数据异常！");
                    }
                }
            });

        } else {
            map.put("activity_id", "5");
            instance.apiGetHongBao("5", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    flHongbao.setVisibility(View.GONE);
                    btGetHongbao.setClickable(true);
                    Log.e("cww", e.getMessage());
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    flHongbao.setVisibility(View.GONE);
                    btGetHongbao.setClickable(true);
                    try {
                        String mindCollectionData = responseBody.string();
                        JSONObject jsonObject = instance.getJsonObject(mindCollectionData);
                        instance.setToken_SP(jsonObject.optString("token"));
                        int status = jsonObject.optInt("status");
                        String message = jsonObject.optString("errorMsg");
                        if (status == 200) {
                            if (jsonObject.optJSONObject("data") != null) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data.optString("amount") != null) {
                                    btUseHongbao.setVisibility(View.VISIBLE);
                                    btGetHongbao.setVisibility(View.GONE);
                                    AnimationUtil.Translate(flHongbaoMoney, 0.0f, 0.0f, 0.0f, -0.9f, 500, 100);
                                    AnimationUtil.Alpha_Translate(ivHongbaoCelLeft, 0.0f, 0.0f, -0.9f, -0.85f, 200, 600);
                                    AnimationUtil.Alpha_Translate(ivHongbaoCelRight, 0.0f, 0.0f, 0.9f, -0.85f, 200, 600);
                                    tx_hongbao_money.setText(data.optString("amount"));
                                }
                            }
                        } else {
                            ToastUtil.showShort(MainActivity.this, message);
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        //  ToastUtil.showLong(getContext(), "请求数据异常！");
                    }
                }
            });
        }

        flHongbao.setVisibility(View.VISIBLE);
        btGetHongbao.setClickable(false);
        btUseHongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                ivHongbao.setVisibility(View.GONE);
                if (isFromAV) {
                    String favor = UserSP.getSPInstance().getFavorIp();
                    String username = UserSP.getSPInstance().getString(LOGIN_USERNAME);
                    String toActivityUrl = favor + "m/activity/activity_index.php?username=" + username;
                    Log.e("btUseHongbao-->", toActivityUrl);
                    WebUtil.toSimpleWebActivity(MainActivity.this, toActivityUrl);
                } else {
                    showFragment(LINEFRAGMENT);

                }
            }
        });


    }


    private void initHongBao(boolean isFromAV, int whichAVPic) {
        hongBaoPopWindow(isFromAV, whichAVPic);
        BgAlphaUtil.setBackgroundAlpha(0.5f, MainActivity.this);
    }

    private void initHongBaoView() {
//        ViewTreeObserver vto = llPlayTry.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onGlobalLayout() {
//                llPlayTry.getLocationOnScreen(locationHongBao);
//                llPlayTry.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                Log.e("cww", "x:" + locationHongBao[0] + ",y:" + locationHongBao[1]);
//            }
//        });

//        ViewTreeObserver vto1 = tvHongbao.getViewTreeObserver();
//        vto1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onGlobalLayout() {
//                tvHongbao.getLocationOnScreen(locationHongBaoMoney);
//                tvHongbao.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                Log.e("cww", "x:" + locationHongBaoMoney[0] + ",y:" + locationHongBaoMoney[1]);
//            }
//        });
    }


    private void checkUpdateApp() {
        checkUpdateHost();
    }

    private void checkUpdateHost() {
        checkUpdate("https://tpfw.083075.com/system/getAppLastChange");
    }

    private void checkUpdate(String versionHost) {
        final String UPDATE_Info = getResources().getString(R.string.UPDATE_Info);
        String code = getResources().getString(R.string.VersionCode);
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(versionHost)) {
            return;
        }
        OkGo.post(versionHost)
                .params("code", code)
                .execute(new AbsCallback<String>() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        final AppUpdateBean appUpdate = new Gson().fromJson(s, AppUpdateBean.class);
                        int appVersion = getAPPRealVersion();
                        int currentVersion = dealVersion(appUpdate.getEntity().getVersion());
                        if (appVersion < currentVersion) {
                            MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this);
                            builder.title("发现新版本");
                            if (4 == appUpdate.getEntity().getVersionType()) {
                                builder.cancelable(false)
                                        .autoDismiss(false);
                                builder.content("重大版本更新，请下载安装新版本后继续使用\n" + UPDATE_Info);
                            } else if (3 == appUpdate.getEntity().getVersionType()) {
                                builder.content(UPDATE_Info)
                                        .negativeText("暂不升级")
                                        .negativeColor(getResources().getColor(R.color.text_hint))
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                            }
                                        });
                            }
                            builder.positiveText("点击升级")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            try {
                                                Uri uri = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next());
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }
                });
    }

    private int dealVersion(String s) {
        if (s.contains(".")) {
            s = s.replace(".", "");
        }
        return Integer.valueOf(s);
    }

    private void requestService() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiOnlineSerivce(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                // LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(getBaseContext(), "请求客服地址出错！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String serviceData = responseBody.string();
                    ServiceBean serviceBean = new Gson().fromJson(serviceData, ServiceBean.class);
                    if (serviceBean.getStatus() == 200) {
                        for (int i = 0; i < serviceBean.getData().size(); i++) {
                            if ("service".equals(serviceBean.getData().get(i).getCode())) {
                                BaseApi.CustomHtml5 = serviceBean.getData().get(i).getContent();
                            }
                        }

                    } else {
                        ToastUtil.showShort(getBaseContext(), "请求客服地址出错！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTabLayout() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tl1.setTabData(mTabEntities);
        //fragment内部性能问题，可以用Fragmentation解决
        tl1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                /**
                 * 切换速度太快会导致
                 *   java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
                 */
                try {
                    showFragment(position);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    public void showFragment(int fragmentId) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment();
        switch (fragmentId) {
            case HOMEFRAGMENT:
                nowFragment = HOMEFRAGMENT;
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.main_linearLayout, homeFragment);
                    homeFragment.setHandler(modeHandler);
                }
                llMainTop.setVisibility(View.VISIBLE);
                fragmentTransaction.show(homeFragment);
                fragmentTransaction.commit();
                break;
            case DISCOUNTFRAGMENT:
                nowFragment = DISCOUNTFRAGMENT;
                if (discountFragment == null) {
                    discountFragment = new DiscountFragment();
                    fragmentTransaction.add(R.id.main_linearLayout, discountFragment);
                }
                llMainTop.setVisibility(View.GONE);
                fragmentTransaction.show(discountFragment);
                fragmentTransaction.commit();
                break;
            case SAVINGFRAGMENT:
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    instance.skipLoginActivity(this, LoginActivity.class, "mainActivity");
                    return;
                }
                nowFragment = SAVINGFRAGMENT;
                if (savingFragment == null) {
                    savingFragment = new SavingFragment();
                    fragmentTransaction.add(R.id.main_linearLayout, savingFragment);
                }
                llMainTop.setVisibility(View.GONE);
                fragmentTransaction.show(savingFragment);
                fragmentTransaction.commit();
                break;
            case LINEFRAGMENT:
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    instance.skipLoginActivity(this, LoginActivity.class, "mainActivity");
                    return;
                }
                nowFragment = LINEFRAGMENT;
                if (linearFragment == null) {
                    linearFragment = new LinearFragment();
                    fragmentTransaction.add(R.id.main_linearLayout, linearFragment);
                }
                llMainTop.setVisibility(View.GONE);
                fragmentTransaction.show(linearFragment);
                fragmentTransaction.commit();
                break;
            case MINDFRAGMENT:
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    instance.skipLoginActivity(this, LoginActivity.class, "mainActivity");
                    return;
                }
                nowFragment = MINDFRAGMENT;
                if (mindFragment == null) {
                    mindFragment = new MindFragment();
                    fragmentTransaction.add(R.id.main_linearLayout, mindFragment);
                }
                llMainTop.setVisibility(View.GONE);
                fragmentTransaction.show(mindFragment);
                fragmentTransaction.commit();
                break;
        }
        tl1.setCurrentTab(nowFragment);
    }

    private void hideAllFragment() {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (discountFragment != null) {
            fragmentTransaction.hide(discountFragment);
        }
        if (savingFragment != null) {
            fragmentTransaction.hide(savingFragment);
        }
        if (linearFragment != null) {
            fragmentTransaction.hide(linearFragment);
        }
        if (mindFragment != null) {
            fragmentTransaction.hide(mindFragment);
        }
    }


    @OnClick({R.id.iv_function, R.id.ll_service, R.id.ll_register, R.id.ll_play_try, R.id.iv_hongbao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_function: {
                startActivity(new Intent(this, DepositActivity.class));
            }
            break;
            case R.id.ll_service: {
                WebUtil.toWebActivity(this, BaseApi.CustomHtml5, "客服");
            }
            break;
            case R.id.ll_register: {
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("fromType", "main");
                startActivity(intent);
            }
            break;
            case R.id.ll_play_try: {
                changeGameModel();
            }
            break;
            case R.id.iv_hongbao: {

                checkCanGetHongBao("5");
//                initHongBao(isAvHongbao, whickAv);
            }
            break;
        }
    }

    private void hongBaoPopWindow(final boolean isFromAV, final int whichAVPic) {
        final View content_view = LayoutInflater.from(this).inflate(R.layout.bg_hongbao, null);
        pop = new PopupWindow(content_view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                true);
        ImageView ivClose = (ImageView) content_view.findViewById(R.id.iv_hongbao_close);
        final Button btGetHongbao = (Button) content_view.findViewById(R.id.bt_get_hongbao);

        /***
         * 女优红包 的 图片背景
         */
        final ImageView bg_hongbao_with_av = (ImageView) content_view.findViewById(R.id.bg_hongbao_with_av);
        final Button btUseHongbao = (Button) content_view.findViewById(R.id.bt_use_hongbao);
        final TextView tx_hongbao_text = (TextView) content_view.findViewById(R.id.tx_hongbao_text);

        /***
         * 女优节 红包
         */
        if (isFromAV) {
            whickAv = whichAVPic;
            btUseHongbao.setBackgroundResource(R.drawable.bt_hongbao_use_n);
            bg_hongbao_with_av.setImageResource(R.drawable.hongbao_top_normal);
            switch (whichAVPic) {
                case 1:
                    bg_hongbao_with_av.setImageResource(R.drawable.hongbao_top_1);
                    tx_hongbao_text.setText("吉沢明歩 给您发来一个红包");
                    tvHongbaoMiss.setText("吉沢明歩 给您发来一个红包");
                    ivHongbaoTopMiss.setImageResource(R.drawable.hongbao_top_1);
                    break;
                case 2:
                    bg_hongbao_with_av.setImageResource(R.drawable.hongbao_top_2);
                    tx_hongbao_text.setText("Miliya 给您发来一个红包");
                    tvHongbaoMiss.setText("Miliya 给您发来一个红包");
                    ivHongbaoTopMiss.setImageResource(R.drawable.hongbao_top_2);
                    break;
                case 3:
                    bg_hongbao_with_av.setImageResource(R.drawable.hongbao_top_3);
                    tx_hongbao_text.setText("橋本有菜 给您发来一个红包");
                    tvHongbaoMiss.setText("橋本有菜 给您发来一个红包");
                    ivHongbaoTopMiss.setImageResource(R.drawable.hongbao_top_3);
                    break;
            }
        } else {
            btUseHongbao.setBackgroundResource(R.drawable.bt_hongbao_use);
            bg_hongbao_with_av.setImageResource(R.drawable.hongbao_top_normal);
            tx_hongbao_text.setText("App 体验红包大派送");
            tvHongbaoMiss.setText("App 体验红包大派送");
            ivHongbaoTopMiss.setImageResource(R.drawable.hongbao_top_normal);
        }


        pop.setBackgroundDrawable(new ColorDrawable(0xffffff));//支持点击Back虚拟键退出
        pop.showAtLocation(getCurrentFocus(), Gravity.CENTER, 0, 0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BgAlphaUtil.setBackgroundAlpha(1.0f, MainActivity.this);
                if (!isGetHongbao) {
                    animationHBMiss();
                } else {
                    ivHongbao.setVisibility(View.GONE);
                    flHongbaoMoneyMiss.setAlpha(0);
                }
                /**
                 * 当 普通红包 的DIALOG 结束后
                 * 开始进行女优红包判断
                 */
                if (!isFromAV) {
                    checkCanGetHongBaoFromAV("6", "123");
                }
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                flHongbaoMoneyMiss.setAlpha(1);
                flHongbaoMoneyMiss.setVisibility(View.VISIBLE);
                if (!isGetHongbao) {
                    animationHBMiss();
                } else {
                    ivHongbao.setVisibility(View.GONE);
                    flHongbaoMoneyMiss.setAlpha(0);
                }
            }
        });


        btGetHongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGetHongbao = true;
                getHongBaoAmount(content_view, isFromAV, whichAVPic);

                if (isFromAV) {
                    tx_hongbao_text.setText("AV女优发牌活动火热进行中");
                    tvHongbaoMiss.setText("AV女优发牌活动火热进行中");
                } else {
                    tx_hongbao_text.setText("App 体验红包大派送");
                    tvHongbaoMiss.setText("App 体验红包大派送");
                }
            }
        });
    }

    private void animationHBMiss() {
        AnimationUtil.Scale_Translate(flHongbaoMoneyMiss, 0.0f, 0.0f, 0.42f, -0.42f, 500, 0).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flHongbaoMoneyMiss.setAlpha(0);
                ivHongbao.setVisibility(View.VISIBLE);
                AnimationUtil.Rotate(ivHongbao, 0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setExitApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setExitApp() {
        View view = View.inflate(this, R.layout.layout_exit, null);
        TextView mLine_success_message = (TextView) view.findViewById(R.id.line_success_message);
        TextView mLine_success_fromto = (TextView) view.findViewById(R.id.line_success_from_to);
        TextView mLine_success_money = (TextView) view.findViewById(R.id.line_success_money);
        TextView tv_line = (TextView) view.findViewById(R.id.tv_line);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_linear);
        Button ld_line = (Button) view.findViewById(R.id.ld_line);
        Button lo_line = (Button) view.findViewById(R.id.lo_line);
        linear.setVisibility(View.VISIBLE);
        tv_line.setVisibility(View.GONE);
        mLine_success_money.setVisibility(View.GONE);
        mLine_success_message.setText("退出");
        mLine_success_fromto.setText("确定要退出万豪会吗?");
        AlertDialog.Builder alertDialog = instance.getAlertDialog(this);
        alertDialog.setView(view)
                .setCancelable(false);
        final AlertDialog show = alertDialog.show();
        ld_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });


        lo_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                MainActivity.this.finish();
                stopService();
                ActivityManager.getActivityManager().deleteAllActivity();
                System.exit(0);
            }
        });
    }

//    public void requestGlobal() {
//        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
//
//        instance.apiGlobal(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ToastUtil.showShort(MainActivity.this, "全局信息请求异常");
//            }
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                try {
//                    String string = responseBody.string();
//                    JSONObject jsonObject = instance.getJsonObject(string);
//                    instance.setToken_SP(jsonObject.optString("token"));
//                    int status = jsonObject.optInt("status");
//                    LogUtil.e("全局信息获取成功:" + string);
//                    if (status == 200) {
//                        instance.getAccountDataSP().setA_Global(AccountDataSP.ACCOUNT_GLOBAL, string);
//                    } else {
//                        String errorMsg = jsonObject.optString("errorMsg");
//                        if (errorMsg.equals("用户未登录")) {
//                            instance.skipLoginActivity(MainActivity.this, LoginActivity.class);
//                        } else {
//                            ToastUtil.showShort(MainActivity.this, errorMsg);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    public int getAPPRealVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && homeFragment != null) {
            homeFragment.setRefreshCollection();
//            tl1.setCurrentTab(nowFragment);
        } else {
            tl1.setCurrentTab(nowFragment);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuccessBean ls) {
        if ("success".equals(ls.getStatus()) && "loginOut".equals(ls.getType())) {
            removeFragment();
        } else if ("success".equals(ls.getStatus()) && "login".equals(ls.getType())) {
            tl1.setCurrentTab(nowFragment);
            checkCanGetHongBao("5");
//            checkCanGetHongBaoFromAV("6", "123");
            judgePhone();
        } else if ("success".equalsIgnoreCase(ls.getStatus()) && "register".equalsIgnoreCase(ls.getType())) {
            tl1.setCurrentTab(nowFragment);
            checkCanGetHongBao("5");
//            checkCanGetHongBaoFromAV("6", "123");
            judgePhone();
        }
    }

    private void removeFragment() {
        nowFragment = HOMEFRAGMENT;
        tl1.setCurrentTab(nowFragment);
        showFragment(nowFragment);
        if (discountFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(discountFragment).commit();
            discountFragment = null;
        }
        if (savingFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(savingFragment).commit();
            savingFragment = null;
        }
        if (linearFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(linearFragment).commit();
            linearFragment = null;
        }
        if (mindFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(mindFragment).commit();
            mindFragment = null;
        }
    }


    private void setPopupWindows() {
        leftPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftPopupWindows = new LeftPopupWindows(MainActivity.this, leftonclick);
                leftPopupWindows.showAtLocation(main_linearLayout, Gravity.LEFT, 0, 0);
                leftPopupWindows.setWindowAlpa(true);
                leftPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        leftPopupWindows.setWindowAlpa(false);
                    }
                });
            }
        });
    }

    private View.OnClickListener leftonclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            leftPopupWindows.dismiss();
            switch (v.getId()) {
                case R.id.shenqing:
                    //热门优惠
//                    startActivity(new Intent(MainActivity.this,MyDiscountActivity.class));
                    tl1.setCurrentTab(DISCOUNTFRAGMENT);
                    showFragment(1);
                    break;

                case R.id.shenpi:
                    //下载客户端

                    break;
                case R.id.person_seting:
                    if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                        //跳转到登录页面
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    } else {
                        tl1.setCurrentTab(DISCOUNTFRAGMENT);
                        showFragment(4);
                    }
                    break;

                case R.id.gongxiangwj:
                    //常见问题
                    if (questionUrl != null && !"".equals(questionUrl)) {
                        WebUtil.toWebActivity(MainActivity.this, questionUrl, "常见问题");
                    }
//                    startActivity(new Intent(MainActivity.this, QuestionAndAnswerActivity.class));
                    break;

                case R.id.iv_popup_vip:
                    //会员等级
                    startActivity(new Intent(MainActivity.this, MemberRatingActivity.class));
                    break;
                case R.id.about_us:
                    //关于我们
                    if (aboutUsUrl != null && !"".equals(aboutUsUrl)) {
                        WebUtil.toWebActivity(MainActivity.this, aboutUsUrl, "关于我们");
                    }
//                    startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                    break;
                case R.id.contact_us:
                    //联系我们
                    startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                    break;
                case R.id.ll_clean_cache:
                    //清除缓存
                    showDialog();
                    break;
            }
        }
    };

    private void showDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("确定清除缓存吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Glide.get(MainActivity.this).clearMemory();
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                imagePipeline.clearMemoryCaches();
                imagePipeline.clearDiskCaches();
                imagePipeline.clearCaches();
                ToastUtil.showShort(MainActivity.this, "清除成功");
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    //常见问题
    private void getQuestionList() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiHelp(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(MainActivity.this, "获取消息列表请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    LogUtil.i("linearResponse", data);
                    JSONObject jsonObject = instance.getJsonObject(data);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        QuestionBean2 questionBean = new Gson().fromJson(data, QuestionBean2.class);
                        LogUtil.i(questionBean.toString());
                        questionUrl = questionBean.getData().getUrl();
                    } else if (status == 301 || status == 332) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //关于我们
    private void getAboutUsList() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAboutUs(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(MainActivity.this, "获取消息列表请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    LogUtil.i("linearResponse", data);
                    JSONObject jsonObject = instance.getJsonObject(data);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        AboutUsBean2 aboutUsBean = new Gson().fromJson(data, AboutUsBean2.class);
                        aboutUsUrl = aboutUsBean.getData().getUrl();
                    } else if (status == 301 || status == 332) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
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
    protected void onResume() {
        super.onResume();
        if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
            llRegister.setVisibility(View.VISIBLE);
            llRegister.setClickable(true);
            llPlayTry.setVisibility(View.VISIBLE);
            llPlayTry.setClickable(true);
            if (BaseApi.GAME_MODEL_OPEN) {
                tvModel.setText("取消");
            } else {
                tvModel.setText("试玩");
            }
            ivHongbao.setVisibility(View.GONE);
        } else {
            llRegister.setVisibility(View.INVISIBLE);
            llRegister.setClickable(false);
            llPlayTry.setVisibility(View.INVISIBLE);
            llPlayTry.setClickable(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    //检测是否需要上传手机信息
    private void judgePhone() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("md5", mdPhoneInfo);

        instance.jPhone(mdPhoneInfo, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("判断是否上传手机信息完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("判断是否上传手机信息失败：" + e.getMessage());

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(str);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        boolean b = jsonObject.optJSONObject("data").optBoolean("exist");
                        if (!b) {
                            uploadPhoneInfo();
                        }
                    }
                    // LogUtil.e("判断是否上传手机信息成功：" + str);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void uploadPhoneInfo() throws JSONException {
        String spy = new JSONObject(phone).toString();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("spy", spy);
        map.put("md5", mdPhoneInfo);
        map.put("os", "android");

        instance.gPhone(spy, mdPhoneInfo, "android", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("上传手机信息完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("上传手机信息异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(str);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        LogUtil.e("上传手机信息成功：" + str + "123456789");
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopService();
    }

    /***
     * 启动service服务
     */
    Intent serviceIntent = null;

    public void startService() {
        Log.e("startService", "背景程序调用 开始！");
        if (serviceIntent == null) {
            serviceIntent = new Intent(this, AlarmService.class);
            startService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }


    /***
     * 停止service服务
     */
    public void stopService() {
        Log.e("stopService", "背景程序调用 关闭！");
        if (serviceIntent == null) {
            serviceIntent = new Intent(this, AlarmService.class);
            stopService(serviceIntent);
        } else {
            stopService(serviceIntent);
        }
    }

    private void changeGameModel() {
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiChangeGameModel(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                Log.e("cww", e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    String gameModel = instance.getJsonObject(s).optJSONObject("data").optString("mode");
                    if (gameModel != null && "try".equals(gameModel)) {
                        BaseApi.GAME_MODEL_OPEN = true;
                        ToastUtil.showShort(MainActivity.this, "进入试玩模式！");
                        tvModel.setText("取消");
                    } else {
                        BaseApi.GAME_MODEL_OPEN = false;
                        ToastUtil.showShort(MainActivity.this, "退出试玩模式！");
                        tvModel.setText("试玩");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
