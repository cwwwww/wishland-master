package com.wishland.www.wanhaohui2.view.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.AccountBankBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.FundsPupupListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class BindBankActivity extends BaseStyleActivity {

    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.et_bind_number)
    EditText et_bind_number;
    @BindView(R.id.et_bind_address)
    EditText et_bind_address;
    @BindView(R.id.et_bind_psw)
    EditText et_bind_psw;
    @BindView(R.id.rl_choose_bank)
    RelativeLayout rl_choose_bank;
    @BindView(R.id.et_bind_pay_name)
    EditText et_bind_pay_name;

    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private List<AccountBankBean.DataBean> bankList;
    private PopupWindow window;
    private AccountBankBean.DataBean bankInfo = new AccountBankBean.DataBean();
    private String payName;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("银行卡");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        bankList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bind_bank, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        payName = getIntent().getStringExtra("pay_name");
        //收款人姓名只能修改一次
        if (payName != null && !"".equals(payName)) {
            et_bind_pay_name.setText(payName);
            et_bind_pay_name.setFocusable(false);
            et_bind_pay_name.setFocusableInTouchMode(false);
        } else {
            et_bind_pay_name.setFocusableInTouchMode(true);
            et_bind_pay_name.setFocusable(true);
            et_bind_pay_name.requestFocus();
        }

    }

    @OnClick({R.id.rl_choose_bank, R.id.bt_bind_close})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_choose_bank:
                chooseBankInfo();
                break;
            case R.id.bt_bind_close:
                submitBankInfo();
                break;
        }
    }

    private void submitBankInfo() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String pay_name = et_bind_pay_name.getText().toString();
        String pay_card = tv_bank_name.getText().toString();
        String pay_num = et_bind_number.getText().toString();
        String pay_address = et_bind_address.getText().toString();
        String bankCode = bankInfo.getBankno();
        String wpassword = et_bind_psw.getText().toString();

        if ("".equals(pay_name) || "点击选择收款银行".equals(pay_card) || "".equals(pay_num) || "".equals(pay_address) || "".equals(bankCode) || "".equals(wpassword)) {
            ToastUtil.showShort(BindBankActivity.this, "信息填写不完整");
            return;
        }
        //密码格式限制(6位数的数字)
        String regEx = "^\\d{6}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(wpassword);
        if (!matcher.matches()) {
            ToastUtil.showShort(this, "设置取款的密码格式不正确，请重新设置！");
            return;
        }

        if (map.size() != 0) {
            map.clear();
        }
        map.put("pay_name", pay_name);
        map.put("pay_card", pay_card);
        map.put("pay_num", pay_num);
        map.put("pay_address", pay_address);
        map.put("bankCode", bankCode);
        map.put("wpassword", wpassword);

        instance.apiAccount_Bind(pay_name, pay_card, pay_num, pay_address, bankCode, wpassword, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("银行信息请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("银行信息请求失败：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    Log.i("linearResponse", string);
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("银行信息请求完成" + string);
                    if (status == 200) {
                        ToastUtil.showShort(BindBankActivity.this, "更换成功");
                        finish();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(BindBankActivity.this, LoginActivity.class, "BindBankActivity");
                        } else {
                            ToastUtil.showShort(BindBankActivity.this, errorMsg);
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

    private void chooseBankInfo() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiBank(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("银行信息请求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("银行信息请求失败：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    Log.i("linearResponse", string.toString());
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        AccountBankBean accountBankBean = instance.getGson().fromJson(string, AccountBankBean.class);
                        bankList = accountBankBean.getData();

                        setDataPopup(bankList);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(BindBankActivity.this, LoginActivity.class, "BindBankActivity");
                        } else {
                            ToastUtil.showShort(BindBankActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //  ToastUtil.showShort(BindBankActivity.this, "获取银行卡信息异常");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //   ToastUtil.showShort(BindBankActivity.this, "获取银行卡信息异常");
                }
            }
        });
    }


    public void setDataPopup(final List<AccountBankBean.DataBean> list) {
        View popupView = BindBankActivity.this.getLayoutInflater().inflate(R.layout.banks_popupwindow, null);
        ListView lsvMore = (ListView) popupView.findViewById(R.id.funds_popupview);
        FundsPupupListAdapter fundsPupupListAdapter = new FundsPupupListAdapter(list);
        lsvMore.setAdapter(fundsPupupListAdapter);
        lsvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_bank_name.setText(list.get(i).getBankname());
                bankInfo = list.get(i);
                window.dismiss();
            }
        });
        window = new PopupWindow(popupView, 300, 600);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        window.showAsDropDown(tv_bank_name, 0, 20);
    }

}
