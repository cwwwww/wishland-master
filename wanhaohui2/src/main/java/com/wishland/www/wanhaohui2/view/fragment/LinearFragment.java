package com.wishland.www.wanhaohui2.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.bean.WalletBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.BgAlphaUtil;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.StatusBarHightUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.adapter.BalanceDetailAdapter;
import com.wishland.www.wanhaohui2.view.adapter.LinearAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.LineScrollView;
import com.wishland.www.wanhaohui2.view.customlayout.MyDividerItemDecoration;
import com.wishland.www.wanhaohui2.view.customlayout.SelectAccountDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Tom on 2017/10/7.
 */

public class LinearFragment extends UmengFragment implements SwipeRefreshLayout.OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_select_account_out)
    LinearLayout ll_select_account_out;
    @BindView(R.id.editText_account_out)
    TextView editText_account_out;
    @BindView(R.id.tv_account_name_out)
    TextView tv_account_name_out;
    @BindView(R.id.tv_account_name_in)
    TextView tv_account_name_in;
    @BindView(R.id.editText_account_in)
    TextView editText_account_in;
    @BindView(R.id.tv_account_money)
    TextView tv_account_money;
    @BindView(R.id.iv_change_account)
    ImageView iv_change_account;
    @BindView(R.id.et_account_counts)
    EditText et_account_counts;
    @BindView(R.id.bt_submit_transfer)
    Button bt_submit_transfer;
    @BindView(R.id.srl_refresh_account)
    SwipeRefreshLayout srl_refresh_account;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.rv_account_list)
    RecyclerView rv_account_list;
    @BindView(R.id.ll_money_yue)
    LinearLayout ll_money_yue;
    @BindView(R.id.lsc_parent)
    LineScrollView lsc_parent;
    @BindView(R.id.iv_line_up)
    ImageView iv_line_up;
    @BindView(R.id.cb_1)
    CheckBox cb_1;
    @BindView(R.id.cb_2)
    CheckBox cb_2;
    @BindView(R.id.cb_3)
    CheckBox cb_3;
    @BindView(R.id.cb_4)
    CheckBox cb_4;
    @BindView(R.id.cb_5)
    CheckBox cb_5;
