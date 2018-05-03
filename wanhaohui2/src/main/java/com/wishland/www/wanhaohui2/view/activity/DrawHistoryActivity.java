package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.QueryMessageListBean;
import com.wishland.www.wanhaohui2.bean.QuestDealBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.TimeUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.DrawHistoryAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.MyDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class DrawHistoryActivity extends BaseStyleActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rlv_draw_list)
    RecyclerView rlv_draw_list;
    @BindView(R.id.tv_no_info)
    ImageView tv_no_info;
    @BindView(R.id.srl_draw_history)
    SwipeRefreshLayout srl_draw_history;


    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private List<QueryMessageListBean> list;
    private List<QuestDealBean.QuestDealData.CommonBean> commonList;
    private DrawHistoryAdapter drawHistoryAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("取款记录");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        list = new ArrayList<>();
        commonList = new ArrayList<>();
        requestMessage();
//        for (int i=0;i<20;i++){
//            QueryMessageListBean bean=new QueryMessageListBean();
//            bean.setChinaTime("2017-12-32 12:20:23");
//            list.add(bean);
//        }
//        drawHistoryAdapter.setData(list);
        srl_draw_history.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_draw_history, R.layout.base_toolbar_back_btn);
        ButterKnife.bind(this);

        linearLayoutManager = new LinearLayoutManager(this);
        rlv_draw_list.setLayoutManager(linearLayoutManager);
        rlv_draw_list.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        drawHistoryAdapter = new DrawHistoryAdapter(this, 2);
        rlv_draw_list.setAdapter(drawHistoryAdapter);

        drawHistoryAdapter.setOnItemClickListener(new DrawHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                QuestDealBean.QuestDealData.CommonBean commonBean = commonList.get(position);
                Intent intent = new Intent(DrawHistoryActivity.this, DrwaResultActivity.class);
                intent.putExtra("BankName", "****银行");
                intent.putExtra("BankNumber", "****");
                intent.putExtra("SubmitMoney", commonBean.getSum());
                intent.putExtra("PayStatus", commonBean.getState());
                startActivity(intent);
            }
        });

    }

    private void requestMessage() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        Date endDate = TimeUtil.getSystemDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String endtime = sdf.format(endDate);
        Date startDate = TimeUtil.getDateBefore(endDate, 182);
        String starttime = sdf.format(startDate);

        int subtype = 0;
        int type = 2;

        int queryCnt = 500;
        int queryId = 0;
        if (map.size() != 0) {
            map.clear();
        }
        map.put("start", starttime);
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");
        map.put("end", endtime);
        map.put("subtype", subtype + "");
        map.put("type", type + "");

        instance.apiMessage(starttime, queryCnt, queryId + "",
                endtime, subtype, type, token, NetUtil.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        LogUtil.e("交易记录查询完成");
                        srl_draw_history.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(DrawHistoryActivity.this, "网络异常");
                        LogUtil.e("交易记录查询异常：" + e.getMessage());
                        srl_draw_history.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResponseBody json) {
                        try {
                            final String string = json.string();
                            Log.i("globaBean", string);
                            final JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            LogUtil.e("交易记录查询成功");
                            if (status == 200) {
                                srl_draw_history.setRefreshing(false);
                                JSONObject data = jsonObject.optJSONObject("data");
                                JSONArray getcommonList = data.optJSONArray("commonList");
                                if (getcommonList == null) {
                                    tv_no_info.setVisibility(View.VISIBLE);
                                } else {
                                    tv_no_info.setVisibility(View.GONE);
                                    commonList = instance.getGson().fromJson(getcommonList.toString(), new TypeToken<List<QuestDealBean.QuestDealData.CommonBean>>() {
                                    }.getType());
                                    if (commonList.size() != 0) {
                                        drawHistoryAdapter.setData(commonList);
                                    }

                                }
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(DrawHistoryActivity.this, LoginActivity.class,"DrawHistoryActivity");
                                } else {
                                    ToastUtil.showShort(DrawHistoryActivity.this, errorMsg);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        requestMessage();
    }
}
