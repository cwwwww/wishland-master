package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.DateBean;
import com.wishland.www.wanhaohui2.bean.GlobalBean;
import com.wishland.www.wanhaohui2.bean.MessageType;
import com.wishland.www.wanhaohui2.bean.RecordQueryBean;
import com.wishland.www.wanhaohui2.bean.RecordTypeBean;
import com.wishland.www.wanhaohui2.model.AccountDataSP;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.TimeUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/11.
 */

public class RecordQueryActivity extends BaseStyleActivity {
    @BindView(R.id.tv_record_type)
    TextView tv_record_type;
    @BindView(R.id.tv_record_time)
    TextView tv_record_time;
    @BindView(R.id.tv_money_content)
    TextView tv_money_content;
    @BindView(R.id.ll_content_money)
    LinearLayout ll_content_money;

    private Model instance;
    private AccountDataSP accountDataSP;
    private UserSP userSP;
    private GlobalBean globalBean;
    private int maketype;
    private List<Integer> typesList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private List<String> datesList = new ArrayList<>();
    private List<Integer> cotentsId = new ArrayList<>();
    private List<String> cotentsName = new ArrayList<>();

    private RecordQueryBean recordQueryBean = new RecordQueryBean();
    private List<GlobalBean.DataBean.TradeSubTypeBean> tradeSubType = new ArrayList<>();

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("账户查询");
        instance = Model.getInstance();
        accountDataSP = instance.getAccountDataSP();
        userSP = instance.getUserSP();
        String accountData = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_GLOBAL);
        globalBean = instance.getGson().fromJson(accountData, GlobalBean.class);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_record_query, R.layout.base_toolbar_back);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.ll_record_type, R.id.ll_time_range, R.id.bt_query_record, R.id.ll_money_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_query_record:
                if ("请选择下注类型".equals(tv_record_type.getText().toString())) {
                    ToastUtil.showShort(RecordQueryActivity.this, "请选择下注类型");
                } else {
                    if ("请选择时间范围".equals(tv_record_time.getText().toString())) {
                        ToastUtil.showShort(RecordQueryActivity.this, "请选择时间范围");
                    } else {
                        questMessage();
                    }
                }

                break;
            case R.id.ll_money_content:
                cotentsId.clear();
                cotentsName.clear();
                tradeSubType = globalBean.getData().getTradeSubType();
                for (int i = 0; i < tradeSubType.size(); i++) {
                    cotentsId.add(i);
                    cotentsName.add(tradeSubType.get(i).getName());
                }
                setPicker(cotentsName, 2, null);
                break;
            case R.id.ll_record_type:
                requestRecordType();
//                final ArrayList<String> list = new ArrayList<>();
//                list.add("存款");
//                list.add("汇款");
//                list.add("提款");
//                list.add("其他");
//                list.add("额度");
//
//                setPicker(list, 0, null);
                break;
            case R.id.ll_time_range:
                final ArrayList<String> dateList = new ArrayList<>();
                dateList.add("一天");
                dateList.add("七天");
                dateList.add("一个月");
                dateList.add("两个月");
                dateList.add("半年");
                final ArrayList<DateBean> timeList = new ArrayList<>();
                timeList.add(new DateBean("一天", 1));
                timeList.add(new DateBean("七天", 7));
                timeList.add(new DateBean("一个月", 30));
                timeList.add(new DateBean("两个月", 60));
                timeList.add(new DateBean("半年", 182));
                setPicker(dateList, 1, timeList);
                break;
        }
    }

    private void requestRecordType() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        instance.apiRecordType(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("转换信息查询完成");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(RecordQueryActivity.this, "网络异常");
                LogUtil.e("转换信息查询异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    LogUtil.i("linearResponse", string);
                    final JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        RecordTypeBean recordTypeBean = new Gson().fromJson(string, RecordTypeBean.class);
                        List<String> data = recordTypeBean.getData();
                        setPicker(data, 0, null);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(RecordQueryActivity.this, LoginActivity.class,"RecordQueryActivity");
                        } else {
                            ToastUtil.showShort(RecordQueryActivity.this, errorMsg);
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

    private void questMessage() {
        int type = recordQueryBean.getType();
        String name = recordQueryBean.getName();
        int content = recordQueryBean.getContent();
        Date endDate = TimeUtil.getSystemDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endtime = sdf.format(endDate);
        Date startDate = TimeUtil.getDateBefore(endDate, recordQueryBean.getDays());
        String starttime = sdf.format(startDate);

        MessageType messageType = new MessageType(starttime, endtime, content, type, name);
        Intent intent = new Intent(RecordQueryActivity.this, DetailQuestDealActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MESSAGE", messageType);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setPicker(List<String> list, final int pickerType, final List<DateBean> dateList) {
        SinglePicker<String> picker2 = new SinglePicker<>(this, list);
        picker2.setCanLoop(false);//不禁用循环
        picker2.setLineVisible(true);
        picker2.setShadowVisible(true);
        picker2.setTextSize(18);
        picker2.setSelectedIndex(1);
        picker2.setTitleText("请选择时间范围");
        picker2.setWheelModeEnable(true);
        picker2.setWeightEnable(true);
        picker2.setWeightWidth(1);
        picker2.setSelectedTextColor(0xFF279BAA);//前四位值是透明度
        picker2.setUnSelectedTextColor(0xFF999999);
        picker2.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int index, String item) {
//                        showToast("index=" + index + ", item=" + item);
                if (pickerType == 0) {
                    tv_record_type.setText(item);
                    maketype = index;
                    recordQueryBean.setType(index);
                    recordQueryBean.setName(item + "记录");
//                    if (index == 3) {
//                        ll_content_money.setVisibility(View.VISIBLE);
//                    } else {
//                        ll_content_money.setVisibility(View.GONE);
//                    }
                } else if (pickerType == 1) {
                    tv_record_time.setText(dateList.get(index).getTime());
                    recordQueryBean.setDays(dateList.get(index).getDate());
                } else if (pickerType == 2) {
                    tv_money_content.setText(item);
                    int content = 0;
                    if (tradeSubType != null) {
                        content = cotentsId.get(index);
                    }
                    recordQueryBean.setContent(content);
                }

            }
        });
        picker2.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
//                        showToast("index=" + index + ", item=" + item);
                if (pickerType == 0) {
                    tv_record_type.setText(item);
                    maketype = index;
                    recordQueryBean.setType(index);
                    recordQueryBean.setName(item + "记录");
//                    if (index == 3) {
//                        ll_content_money.setVisibility(View.VISIBLE);
//                    } else {
//                        ll_content_money.setVisibility(View.GONE);
//                    }
                } else if (pickerType == 1) {
                    tv_record_time.setText(dateList.get(index).getTime());
                    recordQueryBean.setDays(dateList.get(index).getDate());
                } else if (pickerType == 2) {
                    tv_money_content.setText(item);
                    int content = 0;
                    if (tradeSubType != null) {
                        content = cotentsId.get(index);
                    }
                    recordQueryBean.setContent(content);
                }
            }
        });
        picker2.show();
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

    }
}
