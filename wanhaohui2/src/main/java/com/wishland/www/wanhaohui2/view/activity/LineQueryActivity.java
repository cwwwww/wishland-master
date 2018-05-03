package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.BetTypeBean;
import com.wishland.www.wanhaohui2.bean.DateBean;
import com.wishland.www.wanhaohui2.bean.MessageType;
import com.wishland.www.wanhaohui2.bean.PlaceOrderBean;
import com.wishland.www.wanhaohui2.bean.RecordQueryBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.TimeUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;

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

public class LineQueryActivity extends BaseStyleActivity {

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
        setTitle("转换查询");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_line_query, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_time_range, R.id.bt_query_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time_range:
                final ArrayList<String> dateList = new ArrayList<>();
                dateList.add("一天");
                dateList.add("七天");
                dateList.add("一个月");
                dateList.add("三个月");
//                dateList.add("两个月");
//                dateList.add("半年");
                final ArrayList<DateBean> timeList = new ArrayList<>();
                timeList.add(new DateBean("一天", 1));
                timeList.add(new DateBean("七天", 7));
                timeList.add(new DateBean("一个月", 30));
                timeList.add(new DateBean("三个月", 90));
//                timeList.add(new DateBean("两个月", 60));
//                timeList.add(new DateBean("半年", 182));
                setPicker(dateList, timeList);
                break;
            case R.id.bt_query_record:

                if ("请选择时间范围".equals(orderTime.getText().toString())) {
                    ToastUtil.showShort(LineQueryActivity.this, "请选择时间范围");
                } else {
                    queryPlaceOrder();
                }


                break;

        }
    }

    private void setPicker(List<String> list, final List<DateBean> dateList) {
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
                orderTime.setText(dateList.get(index).getTime());
                recordQueryBean.setDays(dateList.get(index).getDate());

            }
        });
        picker2.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                orderTime.setText(dateList.get(index).getTime());
                recordQueryBean.setDays(dateList.get(index).getDate());

            }
        });
        picker2.show();
    }

    private void queryPlaceOrder() {
        Date endDate = TimeUtil.getSystemDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endtime = sdf.format(endDate);
        Date startDate = TimeUtil.getDateBefore(endDate, recordQueryBean.getDays());
        String starttime = sdf.format(startDate);

        MessageType messageType = new MessageType(starttime, endtime, 0, 7, "转换记录");
        Intent intent = new Intent(LineQueryActivity.this, DetailQuestDealActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MESSAGE", messageType);
        intent.putExtras(bundle);
        startActivity(intent);
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
