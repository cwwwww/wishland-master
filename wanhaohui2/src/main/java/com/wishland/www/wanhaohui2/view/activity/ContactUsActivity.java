package com.wishland.www.wanhaohui2.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.ContactBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

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
 * Created by admin on 2017/10/9.
 */

public class ContactUsActivity extends BaseStyleActivity {
    @BindView(R.id.et_contact_number)
    EditText et_contact_number;
    @BindView(R.id.ll_submit_success)
    LinearLayout ll_submit_success;
    @BindView(R.id.ll_submit_contactUs)
    LinearLayout ll_submit_contactUs;
    @BindView(R.id.ll_click_kf)
    LinearLayout ll_click_kf;
    @BindView(R.id.tv_contact_wx)
    TextView tv_contact_wx;
    @BindView(R.id.tv_contact_qq)
    TextView tv_contact_qq;
    @BindView(R.id.tv_contact_skype)
    TextView tv_contact_skype;
    @BindView(R.id.tv_contact_email)
    TextView tv_contact_email;
    @BindView(R.id.empty_layout)
    EmptyLayout empty_layout;

    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private String qqUrl = "";

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("联系我们");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        getInfo();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_contact_us, R.layout.base_toolbar_back);
        ButterKnife.bind(this);

//        tv_contact_wx.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                // 将文本内容放到系统剪贴板里。
//                cm.setText(tv_contact_wx.getText());
//                ToastUtil.showShort(ContactUsActivity.this, "复制成功");
//                return false;
//            }
//        });
//        tv_contact_qq.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                // 将文本内容放到系统剪贴板里。
//                cm.setText(tv_contact_qq.getText());
//                ToastUtil.showShort(ContactUsActivity.this, "已复制成功");
//                return false;
//            }
//        });
//        tv_contact_skype.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                // 将文本内容放到系统剪贴板里。
//                cm.setText(tv_contact_skype.getText());
//                ToastUtil.showShort(ContactUsActivity.this, "已复制成功");
//                return false;
//            }
//        });
    }

    @OnClick({R.id.fl_submit_contact, R.id.bt_submit_close, R.id.ll_click_kf, R.id.ll_click_qq, R.id.ll_contact_wx, R.id.ll_contact_skype, R.id.ll_contact_email})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_click_qq:
                if (isQQClientAvailable(this)) {
                    if (!"".equals(qqUrl)) {
                        String url11 = qqUrl;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=2827506426&version=1")));
//                    String url11 = "mqqwpa://im/chat?chat_type=wpa&uin=100000&version=1";
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
                    }
                } else {
                    ToastUtil.showShort(this, "没有qq应用，请先安装！");
                }
                break;
            case R.id.fl_submit_contact:
                submitContact();
                break;
            case R.id.bt_submit_close:
                finish();
                break;
            case R.id.ll_contact_wx:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tv_contact_wx.getText());
                ToastUtil.showShort(ContactUsActivity.this, "已复制微信号");
                break;
            case R.id.ll_contact_skype:
                ClipboardManager cm2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm2.setText(tv_contact_skype.getText());
                ToastUtil.showShort(ContactUsActivity.this, "已复制Skype ID");
                break;
            case R.id.ll_contact_email:
                ClipboardManager cm3 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm3.setText(tv_contact_email.getText());
                ToastUtil.showShort(ContactUsActivity.this, "已复制邮箱地址");
                break;
            case R.id.ll_click_kf:
                empty_layout.setLoadingMessage("正在连接客服...");
                empty_layout.showLoading();
                Intent intent = new Intent(this, DetailsHtmlPageActivity.class);
                intent.setAction(BaseApi.NEWHTML);
                intent.putExtra("title", "客服");
                intent.putExtra(BaseApi.HTML5DATA, BaseApi.CustomHtml5);
                startActivity(intent);
                break;
        }
    }

    private void getInfo() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        instance.apiContact(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        ContactBean contactBean = new Gson().fromJson(string, ContactBean.class);
                        tv_contact_wx.setText("微信号：" + contactBean.getData().getWx() + "");
                        tv_contact_qq.setText("QQ：" + contactBean.getData().getQq() + "");
                        tv_contact_skype.setText("Skype ID：" + contactBean.getData().getSkype() + "");
                        tv_contact_email.setText("邮箱：" + contactBean.getData().getEmail() + "");
                        qqUrl = contactBean.getData().getQqurl();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(ContactUsActivity.this, LoginActivity.class, "ContactUsActivity");
                        } else {
                            ToastUtil.showShort(ContactUsActivity.this, errorMsg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void submitContact() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if ("".equals(et_contact_number.getText().toString())) {
            ToastUtil.showShort(ContactUsActivity.this, "手机号不能为空");
            return;
        }
        String tel = et_contact_number.getText().toString();

        if (map.size() != 0) {
            map.clear();
        }
        map.put("tel", tel);
        instance.apiCallBack(tel, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody account) {
                try {
                    String string = account.string();
                    Log.i("linearResponse", string);
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        ll_submit_success.setVisibility(View.VISIBLE);
                        ll_submit_contactUs.setVisibility(View.GONE);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(ContactUsActivity.this, LoginActivity.class, "ContactUsActivity");
                        } else {
                            ToastUtil.showShort(ContactUsActivity.this, errorMsg);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        empty_layout.hide();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
}
