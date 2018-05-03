package com.wishland.www.wanhaohui2.view.fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.OfflinePayBean;
import com.wishland.www.wanhaohui2.bean.PayWayBean;
import com.wishland.www.wanhaohui2.bean.PayWayTypeBean;
import com.wishland.www.wanhaohui2.bean.SuccessBean;
import com.wishland.www.wanhaohui2.model.MobclickEvent;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.MyToastUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.StatusBarHightUtil;
import com.wishland.www.wanhaohui2.utils.TimeUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.adapter.BankAccountForAliAdapter;
import com.wishland.www.wanhaohui2.view.adapter.BankAccountForHKAdapter;
import com.wishland.www.wanhaohui2.view.adapter.PayWayTypeAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.MyPicker;
import com.wishland.www.wanhaohui2.view.customlayout.MyPickerForDate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.DateTimePicker;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/7.
 */

public class SavingFragment extends UmengFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.recycler_view_pay_type)
    RecyclerView recyclerViewPayType;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.ll_pay_way_type1)
    LinearLayout llPayWayType1;
    @BindView(R.id.ll_pay_way_type2)
    LinearLayout llPayWayType2;
    @BindView(R.id.tv_attention1)
    TextView tvAttention1;
    @BindView(R.id.tv_attention2)
    TextView tvAttention2;
    @BindView(R.id.tv_attention3)
    TextView tvAttention3;
    @BindView(R.id.recycler_view_card_type)
    RecyclerView recyclerViewCardType;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_bank_number)
    TextView tvBankNumber;
    @BindView(R.id.tv_remit_type)
    TextView tvRemitType;
    @BindView(R.id.ll_other_pay_way)
    LinearLayout llOtherPayWay;
    @BindView(R.id.ll_remit_people)
    LinearLayout llRemitPeople;
    @BindView(R.id.tv_remit_background)
    TextView tvRemitBackground;
    @BindView(R.id.ll_pay_name)
    LinearLayout llPayName;
    @BindView(R.id.tv_pay_name)
    TextView tvPayName;
    @BindView(R.id.ll_pay_place)
    LinearLayout llPayPlace;
    @BindView(R.id.tv_pay_place)
    TextView tvPayPlace;
    @BindView(R.id.recycler_view_ali_account)
    RecyclerView recyclerViewAliAccount;
    @BindView(R.id.et_remit_money)
    EditText etRemitMoney;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_remit_date)
    TextView tvRemitDate;
    @BindView(R.id.et_remit_place)
    EditText etRemitPlace;
    @BindView(R.id.et_remit_name)
    EditText etRemitName;
    @BindView(R.id.et_order_number)
    EditText etOrderNumber;
    @BindView(R.id.ll_ali_name)
    LinearLayout llAliName;
    @BindView(R.id.et_ali_name)
    EditText etAliName;
    @BindView(R.id.tv_ali_name)
    TextView tvAliName;
    @BindView(R.id.et_other_pay)
    EditText etOtherPay;
    @BindView(R.id.ll_pay_bank)
    LinearLayout llPayBank;
    @BindView(R.id.tv_pay_bank)
    TextView tvPayBank;
    @BindView(R.id.bt1)
    CheckBox bt1;
    @BindView(R.id.bt2)
    CheckBox bt2;
    @BindView(R.id.bt3)
    CheckBox bt3;
    @BindView(R.id.bt4)
    CheckBox bt4;
    @BindView(R.id.bt5)
    CheckBox bt5;
    @BindView(R.id.bt6)
    CheckBox bt6;
    @BindView(R.id.tv_remit_hour)
    TextView tvRemitHour;
    @BindView(R.id.tv_remit_minute)
    TextView tvRemitMinute;
    @BindView(R.id.tip_faster_go_to_bank_card)
    TextView tip_faster_go_to_bank_card;
    @BindView(R.id.tv_card_address)
    TextView tvCardAddress;

    Unbinder unbinder;
    private Model instance;
    private UserSP userSP;
    private PayWayTypeBean payWayTypeBean;
    private MyPicker<String> payWayPicker;
    private MyPicker<String> payBankNamePicker;
    private PayWayTypeAdapter payWayTypeAdapter;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    private LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    private LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    private List<PayWayTypeBean.DataBean.PayListBean> payListBeen;
    private List<Integer> viewType1;
    private List<Integer> viewType2;
    private List<Integer> viewType3;
    private List<String> payWayNameList;
    private List<String> payBankNameList;
    private PayWayBean payWayBean1;
    private OfflinePayBean offlinePayBean;
    private BankAccountForHKAdapter bankAccountForHKAdapter;
    private BankAccountForAliAdapter bankAccountForAliAdapter;
    private MyPicker<String> pickerRemitType;
    private MyPickerForDate pickerDate;
    private DateTimePicker pickerDateTime;
    private String payNameType;
    private String intoBank;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        requestData();
        initDatePicker();
