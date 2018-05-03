package com.wishland.www.wanhaohui2.view.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.LevelBean;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;
import com.wishland.www.wanhaohui2.bean.MessageBean;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.StatusBarHightUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.BalanceDetailsActivity;
import com.wishland.www.wanhaohui2.view.activity.ComplaintAdviceActivity;
import com.wishland.www.wanhaohui2.view.activity.ContactUsActivity;
import com.wishland.www.wanhaohui2.view.activity.DrawActivity;
import com.wishland.www.wanhaohui2.view.activity.LineQueryActivity;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.activity.MainActivity;
import com.wishland.www.wanhaohui2.view.activity.MemberRatingActivity;
import com.wishland.www.wanhaohui2.view.activity.MyDiscountActivity;
import com.wishland.www.wanhaohui2.view.activity.MyMessageActivity;
import com.wishland.www.wanhaohui2.view.activity.OthersActivity;
import com.wishland.www.wanhaohui2.view.activity.PersonalInfActivity;
import com.wishland.www.wanhaohui2.view.activity.PlaceOrderActivity;
import com.wishland.www.wanhaohui2.view.activity.RecordQueryActivity;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.MyProgress;
import com.wishland.www.wanhaohui2.view.customlayout.MyProgress2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/7.
 */

public class MindNewFragment extends UmengFragment implements SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.tv_account_money)
    TextView tv_account_money;
    @BindView(R.id.ll_login_out)
    LinearLayout llLoginOut;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.iv_vip1)
    ImageView ivVip1;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.mp)
    MyProgress mp;
    @BindView(R.id.mp_two)
    MyProgress2 mp_two;
    @BindView(R.id.srl_refresh_mind)
    SwipeRefreshLayout srl_refresh_account;
    @BindView(R.id.view_red_bg)
    View view_red_bg;
    @BindView(R.id.tv_user_name)
    TextView userName;
    @BindView(R.id.ll_uv)
    LinearLayout llUV;
    private Model instance;
    private UserSP userSP;
    private static final Map<String, Class> SKIP = new HashMap<>();
    private LevelBean.LevelData.AmountData amount;
    private LevelBean.LevelData.NextLevelData nextlevel;
    private LevelBean.LevelData.LevelInfo levelInfo;
    private int progress;
    private int progress2;
    private boolean isrun = true;
    private boolean isrun2 = true;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mp != null) {
                        progress = mp.getProgress();
                        if (progress <= pb1) {
                            mp.setProgress(progress + 1, String.valueOf((int) ckje));
                            start(0);
                        } else {
                            isrun = false;
                        }
                    }
                    break;
                case 1:
                    if (mp_two != null) {
                        progress2 = mp_two.getProgress();
                        if (progress2 <= pb2) {
                            mp_two.setProgress(progress2 + 1, String.valueOf((int) ztzje));
                            start(1);
                        } else {
                            isrun2 = false;
                        }
                    }
                    break;
            }


        }
    };
    private int pb1;
    private int pb2;
    private double ckje;
    private double ztzje;
    private Map<String, String> map;

    private void start(int type) {
        switch (type) {
            case 0:
                if (isrun) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessageDelayed(msg, 5);
                }
                break;
            case 1:
                if (isrun2) {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessageDelayed(msg, 5);
                }
                break;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mind, null);
        unbinder = ButterKnife.bind(this, view);
        initStatusBar();
        EventBus.getDefault().register(this);
        initView();
        initData();
        if (userSP.getInt(UserSP.LOGIN_SUCCESS) == 1) {
            llLoginOut.setVisibility(View.VISIBLE);
        } else {
            llLoginOut.setVisibility(View.GONE);
        }

        return view;
    }

    private void initStatusBar() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) llUV.getLayoutParams();
        lp.setMargins(0, StatusBarHightUtil.getStatusBarHeight(), 0, 0);
        llUV.setLayoutParams(lp);
    }


    private void initView() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        srl_refresh_account.setRefreshing(true);
        srl_refresh_account.setOnRefreshListener(this);
        srl_refresh_account.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initData() {
        if (JudgeNewWorkUtil.isNetworkAvalible(getContext())) {
            lineRequestGridData();
            getLeverInfo();
            getMyMessageData();
        } else {
            ToastUtil.showShort(getContext(), "网络异常，请检查网络设置！");
            srl_refresh_account.setRefreshing(false);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && userSP.getInt(UserSP.LOGIN_SUCCESS) != -1) {
            initData();
        }
    }

    static {
        SKIP.put("签到", null);
        SKIP.put("会员等级", MemberRatingActivity.class);
        SKIP.put("用户余额详情", BalanceDetailsActivity.class);
        SKIP.put("存款", null);
        SKIP.put("取款", DrawActivity.class);
        SKIP.put("额度转换", null);
        SKIP.put("我的优惠", MyDiscountActivity.class);
        SKIP.put("记录查询", RecordQueryActivity.class);
        SKIP.put("转换查询", LineQueryActivity.class);
        SKIP.put("下注查询", PlaceOrderActivity.class);
        SKIP.put("个人资料", PersonalInfActivity.class);
        SKIP.put("我的消息", MyMessageActivity.class);
        SKIP.put("投诉建议", ComplaintAdviceActivity.class);
        SKIP.put("联系我们", ContactUsActivity.class);
        SKIP.put("退出账号", LoginActivity.class);
        SKIP.put("关于我们", OthersActivity.class);
        SKIP.put("清除缓存", null);
    }


    @OnClick({R.id.ll_pb, R.id.ll_contact_us, R.id.ll_zhuanhuan_info, R.id.iv_sign_in, R.id.ll_zhudan_info, R.id.tv_user_detail, R.id.iv_vip1, R.id.ll_deposit, R.id.ll_with_draw, R.id.ll_line_conversion, R.id.ll_discounts, R.id.ll_records_query,
            R.id.ll_personal_info, R.id.ll_mind_message,
            R.id.ll_complain_advice, R.id.ll_login_out, R.id.ll_clean_cache, R.id.ll_Others})
    public void onViewClicked(View view) {
        //判断用户是否登录过期
        if (userSP.getInt(UserSP.LOGIN_SUCCESS) != 1) {
            instance.skipLoginActivity(getActivity(), LoginActivity.class, "MindFragment");
            return;
        }
        Class cls = SKIP.get(view.getTag().toString());
        if (view.getTag().toString().equals("清除缓存")) {
            showDialog();
        } else if (cls != null && view.getTag().toString().equals("退出账号")) {
            loginOut(cls);
        } else if (cls != null) {
            startActivity(new Intent(getActivity(), cls));
        } else if (view.getTag().toString().equals("额度转换")) {
            ((MainActivity) getActivity()).showFragment(3);
        } else if (view.getTag().toString().equals("存款")) {
            ((MainActivity) getActivity()).showFragment(2);
        }

    }

    private void getLeverInfo() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLever(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                srl_refresh_account.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                srl_refresh_account.setRefreshing(false);
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    srl_refresh_account.setRefreshing(false);
                    if (status == 200) {
                        LevelBean levelBean = new Gson().fromJson(s, LevelBean.class);
                        userName.setText(levelBean.getData().getUsername());
                        amount = levelBean.getData().getAmount();
                        nextlevel = levelBean.getData().getNextlevel();
                        levelInfo = levelBean.getData().getLevel();
                        setVipView();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
//                            instance.skipLoginActivity(getActivity(), LoginActivity.class);
                        } else {
                            ToastUtil.showShort(getActivity(), errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setVipView() {
        DecimalFormat df = new DecimalFormat("#.00");
        String ckjeNeed;
        String ztzjeNeed;
        if (nextlevel.getCkje_need() == 0) {
            ckjeNeed = "00";
        } else {
            ckjeNeed = String.valueOf(df.format(nextlevel.getCkje_need()));
        }

        if (nextlevel.getZtzje_need() == 0) {
            ztzjeNeed = "00";
        } else {
            ztzjeNeed = String.valueOf(df.format(nextlevel.getZtzje_need()));
        }
        SpannableString span = new SpannableString("还需" + ckjeNeed + "存款和"
                + ztzjeNeed + "投注额升级为" + nextlevel.getLevelname());
        int lengthCk = ckjeNeed.length();
        int lengthZt = ztzjeNeed.length();
        int lengthLv = nextlevel.getLevelname().length();
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#fd7328")), 2, 2 + lengthCk, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#fd7328")), 5 + lengthCk, 5 + lengthCk + lengthZt, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(Color.parseColor("#fd7328")), 11 + lengthCk + lengthZt, 11 + lengthCk + lengthZt + lengthLv, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvVip.setText(span);


        double ck = Double.valueOf(amount.getCkje());
        double ckNeed = ck / (ck + nextlevel.getCkje_need());
        pb1 = (int) (ckNeed * 100);
        ckje = Double.valueOf(amount.getCkje());


        start(0);

        double tz = Double.valueOf(amount.getZtzje());
        double tzNeed = tz / (tz + nextlevel.getZtzje_need());
        pb2 = (int) (tzNeed * 100);
        ztzje = Double.valueOf(amount.getZtzje());

        start(1);

        //vip图片设置
        Glide.with(getContext()).load(levelInfo.getLevelimg()).into(ivVip1);
        //保存图片地址
        userSP.setVipImageUrl(UserSP.VIP_IMAGE_URI, levelInfo.getLevelimg());
    }


    private void loginOut(final Class cls) {
        emptyLayout.setLoadingMessage("正在退出账号...");
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLoginOut(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (emptyLayout != null) {
                    emptyLayout.hide();
                }
                ToastUtil.showShort(getContext(), "退出账号失败！");
                LogUtil.e("cww", e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if (emptyLayout != null) {
                    emptyLayout.hide();
                }
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        ToastUtil.showShort(getContext(), "退出账号成功！");
                        llLoginOut.setVisibility(View.GONE);
                        EventBus.getDefault().post(new SuccessBean("success", "loginOut"));
                        UserSP.getSPInstance().setSuccess(UserSP.LOGIN_SUCCESS, -1);
                        UserSP.getSPInstance().setLoginOut(UserSP.Login_OUT, true);
                        UserSP.getSPInstance().setVipImageUrl(UserSP.VIP_IMAGE_URI, "res://com.wishland.www.wanhaohui2/" + R.mipmap.vip_0);
                        startActivity(new Intent(getActivity(), cls));
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "MindFragment");
                        } else {
                            ToastUtil.showShort(getActivity(), errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //ToastUtil.showLong(getContext(), "请求数据出错！");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //ToastUtil.showLong(getContext(), "请求数据出错！");
                }
            }
        });
    }

    private void initProgressData() {
        //进度条数据重置
        progress = progress2 = pb1 = pb2 = 0;
        ckje = 0;
        ztzje = 0;
        isrun = isrun2 = true;
    }

//    private void lineRequestGridData() {
//        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
//        if (map.size() != 0) {
//            map.clear();
//        }
//        map.put("type", "wallet");
//
//        instance.apiItem("wallet", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
//            @Override
//            public void onCompleted() {
//                srl_refresh_account.setRefreshing(false);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                srl_refresh_account.setRefreshing(false);
//                LogUtil.e("cww", e.getMessage());
//                ToastUtil.showLong(getActivity(), "获取账户余额异常！");
//            }
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                try {
//                    String lineMoneyData = responseBody.string();
//                    LogUtil.i("linearResponse", lineMoneyData);
//                    JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
//                    int status = jsonObject.optInt("status");
//                    instance.setToken_SP(jsonObject.optString("token"));
//                    srl_refresh_account.setRefreshing(false);
//                    if (status == 200) {
//                        WalletBean lineMoneyDataBean = new Gson().fromJson(lineMoneyData, WalletBean.class);
//                        WalletBean.WalletData data = lineMoneyDataBean.getData();
//                        tv_account_money.setText(data.getBalance());
//                    } else {
//                        String errorMsg = jsonObject.optString("errorMsg");
//                        if (errorMsg.equals("用户未登录")) {
//                            instance.skipLoginActivity(getActivity(), LoginActivity.class);
//                        } else {
//                            ToastUtil.showShort(getActivity(), errorMsg);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

    private void lineRequestGridData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLineMoneyData(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                if (emptyLayout != null) {
                    emptyLayout.hide();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (emptyLayout != null) {
                    emptyLayout.hide();
                }
                srl_refresh_account.setRefreshing(false);
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(getContext(), "获取账号信息请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String lineMoneyData = responseBody.string();
                    LogUtil.i("linearResponse", lineMoneyData);
                    JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (emptyLayout != null) {
                        emptyLayout.hide();
                    }
                    srl_refresh_account.setRefreshing(false);
                    if (status == 200) {
                        LineMoneyDataBean lineMoneyDataBean = new Gson().fromJson(lineMoneyData, LineMoneyDataBean.class);
                        tv_account_money.setText(lineMoneyDataBean.getData().getTotal() + "");
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "MindFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // ToastUtil.showLong(getContext(), "请求数据出错！");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //ToastUtil.showLong(getContext(), "请求数据出错！");
                }

            }
        });
    }

    private void getMyMessageData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        int queryId = 0;
        if (map.size() != 0) {
            map.clear();
        }
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");
        instance.apiMessage(queryCnt + "", queryId + "", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                if (srl_refresh_account != null) {
                    srl_refresh_account.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(getActivity(), "获取消息列表请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String lineMoneyData = responseBody.string();
                    LogUtil.i("linearResponse", lineMoneyData);
                    JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("dataList");
                        if (jsonArray == null) {
//                            ToastUtil.showShort(getActivity(), "暂时没有消息");
                        } else {
                            MessageBean messageBean = new Gson().fromJson(lineMoneyData, MessageBean.class);
                            if (view_red_bg != null) {
                                if (messageBean.getData().getUnReadMsg() == 0) {
                                    view_red_bg.setVisibility(View.GONE);
                                } else {
                                    view_red_bg.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } else if (status == 301 || status == 332) {
//                        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                        startActivity(intent);
                    } else {
                        ToastUtil.showShort(getActivity(), "获取消息列表请求异常！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuccessBean ls) {
        if ("success".equals(ls.getStatus()) && "login".equals(ls.getType())) {
            llLoginOut.setVisibility(View.VISIBLE);
            initProgressData();
            initData();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        if (handler != null) {
            handler.removeMessages(0);
            handler.removeMessages(1);
        }
    }


    @Override
    public void onRefresh() {
        initData();
    }

    private void showDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("提示：");
        builder.setMessage("确定清除缓存吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Glide.get(getContext()).clearMemory();
                ImagePipeline imagePipeline = Fresco.getImagePipeline();
                imagePipeline.clearMemoryCaches();
                imagePipeline.clearDiskCaches();
                imagePipeline.clearCaches();
                ToastUtil.showShort(getContext(), "清除成功");
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
}
