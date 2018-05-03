package com.wishland.www.wanhaohui2.view.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.BetTypeBean;
import com.wishland.www.wanhaohui2.bean.PlaceOrderBean;
import com.wishland.www.wanhaohui2.bean.PlaceOrderListBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.PlaceOrderAdapter;
import com.wishland.www.wanhaohui2.view.adapter.PlaceTitileAdapter;

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
import okhttp3.ResponseBody;
import rx.Subscriber;

public class PlaceOrderDealActivity extends BaseStyleActivity {

    @BindView(R.id.rv_place_order)
    RecyclerView recyclerView;
    //    @BindView(R.id.iv_down)
//    ImageView iv_down;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rl_title)
    FrameLayout rl_title;
    @BindView(R.id.tv_no_info)
    ImageView tv_no_info;


    private String startTime;
    private String endTime;
    private String name;
    private String type;
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private List<PlaceOrderListBean.DataBean.PlaceOrderData> betList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private PlaceOrderAdapter placeOrderAdapter;
    private PopupWindow popupWindow;
    private WindowManager wm;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("下注查询");
        PlaceOrderBean placeOrderBean = (PlaceOrderBean) getIntent().getSerializableExtra("MESSAGE");
        startTime = placeOrderBean.getStartTime();
        endTime = placeOrderBean.getEndTime();
        name = placeOrderBean.getName();
        type = placeOrderBean.getType();

        setData();
    }

    private void setData() {
        linearLayoutManager = new LinearLayoutManager(this);
        placeOrderAdapter = new PlaceOrderAdapter(this, name);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(placeOrderAdapter);

        requestMessage();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_place_order_deal, R.layout.base_toolbar_back_img);
        ButterKnife.bind(this);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
    }

    @OnClick({R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                requestRecordType();
                break;
        }
    }

    public void setDataPopup(final List<BetTypeBean.BetTypeData> list) {
        View popupView = this.getLayoutInflater().inflate(R.layout.funds_popupwindow, null);
        RecyclerView lsvMore = (RecyclerView) popupView.findViewById(R.id.funds_popupview);
        PlaceTitileAdapter linearAdapter = new PlaceTitileAdapter(this, name);
        lsvMore.setLayoutManager(new GridLayoutManager(this, 3));
        lsvMore.setAdapter(linearAdapter);
        linearAdapter.setOnItemClickListener(new PlaceTitileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BetTypeBean.BetTypeData betTypeData = list.get(position);
                type = betTypeData.getKey();
                name = betTypeData.getName();
                setData();
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(R.style.DialogTop);
        popupWindow.update();
//        popupWindow.showAtLocation(recyclerView, Gravity.BOTTOM,0,0);
        popupWindow.showAsDropDown(rl_title, 0, 0);
        linearAdapter.setData(list);
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
                ToastUtil.showShort(PlaceOrderDealActivity.this, "网络异常");
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
                        setDataPopup(dateList);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(PlaceOrderDealActivity.this, LoginActivity.class, "PlaceOrderDealActivity");
                        } else {
                            ToastUtil.showShort(PlaceOrderDealActivity.this, errorMsg);
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

    private void requestMessage() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String count = "100";
        String from = "0";
        if (map.size() != 0) {
            map.clear();
        }
        map.put("start", startTime);
        map.put("end", endTime);
        map.put("type", type);
        map.put("from", from);
        map.put("count", count);

        instance.apiQueryList(startTime, endTime, type, from, count, token, NetUtil.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e("交易記錄查詢完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(PlaceOrderDealActivity.this, "网络异常");
                        LogUtil.e("交易記錄查詢異常：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody json) {
                        try {
                            final String string = json.string();
                            final JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            LogUtil.e("交易記錄查詢成功");
                            if (status == 200) {
                                betList.clear();
                                PlaceOrderListBean placeOrderListBean = new Gson().fromJson(string, PlaceOrderListBean.class);
                                setTitle(name + "  ( " + placeOrderListBean.getData().getTotal() + "条记录 )");
                                List<PlaceOrderListBean.DataBean.PlaceOrderData> list = placeOrderListBean.getData().getBet();
                                betList.addAll(list);
                                if (betList.size() != 0) {
                                    tv_no_info.setVisibility(View.GONE);
                                    placeOrderAdapter.setData(betList);
                                } else {
                                    tv_no_info.setVisibility(View.VISIBLE);
                                }
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(PlaceOrderDealActivity.this, LoginActivity.class, "PlaceOrderDealActivity");
                                } else {
                                    ToastUtil.showShort(PlaceOrderDealActivity.this, errorMsg);
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