//    @BindView(R.id.bottom_sheet)
//    NestedScrollView bottom_sheet;

    private List<LineMoneyDataBean.LineMoneyData.WalletBean> selcetAccountOutList = new ArrayList<>();
    private Model instance;
    private UserSP userSP;
    //转出账号选择状态
    private int fromconunt = -1;
    //转入账号选择状态
    private int toconunt = -1;
    private int exChangeconunt = -1;
    private int ROLL_OUT = 1;
    private int ROLL_IN = 0;
    private int ACCOUNT_INFO = 4;
    private int REFRESH_ACCOUNT = 2;
    private LineMoneyDataBean.LineMoneyData.WalletBean fromBean = null;
    private LineMoneyDataBean.LineMoneyData.WalletBean exChangeBean = null;
    private LineMoneyDataBean.LineMoneyData.WalletBean toBean = null;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> map2 = new HashMap<>();
    private String fromwallettype;
    private String towallettype;
    private String exChangetype;
    private String fromwalletname;
    private String towalletname;
    private String exChangename;
    private int isShow = 0;
    private BalanceDetailAdapter adapter;
    private PopupWindow popupWindow;
    private WindowManager wm;
    private String LINE_MONEY_ONE = "50";
    private String LINE_MONEY_TWO = "100";
    private String LINE_MONEY_THREE = "500";
    private String LINE_MONEY_FOUR = "1000";
    private String LINE_MONEY_FIVE = "5000";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transfer, container, false);
        unbinder = ButterKnife.bind(this, view);
        initStatusBar();
        EventBus.getDefault().register(this);
        initData();
        initView();
        requestData();
        initLinearMoney();
        return view;
    }

    private void requestData() {
        if (JudgeNewWorkUtil.isNetworkAvalible(getContext())) {
            lineRequestGridData("", REFRESH_ACCOUNT);
        } else {
            ToastUtil.showShort(getContext(), "网络异常，请检查网络设置！");
            srl_refresh_account.setRefreshing(false);
        }
    }

    private void initLinearMoney() {
        et_account_counts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String money = s.toString();
                String money1 = editText_account_out.getText().toString().trim().replace(" 元", "");
                int index;
                if (money1.contains(".")) {
                    index = money1.indexOf(".");
                    if (money.equals(money1) || money.equals(money1.substring(0, index))) {
                        cb_1.setChecked(true);
                    } else {
                        cb_1.setChecked(false);
                    }
                } else if (!"".equals(money)) {
                    if (money.equals(money1)) {
                        cb_1.setChecked(true);
                    } else {
                        cb_1.setChecked(false);

                    }
                }
                if ("100".equals(money)) {
                    cb_2.setChecked(true);
                } else {
                    cb_2.setChecked(false);
                }
                if ("500".equals(money)) {
                    cb_3.setChecked(true);
                } else {
                    cb_3.setChecked(false);
                }
                if ("1000".equals(money)) {
                    cb_4.setChecked(true);
                } else {
                    cb_4.setChecked(false);
                }
                if ("5000".equals(money)) {
                    cb_5.setChecked(true);
                } else {
                    cb_5.setChecked(false);
                }
                if (!et_account_counts.getText().toString().isEmpty()) {
                    bt_submit_transfer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_btn_2));
                } else {
                    bt_submit_transfer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_btn_1));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //处理popupwind背景透明
        ((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void initData() {
        tvTitle.setText("额度转换");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        fromconunt = toconunt = exChangeconunt = -1;
        fromBean = toBean = exChangeBean = null;
    }

    private void initStatusBar() {
        int padding = StatusBarHightUtil.getStatusBarHeight();
        tvTitle.setPadding(padding, padding, padding, padding / 3);
    }

    private void initView() {
        rv_account_list.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rv_account_list.addItemDecoration(new MyDividerItemDecoration(getActivity(), GridLayoutManager.VERTICAL));
        rv_account_list.addItemDecoration(new MyDividerItemDecoration(getActivity(), GridLayoutManager.HORIZONTAL));
        adapter = new BalanceDetailAdapter(getActivity());
        rv_account_list.setAdapter(adapter);

        wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        srl_refresh_account.setRefreshing(true);
        srl_refresh_account.setOnRefreshListener(this);
        srl_refresh_account.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                lineRequestItemData(selcetAccountOutList.get(position).getWallettype(), position);
            }
        });
    }


    @OnClick({R.id.cb_1, R.id.cb_2, R.id.cb_3, R.id.cb_4, R.id.cb_5, R.id.bt_recycle_all, R.id.ll_select_account_out, R.id.ll_selsect_account_in, R.id.iv_change_account, R.id.bt_submit_transfer, R.id.ll_money_yue})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.cb_1:
                double moneyAll = 0.0;
                String outMoney = editText_account_out.getText().toString().replace(" 元", "").trim();
                if (!"".equals(outMoney)) {
                    moneyAll = Double.valueOf(outMoney);
                    et_account_counts.setText("" + (int) moneyAll);
                    et_account_counts.setSelection(String.valueOf((int) moneyAll).length());
                } else {
                    cb_1.setChecked(false);
                    ToastUtil.showShort(getContext(), "请选择转出账号！");
                }
//                et_account_counts.setText(LINE_MONEY_ONE);
//                et_account_counts.setSelection(LINE_MONEY_ONE.length());
                break;
            case R.id.cb_2:
                et_account_counts.setText(LINE_MONEY_TWO);
                et_account_counts.setSelection(LINE_MONEY_TWO.length());
                break;
            case R.id.cb_3:
                et_account_counts.setText(LINE_MONEY_THREE);
                et_account_counts.setSelection(LINE_MONEY_THREE.length());
                break;
            case R.id.cb_4:
                et_account_counts.setText(LINE_MONEY_FOUR);
                et_account_counts.setSelection(LINE_MONEY_FOUR.length());
                break;
            case R.id.cb_5:
                et_account_counts.setText(LINE_MONEY_FIVE);
                et_account_counts.setSelection(LINE_MONEY_FIVE.length());
                break;
            case R.id.ll_select_account_out:
                emptyLayout.showLoading();
                lineRequestGridData("请选择转出账号", ROLL_OUT);
                break;
            case R.id.ll_selsect_account_in:
                emptyLayout.showLoading();
                lineRequestGridData("请选择转入账号", ROLL_IN);
                break;
            case R.id.iv_change_account:
                exChangeAccount();
                break;
            case R.id.bt_submit_transfer:
                emptyLayout.showLoading();
                PutInMoney();
                break;
            case R.id.bt_recycle_all:
