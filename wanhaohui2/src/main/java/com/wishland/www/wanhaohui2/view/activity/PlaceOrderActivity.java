package com.wishland.www.wanhaohui2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.BetTypeBean;
import com.wishland.www.wanhaohui2.bean.DateBean;
import com.wishland.www.wanhaohui2.bean.MessageType;
import com.wishland.www.wanhaohui2.bean.PlaceOrderBean;
import com.wishland.www.wanhaohui2.bean.RecordQueryBean;
import com.wishland.www.wanhaohui2.bean.RecordTypeBean;
import com.wishland.www.wanhaohui2.model.AccountDataSP;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
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

public class PlaceOrderActivity extends BaseStyleActivity {

    @BindView(R.id.tv_record_type)
    TextView orderType;
    @BindView(R.id.tv_record_time)
    TextView orderTime;

    private Model instance;
    private UserSP userSP;
    private RecordQueryBean recordQueryBean = new RecordQueryBean();


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("下注查询");
        instance = Model.getInstance();
        userSP = instance.getUserSP();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_place_order, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_record_type, R.id.ll_time_range, R.id.bt_query_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_record_type:
                requestRecordType();
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
                setPicker(dateList, 1, null, timeList);
                break;
            case R.id.bt_query_record:
                if ("请选择下注类型".equals(orderType.getText().toString())) {
                    ToastUtil.showShort(PlaceOrderActivity.this, "请选择下注类型");
                } else {
                    if ("请选择时间范围".equals(orderTime.getText().toString())) {
                        ToastUtil.showShort(PlaceOrderActivity.this, "请选择时间范围");
                    } else {
                        queryPlaceOrder();
                    }
                }

                break;

        }
    }

    private void queryPlaceOrder() {
        String name = recordQueryBean.getName();
        String type = recordQueryBean.getTitle();
        Date endDate = TimeUtil.getSystemDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endtime = sdf.format(endDate);
        Date startDate = TimeUtil.getDateBefore(endDate, recordQueryBean.getDays());
        String starttime = sdf.format(startDate);

        PlaceOrderBean placeOrderBean = new PlaceOrderBean(starttime, endtime, type, name);
        Intent intent = new Intent(PlaceOrderActivity.this, PlaceOrderDealActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MESSAGE", placeOrderBean);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void requestRecordType() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        instance.apiBetType(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("转换信息查询完成");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(PlaceOrderActivity.this, "网络异常");
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
                        BetTypeBean recordTypeBean = new Gson().fromJson(string, BetTypeBean.class);
                        List<BetTypeBean.BetTypeData> dateList = recordTypeBean.getData();
                        List<String> stringList = new ArrayList<String>();
                        for (BetTypeBean.BetTypeData data : dateList
                                ) {
                            stringList.add(data.getName());
                        }
                        setPicker(stringList, 0, dateList, null);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(PlaceOrderActivity.this, LoginActivity.class,"PlaceOrderActivity");
                        } else {
                            ToastUtil.showShort(PlaceOrderActivity.this, errorMsg);
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

    private void setPicker(List<String> list, final int pickerType, final List<BetTypeBean.BetTypeData> betTypeList, final List<DateBean> dateList) {
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
                if (pickerType == 0) {
                    orderType.setText(betTypeList.get(index).getName());
                    recordQueryBean.setName(betTypeList.get(index).getName());
                    recordQueryBean.setTitle(betTypeList.get(index).getKey());
                } else {
                    orderTime.setText(dateList.get(index).getTime());
                    recordQueryBean.setDays(dateList.get(index).getDate());
                }
            }
        });
        picker2.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                if (pickerType == 0) {
                    orderType.setText(betTypeList.get(index).getName());
                    recordQueryBean.setName(betTypeList.get(index).getName());
                    recordQueryBean.setTitle(betTypeList.get(index).getKey());
                } else {
                    orderTime.setText(dateList.get(index).getTime());
                    recordQueryBean.setDays(dateList.get(index).getDate());
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
}