//        initTimePicker();
    }

    private void requestData() {
        if (JudgeNewWorkUtil.isNetworkAvalible(getContext())) {
            initPayWayData();
        } else {
            ToastUtil.showShort(getContext(), "网络异常，请检查网络设置！");
        }
    }

    private void init() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        viewType1 = new ArrayList<>();
        viewType2 = new ArrayList<>();
        viewType3 = new ArrayList<>();
        payWayTypeAdapter = new PayWayTypeAdapter(getActivity(), viewType1);
        payWayTypeAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                initAllViewData();
                List<Integer> viewType = new ArrayList<Integer>();
                for (int i = 0; i < payListBeen.size(); i++) {
                    if (i == position) {
                        viewType.add(1);
                    } else {
                        viewType.add(0);
                    }
                }
                payWayTypeAdapter.setViewType(viewType);
                getPayWayData(position);
            }
        });
        bankAccountForHKAdapter = new BankAccountForHKAdapter(getActivity(), viewType2);
        bankAccountForAliAdapter = new BankAccountForAliAdapter(getActivity(), viewType3);

        bankAccountForHKAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                List<Integer> viewType = new ArrayList<Integer>();
                if (offlinePayBean.getData().get(0).getPara().getBank() != null && offlinePayBean.getData().get(0).getPara().getBank().size() != 0) {
                    for (int i = 0; i < offlinePayBean.getData().get(0).getPara().getBank().size(); i++) {
                        if (i == position) {
                            viewType.add(1);
                        } else {
                            viewType.add(0);
                        }
                    }
                    bankAccountForHKAdapter.setViewType(viewType);
                    tvUserName.setText(offlinePayBean.getData().get(0).getPara().getBank().get(position).getName());
                    tvBankNumber.setText(offlinePayBean.getData().get(0).getPara().getBank().get(position).getCardid());
                    tvCardAddress.setText(offlinePayBean.getData().get(0).getPara().getBank().get(position).getAddress());
                }
            }
        });


        bankAccountForAliAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                List<Integer> viewType = new ArrayList<Integer>();
                if (offlinePayBean.getData().get(1).getPara().getAccount() != null && offlinePayBean.getData().get(1).getPara().getAccount().size() != 0) {
                    for (int i = 0; i < offlinePayBean.getData().get(1).getPara().getAccount().size(); i++) {
                        if (i == position) {
                            viewType.add(1);
                        } else {
                            viewType.add(0);
                        }
                    }
                    bankAccountForAliAdapter.setViewType(viewType);
                    tvUserName.setText(offlinePayBean.getData().get(1).getPara().getAccount().get(position).getAlipayName());
                    tvBankNumber.setText(offlinePayBean.getData().get(1).getPara().getAccount().get(position).getAlipayID());
                }
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_deposit_new, container, false);
        unbinder = ButterKnife.bind(this, view);
        initStatusBar();
        EventBus.getDefault().register(this);
        emptyLayout.setLoadingMessage("正在请求支付方式...");
        tvTitle.setText("存款");
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initAllViewData();
                requestData();
            }
        });
        setRefreshStyle();
        recyclerViewPayType.setLayoutManager(linearLayoutManager);
        recyclerViewPayType.setAdapter(payWayTypeAdapter);
        recyclerViewPayType.setNestedScrollingEnabled(false);
        recyclerViewCardType.setLayoutManager(linearLayoutManager1);
        recyclerViewCardType.setAdapter(bankAccountForHKAdapter);
        recyclerViewCardType.setNestedScrollingEnabled(false);
        recyclerViewAliAccount.setLayoutManager(linearLayoutManager2);
        recyclerViewAliAccount.setAdapter(bankAccountForAliAdapter);
        recyclerViewAliAccount.setNestedScrollingEnabled(false);
        initCheckBox();
        return view;
    }


    private void initCheckBox() {
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String money = charSequence.toString();
                if ("11".equals(money)) {
                    bt1.setChecked(true);
                } else {
                    bt1.setChecked(false);
                }

                if ("51".equals(money)) {
                    bt2.setChecked(true);
                } else {
                    bt2.setChecked(false);
                }

                if ("101".equals(money)) {
                    bt3.setChecked(true);
                } else {
                    bt3.setChecked(false);
                }

                if ("201".equals(money)) {
                    bt4.setChecked(true);
                } else {
                    bt4.setChecked(false);
                }

                if ("501".equals(money)) {
                    bt5.setChecked(true);
                } else {
                    bt5.setChecked(false);
                }

                if ("1001".equals(money)) {
                    bt6.setChecked(true);
                } else {
                    bt6.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initStatusBar() {
        int padding = StatusBarHightUtil.getStatusBarHeight();
        tvTitle.setPadding(padding, padding, padding, padding / 3);
    }

    private void initAllViewData() {
        etMoney.setText("");
        etRemitPlace.setText("");
        etRemitMoney.setText("");
        etRemitName.setText("");
        etAliName.setText("");
        etOrderNumber.setText("");
        etOtherPay.setText("");
        llOtherPayWay.setVisibility(View.GONE);
        llRemitPeople.setVisibility(View.GONE);
        tvRemitBackground.setVisibility(View.GONE);
    }

    private void setRefreshStyle() {
        refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright), getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light), getResources().getColor(android.R.color.holo_red_light));
    }

    @OnClick({R.id.personal_text_copy, R.id.bank_number_text_copy, R.id.tip_faster_go_to_bank_card, R.id.address_text_copy})
    public void onCopyClicked(View view) {
        ClipboardManager cm = (ClipboardManager) this.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        switch (view.getId()) {
            case R.id.personal_text_copy:
                // 将文本内容放到系统剪贴板里。
                ToastUtil.showShort(getContext(), "已复制");
                cm.setText(tvUserName.getText());
                break;

            case R.id.bank_number_text_copy:
                // 将文本内容放到系统剪贴板里。
                ToastUtil.showShort(getContext(), "已复制");
                cm.setText(tvBankNumber.getText());
                break;
            case R.id.address_text_copy:
                //将文本内容放到系统剪贴板里
                ToastUtil.showShort(getContext(), "已复制");
                cm.setText(tvCardAddress.getText());
                break;
            case R.id.tip_faster_go_to_bank_card:
                payWayTypeAdapter.setViewType(viewType1);
                payWayTypeAdapter.setData(payListBeen);
                getPayWayData(0);
                break;
        }
    }


    @OnClick({R.id.ll_pay_way, R.id.iv_pay_way, R.id.bt1,
            R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5, R.id.bt6,
            R.id.bt_commit, R.id.tv_remit_type, R.id.iv_remit_type,
            R.id.bt_pay_type_commit2, R.id.ll_remit_date
            , R.id.ll_pay_bank_name, R.id.iv_pay_bank})
    public void onViewClicked(View view) {
        if (userSP.getInt(UserSP.LOGIN_SUCCESS) != 1) {
            instance.skipLoginActivity(getActivity(), LoginActivity.class, "SavingFragment");
            return;
        }
        switch (view.getId()) {
            case R.id.iv_pay_way:
            case R.id.ll_pay_way: {
                if (payWayPicker != null) {
                    payWayPicker.show();
                }
            }
            break;
            case R.id.ll_pay_bank_name:
            case R.id.iv_pay_bank: {
                if (payBankNamePicker != null) {
                    payBankNamePicker.show();
                }
            }
            break;
            case R.id.bt1: {
                etMoney.setText("11");
                etMoney.setSelection("11".length());
            }
            break;
            case R.id.bt2: {
                etMoney.setText("51");
                etMoney.setSelection("51".length());
            }
            break;
            case R.id.bt3: {
                etMoney.setText("101");
                etMoney.setSelection("101".length());
            }
            break;
            case R.id.bt4: {
                etMoney.setText("201");
                etMoney.setSelection("201".length());
            }
            break;
            case R.id.bt5: {
                etMoney.setText("501");
                etMoney.setSelection("501".length());
            }
            break;
            case R.id.bt6: {
                etMoney.setText("1001");
                etMoney.setSelection("1001".length());
            }
            break;
            case R.id.bt_commit: {
                //在线支付
                onLinePay();
            }
            break;
            //支付方式
            case R.id.iv_remit_type:
            case R.id.tv_remit_type: {
                if (pickerRemitType != null) {
                    pickerRemitType.show();
                }
            }
            break;
            //入款提交
            case R.id.bt_pay_type_commit2: {
                //支付宝入款、公司入款
                if ("支付宝入款".equals(tvRemitType.getText().toString())) {
                    aliPay();
                } else {
                    hKPay();
                }
            }
            break;
            //汇款日期
            case R.id.ll_remit_date: {
//                pickerDate.show();
                pickerDateTime.show();
            }
            break;
        }
    }

    private void onLinePay() {
        if (TextUtils.isEmpty(etMoney.getText().toString())) {
            ToastUtil.showShort(getContext(), "请输入金额！");
            return;
        } else if (Double.valueOf(etMoney.getText().toString()) < 10) {
            ToastUtil.showShort(getContext(), "最少10元");
            return;
        }
        double minMoney = 0;
        double maxMoney = 0;
        String risk = "0";
        String payUrl = "";
        String payCode = "";
        String payName = tvPayWay.getText().toString();
        String payBank = tvPayBank.getText().toString();
        if (payWayNameList != null) {
            for (int i = 0; i < payWayNameList.size(); i++) {
                if (payWayNameList.get(i).equals(payName)) {
                    if (payWayBean1 != null) {
                        risk = payWayBean1.getData().get(i).getRisk();
                        payUrl = payWayBean1.getData().get(i).getUrl();
                        if (!"".equals(payWayBean1.getData().get(i).getMin())) {
                            minMoney = Double.valueOf(payWayBean1.getData().get(i).getMin());
                        }
                        if (!"".equals(payWayBean1.getData().get(i).getMax())) {
                            maxMoney = Double.valueOf(payWayBean1.getData().get(i).getMax());
                        }
                        if ("网银在线".equals(payNameType)) {
                            for (int j = 0; j < payWayBean1.getData().get(i).getItem().getCode().size(); j++) {
                                if (payWayBean1.getData().get(i).getItem().getCode().get(j).getName().equals(payBank)) {
                                    payCode = payWayBean1.getData().get(i).getItem().getCode().get(j).getCode();
                                    break;
                                }
                            }
                        } else {
                            payCode = payWayBean1.getData().get(i).getItem().getCode().get(0).getCode();
                        }
                        break;
                    }
                }
            }
        }

        //判断存款是否满足情况
        if ("1".equals(risk) && Double.valueOf(etMoney.getText().toString()) % 10 == 0) {
            ToastUtil.showShort(getContext(), "输入的金额不能为10的倍数，请重新输入！");
            return;
        }

        //判断存款的最大值和最小值
        if (maxMoney != minMoney && maxMoney != 0) {
            if (Double.valueOf(etMoney.getText().toString().trim()) > maxMoney || Double.valueOf(etMoney.getText().toString().trim()) < minMoney) {
                ToastUtil.showShort(getContext(), "存款金额，最少" + minMoney + "元，最多" + maxMoney + "元");
                return;
            }
        }
        UmengAnalysis(payName, etMoney.getText().toString().trim(), payBank);

        //Log.e("cww", payUrl.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)).replace("[money]", etMoney.getText().toString().trim()).replace("[code]", payCode));
        WebUtil.toWebActivity(getContext(), payUrl.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)).replace("[money]", etMoney.getText().toString().trim()).replace("[code]", payCode), "");

    }

    public enum DEPOSIT_TYPE {
        WECHAT("微信"),
        ALIPAY("支付宝"),
        TENCENT("财付通"),
        ONLINE("网银"),
        OFFLINE("线下"),
        NONE("其他支付");

        private String title;

        DEPOSIT_TYPE(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public static DEPOSIT_TYPE findTypeContainsTitle(String title) {

            for (DEPOSIT_TYPE item : values()) {
                if (title.contains(item.title)) {
                    return item;
                }
            }
            return NONE;
        }
    }

    /***
     * 友盟统计
     * @param payName 支付方式
     * @param payAmount 金额
     * @param bankName 银行名
     */
    private void UmengAnalysis(String payName, String payAmount, String bankName) {

        MobclickAgent.onEvent(this.getActivity(), MobclickEvent.DEPOSIT_TYPE, payNameType);

        HashMap<String, String> payInfo = new HashMap<String, String>();

        DEPOSIT_TYPE type = DEPOSIT_TYPE.findTypeContainsTitle(payNameType);
        switch (type) {
            case WECHAT:
                payInfo.put("payWay", payName);
                payInfo.put("money", payAmount);
                MobclickAgent.onEvent(this.getActivity(), MobclickEvent.DEPOSIT_TYPE_WECHAT, payInfo);
                break;
            case ALIPAY:
                payInfo.put("payWay", payName);
                payInfo.put("money", payAmount);
                MobclickAgent.onEvent(this.getActivity(), MobclickEvent.DEPOSIT_TYPE_ALIPAY, payInfo);
                break;
            case TENCENT:
                payInfo.put("payWay", payName);
                payInfo.put("money", payAmount);
                MobclickAgent.onEvent(this.getActivity(), MobclickEvent.DEPOSIT_TYPE_TENCENT, payInfo);
                break;
            case ONLINE:
                payInfo.put("payWay", payName);
                payInfo.put("money", payAmount);
                payInfo.put("bankName", bankName);
                MobclickAgent.onEvent(this.getActivity(), MobclickEvent.DEPOSIT_TYPE_ONLINE, payInfo);
                break;
            case OFFLINE:
                payInfo.put("payWay", payName);
                payInfo.put("money", payAmount);
                payInfo.put("bankName", bankName);
                MobclickAgent.onEvent(this.getActivity(), MobclickEvent.DEPOSIT_TYPE_OFFLINE, payInfo);
                break;
            case NONE:
                break;
        }
    }

    private void hKPay() {
        final String vAmount = etRemitMoney.getText().toString();
        String remitPlace = etRemitPlace.getText().toString().trim();
        if (TextUtils.isEmpty(remitPlace)) {
            ToastUtil.showShort(getContext(), "请输入汇款地点");
            return;
        }
        if (TextUtils.isEmpty(vAmount)) {
            ToastUtil.showShort(getContext(), "请输入金额！");
            return;
        } else if (Double.valueOf(vAmount) < 10) {
            ToastUtil.showShort(getContext(), "最少10元");
            return;
        }
        emptyLayout.setLoadingMessage("正在提交支付信息...");
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String intoBankName = tvUserName.getText().toString().trim();
        for (int i = 0; i < offlinePayBean.getData().get(0).getPara().getBank().size(); i++) {
            if (offlinePayBean.getData().get(0).getPara().getBank().get(i).getBank().contains(intoBankName)) {
                intoBank = offlinePayBean.getData().get(0).getPara().getBank().get(i).getBank();
                break;
            }
        }

        final String inType = tvRemitType.getText().toString();
        String cn_date = tvRemitDate.getText().toString();
        String vSite = etRemitPlace.getText().toString();
        String s_h = tvRemitHour.getText().toString();
        String s_i = tvRemitMinute.getText().toString().replace(":", "");
        String vName = "";
        String otherPayWay = "";
        Map<String, String> map = new HashMap();
        map.put("type", "hk");
        map.put("v_amount", vAmount);
        map.put("IntoBank", intoBank);
        map.put("InType", inType);
        map.put("cn_date", cn_date);
        map.put("s_h", s_h);
        map.put("s_i", s_i);
        map.put("s_s", "00");
        map.put("v_site", vSite);
        if ("网银转账".equals(tvRemitType.getText().toString())) {
            vName = etRemitName.getText().toString();
            if (TextUtils.isEmpty(vName)) {
                ToastUtil.showShort(getContext(), "请输入汇款人姓名！");
                emptyLayout.hide();
                return;
            }
            map.put("v_Name", vName);
        }
        if ("其它[手动输入]".equals(tvRemitType.getText().toString())) {
            otherPayWay = etOtherPay.getText().toString().trim();
            if (TextUtils.isEmpty(otherPayWay)) {
                ToastUtil.showShort(getContext(), "请输入其它汇款方式名称！");
                emptyLayout.hide();
                return;
            }
            map.put("IntoType", otherPayWay);
        }
        instance.apiPayHK("hk", vAmount, intoBank, cn_date, s_h, s_i, "00", inType, otherPayWay, vName, remitPlace, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                UmengAnalysis(inType, vAmount, intoBank);
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                ToastUtil.showShort(getContext(), "支付信息提交失败！");
                LogUtil.e("cww", e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        MyToastUtil.getToastEmail().toastShow(getActivity(), null, "");
//                        ToastUtil.showShort(getContext(), "支付信息提交成功！");
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "SavingFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void aliPay() {
        String type = offlinePayBean.getData().get(1).getType();
        final String vAmount = etRemitMoney.getText().toString().trim();
        final String intoType = etOrderNumber.getText().toString().trim();
        String vName = etAliName.getText().toString().trim();
        if (TextUtils.isEmpty(vName)) {
            ToastUtil.showShort(getContext(), "请输入支付宝姓名！");
            return;
        }
        if (TextUtils.isEmpty(intoType)) {
            ToastUtil.showShort(getContext(), "请输入订单号！");
            return;
        }
        if (TextUtils.isEmpty(vAmount)) {
            ToastUtil.showShort(getContext(), "请输入金额！");
            return;
        } else if (Double.valueOf(vAmount) < 10) {
            ToastUtil.showShort(getContext(), "最少10元");
            return;
        }
        emptyLayout.showLoading();
        emptyLayout.setLoadingMessage("正在提交支付信息...");
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        final String intoBank = offlinePayBean.getData().get(1).getPara().getAccount().get(0).getAlipayID();
        String cn_date = tvRemitDate.getText().toString();

        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("v_amount", vAmount);
        map.put("IntoBank", intoBank);
        map.put("IntoType", intoType);
        map.put("cn_date", cn_date);
        map.put("v_Name", vName);
        instance.apiPayAliPay(type, vAmount, intoBank, intoType, cn_date, vName, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                UmengAnalysis(intoType, vAmount, intoBank);
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                ToastUtil.showShort(getContext(), "支付信息提交失败！");
                LogUtil.e("cww", e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
//                        ToastUtil.showShort(getContext(), "支付信息提交成功！");
                        MyToastUtil.getToastEmail().toastShow(getActivity(), null, "");
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "SavingFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initPayWayData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiRecharge(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                refreshLayout.setRefreshing(false);
//                ToastUtil.showShort(getContext(), "获取存款路径信息错误！");
                LogUtil.e("cww", e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                refreshLayout.setRefreshing(false);
                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        payWayTypeBean = new Gson().fromJson(s, PayWayTypeBean.class);
                        payListBeen = new ArrayList<>();
                        payListBeen.addAll(payWayTypeBean.getData().getPay_list());
                        viewType1 = new ArrayList<>();
                        for (int i = 0; i < payListBeen.size(); i++) {
                            if (i == 0) {
                                viewType1.add(1);
                            } else {
                                viewType1.add(0);
                            }
                        }
                        payWayTypeAdapter.setViewType(viewType1);
                        payWayTypeAdapter.setData(payListBeen);
                        linearLayoutManager.scrollToPosition(0);
                        getPayWayData(0);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "SavingFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    // ToastUtil.showLong(getContext(), "请求数据出错！");
                }

            }
        });
    }

    private void initPayWayPicker(List<String> mList) {
        if (mList != null && mList.size() != 0) {
            tvPayWay.setText(mList.get(0));
            payWayPicker = new MyPicker<>(getActivity(), mList);
            initPicker(payWayPicker);
            //初始化支付银行
            //网银在线
            if ("网银在线".equals(payNameType)) {
                initPayBankNamePicker(mList.get(0));
            }
        } else {
            payWayPicker.removeAllItem();
            tvPayWay.setText("暂无");
        }
        payWayPicker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                tvPayWay.setText(item);
                //网银在线
                if (payNameType != null && "网银在线".equals(payNameType)) {
                    initPayBankNamePicker(item);
                }
            }
        });
    }

    private void initPayBankNamePicker(String s) {
        PayWayBean.DataBean.ItemBean itemBean = null;
        for (int i = 0; i < payWayBean1.getData().size(); i++) {
            if (!TextUtils.isEmpty(s) && s.equals(payWayBean1.getData().get(i).getPay_name())) {
                itemBean = payWayBean1.getData().get(i).getItem();
                break;
            }
        }
        if (itemBean != null) {
            if (itemBean.getCode() != null && itemBean.getCode().size() != 0) {
                payBankNameList = new ArrayList<>();
                for (int i = 0; i < itemBean.getCode().size(); i++) {
                    payBankNameList.add(itemBean.getCode().get(i).getName());
                }
                payBankNamePicker = new MyPicker<>(getActivity(), payBankNameList);
                initPicker(payBankNamePicker);
                tvPayBank.setText(payBankNameList.get(0));
            } else {
                payBankNamePicker.removeAllItem();
                tvPayBank.setText("暂无");
            }
        }
        if (payBankNamePicker != null) {
            payBankNamePicker.setOnItemPickListener(new OnItemPickListener<String>() {
                @Override
                public void onItemPicked(int i, String s) {
                    tvPayBank.setText(s);
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SuccessBean ls) {
        if ("success".equals(ls.getStatus()) && "login".equals(ls.getType())) {
            initPayWayData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void getPayWayData(int position) {
        if (position == -1) {
            position = 1;
        }
//        Log.e("cww", "position" + position);
        final String payType = payWayTypeBean.getData().getPay_list().get(position).getType();
        payNameType = payWayTypeBean.getData().getPay_list().get(position).getTitle();
        if ("转到银行卡".equals(payNameType)) {
            llPayWayType1.setVisibility(View.GONE);
            llPayWayType2.setVisibility(View.VISIBLE);
            tvRemitDate.setText(TimeUtil.getSystemDateString());
            tvRemitHour.setText(TimeUtil.getSystemHour());
            if (TimeUtil.getSystemMinute().length() == 1) {
                tvRemitMinute.setText(":0" + TimeUtil.getSystemMinute());
            } else {
                tvRemitMinute.setText(":" + TimeUtil.getSystemMinute());
            }
        } else {
            //网银在线
            if ("网银在线".equals(payNameType)) {
                llPayBank.setVisibility(View.VISIBLE);
            } else {
                llPayBank.setVisibility(View.GONE);
            }
            llPayWayType1.setVisibility(View.VISIBLE);
            llPayWayType2.setVisibility(View.GONE);


            tip_faster_go_to_bank_card.setVisibility(View.GONE);
            if (payNameType.contains("微信")) {
                tip_faster_go_to_bank_card.setVisibility(View.VISIBLE);
                tip_faster_go_to_bank_card.setText("使用微信转账到银行卡，到账速度更快。\n点击获取银行卡号 >>");
            } else if (payNameType.contains("支付宝")) {
                tip_faster_go_to_bank_card.setText("使用支付宝转账到银行卡，到账速度更快。\n点击获取银行卡号 >>");
                tip_faster_go_to_bank_card.setVisibility(View.VISIBLE);
            }
        }
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        emptyLayout.showLoading();
        emptyLayout.setLoadingMessage("正在请求支付方式...");
        Map<String, String> map = new HashMap<>();
        map.put("type", payType);
        instance.apiRequestPayPara(token, NetUtil.getParamsPro(map).get("signature"), payType, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
//                ToastUtil.showShort(getContext(), "支付方式获取异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String payWayData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(payWayData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        if ("转到银行卡".equals(payNameType)) {
                            if (jsonObject.optJSONObject("data") != null) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data.optString("status") != null && !"".equals(data.optString("status"))) {
                                    ToastUtil.showShort(getActivity(), "该账户暂不支持线下入款通道！");
                                    return;
                                }
                            }

                            offlinePayBean = new Gson().fromJson(payWayData, OfflinePayBean.class);
                            viewType2 = new ArrayList<>();
                            for (int i = 0; i < offlinePayBean.getData().get(0).getPara().getBank().size(); i++) {
                                if (i == 0) {
                                    viewType2.add(1);
                                } else {
                                    viewType2.add(0);
                                }
                            }
                            bankAccountForHKAdapter.setViewType(viewType2);
                            viewType3 = new ArrayList<>();
                            List<String> remitType = new ArrayList<>();
                            remitType.addAll(offlinePayBean.getData().get(0).getPara().getType());
                            if (offlinePayBean.getData().size() != 1) {
                                for (int i = 0; i < offlinePayBean.getData().get(1).getPara().getAccount().size(); i++) {
                                    if (i == 0) {
                                        viewType3.add(1);
                                    } else {
                                        viewType3.add(0);
                                    }
                                }
                                bankAccountForAliAdapter.setViewType(viewType3);
                                remitType.add(offlinePayBean.getData().get(1).getTitle());
                            }
                            initRemitType(remitType);
                            initViewData();
                        } else {
                            payWayBean1 = new Gson().fromJson(payWayData, PayWayBean.class);
                            payWayNameList = new ArrayList<>();
                            for (int i = 0; i < payWayBean1.getData().size(); i++) {
                                payWayNameList.add(payWayBean1.getData().get(i).getPay_name());
                            }
                            initPayWayPicker(payWayNameList);
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "SavingFragment");
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initDatePicker() {
//        pickerDate = new MyPickerForDate(getActivity(), MyPickerForDate.HOUR_24, "年", "月", "日");
//        pickerDate.setDateRangeStart(TimeUtil.getYear(), 1, 1);
//        pickerDate.setDateRangeEnd(TimeUtil.getYear(), 12, 31);
//        pickerDate.setWeightEnable(true);
//        pickerDate.setWheelModeEnable(true);
//        pickerDate.setOnDateTimePickListener(new MyPickerForDate.OnYearMonthDayTimePickListener() {
//            @Override
//            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
//                tvRemitDate.setText(year + "-" + month + "-" + day);
//            }
//        });
        pickerDateTime = new DateTimePicker(getActivity(), DateTimePicker.HOUR_24);
        pickerDateTime.setDateRangeStart(TimeUtil.getYear(), 1, 1);
        pickerDateTime.setDateRangeEnd(TimeUtil.getYear(), 12, 31);
        pickerDateTime.setTimeRangeStart(0, 0);
        pickerDateTime.setTimeRangeEnd(23, 59);
        pickerDateTime.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                tvRemitDate.setText(year + "-" + month + "-" + day);
                tvRemitHour.setText(hour);
                tvRemitMinute.setText(":" + minute);
//                ToastUtil.showShort(getContext(), year + "-" + month + "-" + day + " " + hour + ":" + minute);
            }
        });
    }

    private void initRemitType(List<String> type) {
        if (type != null && type.size() != 0) {
            tvRemitType.setText(type.get(0));
            pickerRemitType = new MyPicker<>(getActivity(), type);
            initPicker(pickerRemitType);
        } else {
            pickerRemitType.removeAllItem();
            tvRemitType.setText("暂无");
        }

        pickerRemitType.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                tvRemitType.setText(item);
                if (tvRemitType.getText().equals("其它[手动输入]")) {
                    llOtherPayWay.setVisibility(View.VISIBLE);
                } else {
                    llOtherPayWay.setVisibility(View.GONE);
                }
                if ("网银转账".equals(item)) {
                    llRemitPeople.setVisibility(View.VISIBLE);
                    tvRemitBackground.setVisibility(View.VISIBLE);
                } else {
                    llRemitPeople.setVisibility(View.GONE);
                    tvRemitBackground.setVisibility(View.GONE);
                }
                if ("支付宝入款".equals(item)) {
                    initAliPayViewData();
                } else {
                    initViewData();
                }
            }
        });
    }

    private void initViewData() {
        llAliName.setVisibility(View.GONE);
        tvAliName.setVisibility(View.GONE);
        llPayPlace.setVisibility(View.VISIBLE);
        tvPayPlace.setVisibility(View.VISIBLE);
        llPayName.setVisibility(View.GONE);
        tvPayName.setVisibility(View.GONE);
        recyclerViewAliAccount.setVisibility(View.GONE);
        recyclerViewCardType.setVisibility(View.VISIBLE);
        tvCardAddress.setVisibility(View.VISIBLE);
        tvAttention1.setText(offlinePayBean.getData().get(0).getPara().getTip().get(0));
        tvAttention2.setText(offlinePayBean.getData().get(0).getPara().getTip().get(1));
        tvAttention3.setVisibility(View.VISIBLE);
        tvAttention3.setText(offlinePayBean.getData().get(0).getPara().getTip().get(2));
        tvCardAddress.setText(offlinePayBean.getData().get(0).getPara().getBank().get(0).getAddress());
        tvUserName.setText(offlinePayBean.getData().get(0).getPara().getBank().get(0).getName());
        tvBankNumber.setText(offlinePayBean.getData().get(0).getPara().getBank().get(0).getCardid());
        bankAccountForHKAdapter.setImgUrl(offlinePayBean.getData().get(0).getPara().getImg());
        bankAccountForHKAdapter.setData(offlinePayBean.getData().get(0).getPara().getBank());
    }

    private void initAliPayViewData() {
        llAliName.setVisibility(View.VISIBLE);
        tvAliName.setVisibility(View.VISIBLE);
        recyclerViewAliAccount.setVisibility(View.VISIBLE);
        recyclerViewCardType.setVisibility(View.GONE);
        llPayPlace.setVisibility(View.GONE);
        tvPayPlace.setVisibility(View.GONE);
        llPayName.setVisibility(View.VISIBLE);
        tvPayName.setVisibility(View.VISIBLE);
        tvCardAddress.setVisibility(View.GONE);
        tvAttention1.setText(offlinePayBean.getData().get(1).getPara().getTip().get(0));
        tvAttention2.setText(offlinePayBean.getData().get(1).getPara().getTip().get(1));
        tvAttention3.setVisibility(View.GONE);
        tvUserName.setText(offlinePayBean.getData().get(1).getPara().getAccount().get(0).getAlipayName());
        tvBankNumber.setText(offlinePayBean.getData().get(1).getPara().getAccount().get(0).getAlipayID());
        bankAccountForAliAdapter.setImageUrl(offlinePayBean.getData().get(1).getPara().getImg());
        bankAccountForAliAdapter.setData(offlinePayBean.getData().get(1).getPara().getAccount());
    }

    public void initPicker(MyPicker myPicker) {
        myPicker.setCanLoop(false);//不禁用循环
        myPicker.setLineVisible(true);
        myPicker.setShadowVisible(true);
        myPicker.setTextSize(18);
        myPicker.setSelectedIndex(1);
        myPicker.setWheelModeEnable(true);
        //启用权重 setWeightWidth 才起作用
        myPicker.setWeightEnable(true);
        myPicker.setWeightWidth(1);
        myPicker.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
        myPicker.setUnSelectedTextColor(0xFF999999);
        myPicker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int index, String item) {
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && userSP.getInt(UserSP.LOGIN_SUCCESS) != -1) {
            initAllViewData();
            requestData();
        }
    }
}