//                emptyLayout.showLoading();
                shwoDialog();
                break;
            case R.id.ll_money_yue:
                if (isShow == 0) {
                    emptyLayout.showLoading();
                    lineRequestGridData("", ACCOUNT_INFO);
                } else {
                    isShow = 0;
                    rv_account_list.setVisibility(View.GONE);
                    iv_line_up.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_flod));
                }

                break;
        }
    }

    private void shwoDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("提示：");
        builder.setMessage("确定将所有平台额度都回收到钱包吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emptyLayout.showLoading();
                recycleAllMoney();
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

    private void recycleAllMoney() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiRecycleAllMoney(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
//                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
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
                    emptyLayout.hide();
                    srl_refresh_account.setRefreshing(false);
                    if (status == 200) {
                        lineRequestGridData("", REFRESH_ACCOUNT);
                        ToastUtil.showShort(getActivity(), "回收成功");
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "LinearFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //   ToastUtil.showLong(getContext(), "请求数据出错！");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //    ToastUtil.showLong(getContext(), "请求数据出错！");
                }

            }
        });
    }


    /**
     * 提交额度转换
     */
    private void PutInMoney() {
        final String amount = et_account_counts.getText().toString() + "";
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (fromconunt == -1) {
            ToastUtil.showShort(getActivity(), "请选择转出账号");
            emptyLayout.hide();
        } else if (toconunt == -1) {
            ToastUtil.showShort(getActivity(), "请选择转入账号");
            emptyLayout.hide();
        } else if (amount.isEmpty()) {
            ToastUtil.showShort(getActivity(), "请输入转换金额");
            emptyLayout.hide();
        } else {
//            fromwallettype = fromBean.getWallettype();
//            towallettype = toBean.getWallettype();
            if (map2.size() != 0) {
                map2.clear();
            }
            map2.put("fromWalletType", fromwallettype);
            map2.put("toWalletType", towallettype);
            map2.put("amount", amount);
//            LogUtil.i("linearResponse", "fromwallettype：" + fromwallettype);
//            LogUtil.i("linearResponse", "towallettype：" + towallettype);
//            LogUtil.i("linearResponse", "amount：" + amount);

            instance.apiLinePutIn(fromwallettype, towallettype, amount, token, NetUtil.getParamsPro(map2).get("signature"), new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
//                    emptyLayout.hide();
                }

                @Override
                public void onError(Throwable e) {
                    emptyLayout.hide();
                    LogUtil.e("cww", e.getMessage());
                    ToastUtil.showShort(getContext(), "提交额度转换请求异常！");
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String lineMoneyData = responseBody.string();
                        LogUtil.i("linearResponse", lineMoneyData);
                        JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                        int status = jsonObject.optInt("status");
                        instance.setToken_SP(jsonObject.optString("token"));
                        emptyLayout.hide();
                        if (status == 200) {
                            cleanView("转换成功:", fromwalletname + "  -->  " + towalletname, "转账金额：" + amount);
                            lineRequestGridData("", REFRESH_ACCOUNT);
                        } else {
                            String errorMsg = jsonObject.optString("errorMsg");
                            if (errorMsg.equals("用户未登录")) {
                                instance.skipLoginActivity(getActivity(), LoginActivity.class, "LinearFragment");
                            } else {
                                ToastUtil.showShort(getContext(), errorMsg);
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
    }

    private void cleanView(String type, String fromto, String money) {
        View view = View.inflate(getActivity(), R.layout.line_success, null);
        TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
        TextView tv_from_to = (TextView) view.findViewById(R.id.tv_from_to);
        TextView tv_line_money = (TextView) view.findViewById(R.id.tv_line_money);
        RelativeLayout rl_close = (RelativeLayout) view.findViewById(R.id.rl_close);

        tv_type.setText(type);
        tv_from_to.setText(fromto);
        tv_line_money.setText(money);

        tv_account_name_in.setText("请选择转入账号");
        tv_account_name_out.setText("请选择转出账号");
        editText_account_out.setText("");
        editText_account_in.setText("");
        et_account_counts.setText("");
        ToastUtil.showShort(getContext(), "转账成功");
//        MyToastUtil.getToastEmail().toastShow(getActivity(), null, "");

//        final AlertDialog.Builder alertDialog = instance.getAlertDialog(getContext());
//        alertDialog.setView(view)
//                .setCancelable(false);
//        final AlertDialog show = alertDialog.show();
//
//        rl_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                show.dismiss();
//            }
//        });
        fromconunt = toconunt = -1;
        fromBean = toBean = exChangeBean = null;
    }

    /**
     * 请求转换数据
     */
    private void lineRequestGridData(final String title, final int type) {
        if (!JudgeNewWorkUtil.isNetworkAvalible(getContext())) {
            ToastUtil.showShort(getContext(), "网络异常，请检查网络设置！");
            srl_refresh_account.setRefreshing(false);
            return;
        }
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLineMoneyData(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
//                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
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
                    emptyLayout.hide();
                    srl_refresh_account.setRefreshing(false);
                    if (status == 200) {
                        selcetAccountOutList.clear();
                        LineMoneyDataBean lineMoneyDataBean = new Gson().fromJson(lineMoneyData, LineMoneyDataBean.class);
                        List<LineMoneyDataBean.LineMoneyData.WalletBean> walletList = lineMoneyDataBean.getData().getWallet();
                        selcetAccountOutList.addAll(walletList);
                        adapter.setData(selcetAccountOutList);
                        if (type == REFRESH_ACCOUNT) {
                            refreshAccount(lineMoneyDataBean.getData().getTotal());
                            fromconunt = 1;
                            tv_account_name_out.setText(walletList.get(0).getName());
                            editText_account_out.setText(walletList.get(0).getAmout() + " 元");
                            fromBean = walletList.get(0);
                            fromwallettype = walletList.get(0).getWallettype();
                            fromwalletname = walletList.get(0).getName();
                        } else if (type == ACCOUNT_INFO) {
                            showAccountInfo();
                        } else {
                            setDataPopup(selcetAccountOutList, type);
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "LinearFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
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

    private void showAccountInfo() {
        if (isShow == 0) {
            isShow = 1;
//            adapter.setData(selcetAccountOutList);
            rv_account_list.setVisibility(View.VISIBLE);
            iv_line_up.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_less));
        } else {
            isShow = 0;
            rv_account_list.setVisibility(View.GONE);
        }
    }

    private void refreshAccount(double total) {
        tv_account_money.setText(total + "");
        srl_refresh_account.setRefreshing(false);
    }

    private void exChangeAccount() {
        String nameOut = tv_account_name_out.getText().toString();
        String nameIn = tv_account_name_in.getText().toString();
        String countOut = editText_account_out.getText().toString();
        String countIn = editText_account_in.getText().toString();
        if (!"请选择转出账号".equals(nameOut)) {
            tv_account_name_in.setText(nameOut);
        } else {
            tv_account_name_in.setText("请选择转入账号");
        }
        if (!"请选择转入账号".equals(nameIn)) {
            tv_account_name_out.setText(nameIn);
        } else {
            tv_account_name_out.setText("请选择转出账号");
        }
        editText_account_out.setText(countIn);
        editText_account_in.setText(countOut);

        exChangetype = fromwallettype;
        fromwallettype = towallettype;
        towallettype = exChangetype;

        exChangename = fromwalletname;
        fromwalletname = towalletname;
        towalletname = exChangename;

        exChangeconunt = fromconunt;
        fromconunt = toconunt;
        toconunt = exChangeconunt;
    }

    //额度转换item刷新
    private void lineRequestItemData(String type, final int position) {
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("type", type);
        instance.apiItem(type, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                srl_refresh_account.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                srl_refresh_account.setRefreshing(false);
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(getActivity(), "获取账户余额异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String lineMoneyData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    srl_refresh_account.setRefreshing(false);
                    if (status == 200) {
                        WalletBean lineMoneyDataBean = new Gson().fromJson(lineMoneyData, WalletBean.class);
                        WalletBean.WalletData data = lineMoneyDataBean.getData();
                        for (int i = 0; i < selcetAccountOutList.size(); i++) {
                            if (i == position) {
                                selcetAccountOutList.get(i).setAmout(data.getBalance());
                                adapter.setData(selcetAccountOutList);
                            }
                        }

//                        tv_account_money.setText(data.getBalance());
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "LinearFragment");
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

    public void setDataPopup(final List<LineMoneyDataBean.LineMoneyData.WalletBean> list, final int type) {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.funds_popupwindow, null);
        RecyclerView lsvMore = (RecyclerView) popupView.findViewById(R.id.funds_popupview);
        RelativeLayout rl_line_down = (RelativeLayout) popupView.findViewById(R.id.rl_line_down);
        LinearAdapter linearAdapter = new LinearAdapter(getActivity());
        lsvMore.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//        lsvMore.addItemDecoration(new MyDividerItemDecoration(getActivity(), GridLayoutManager.VERTICAL));
//        lsvMore.addItemDecoration(new MyDividerItemDecoration(getActivity(), GridLayoutManager.HORIZONTAL));
        lsvMore.setAdapter(linearAdapter);
        linearAdapter.setOnItemClickListener(new LinearAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LineMoneyDataBean.LineMoneyData.WalletBean bean = list.get(position);
                if (type == ROLL_OUT) {
                    fromconunt = 1;
                    tv_account_name_out.setText(bean.getName());
                    editText_account_out.setText(bean.getAmout() + " 元");
                    fromBean = bean;
                    fromwallettype = bean.getWallettype();
                    fromwalletname = bean.getName();
                    if (cb_1.isChecked()) {
                        double money = Double.valueOf(bean.getAmout().toString().trim());
                        et_account_counts.setText((int) money + "");
                        et_account_counts.setSelection(String.valueOf((int) money).length());
                    }
                } else {
                    toconunt = 1;
                    tv_account_name_in.setText(bean.getName());
                    editText_account_in.setText(bean.getAmout() + " 元");
                    toBean = bean;
                    towallettype = bean.getWallettype();
                    towalletname = bean.getName();
                }
                popupWindow.dismiss();
            }
        });
        rl_line_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, wm.getDefaultDisplay().getHeight() / 5 * 3);
//        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.DialogBottom);
        popupWindow.update();
        popupWindow.showAtLocation(lsc_parent, Gravity.BOTTOM, 0, 0);
        linearAdapter.setData(list);
        BgAlphaUtil.setBackgroundAlpha(0.5f, getContext());
//        setBackgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                BgAlphaUtil.setBackgroundAlpha(1.0f, getContext());
//                setBackgroundAlpha(1.0f);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuccessBean ls) {
        if ("success".equals(ls.getStatus()) && "login".equals(ls.getType())) {
            lineRequestGridData("", REFRESH_ACCOUNT);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && userSP.getInt(UserSP.LOGIN_SUCCESS) != -1) {
            lineRequestGridData("", REFRESH_ACCOUNT);
        }
    }

    @Override
    public void onRefresh() {
        lineRequestGridData("", REFRESH_ACCOUNT);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) getContext()).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) getContext()).getWindow().setAttributes(lp);
    }


    private void showOutDialog(String title, final int type) {
        new SelectAccountDialog(getActivity(), selcetAccountOutList, new SelectAccountDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    dialog.dismiss();
                }
            }
        }, new SelectAccountDialog.OnItemClickListener() {

            @Override
            public void onItemClickListener(Dialog dialog, LineMoneyDataBean.LineMoneyData.WalletBean bean) {
                if (type == ROLL_OUT) {
                    fromconunt = 1;
                    tv_account_name_out.setText(bean.getName());
                    editText_account_out.setText(bean.getAmout() + " 元");
                    fromBean = bean;
                    fromwallettype = fromBean.getWallettype();
                    fromwalletname = fromBean.getName();
                } else {
                    toconunt = 1;
                    tv_account_name_in.setText(bean.getName());
                    editText_account_in.setText(bean.getAmout() + " 元");
                    toBean = bean;
                    towallettype = toBean.getWallettype();
                    towalletname = toBean.getName();
                }
                dialog.dismiss();
            }
        }).setTitle(title).show();
    }
}
