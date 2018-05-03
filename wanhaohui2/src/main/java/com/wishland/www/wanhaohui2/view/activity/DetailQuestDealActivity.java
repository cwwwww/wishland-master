package com.wishland.www.wanhaohui2.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.LineDetailBean;
import com.wishland.www.wanhaohui2.bean.MessageType;
import com.wishland.www.wanhaohui2.bean.QueryMessageListBean;
import com.wishland.www.wanhaohui2.bean.QuestDealBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.DrawHistoryAdapter;
import com.wishland.www.wanhaohui2.view.adapter.FundsCathecticDealAdapter;
import com.wishland.www.wanhaohui2.view.adapter.LineDetailAdapter;
import com.wishland.www.wanhaohui2.view.adapter.OtherQuestAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class DetailQuestDealActivity extends BaseStyleActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_detail_quest)
    ListView fundslistview;
    @BindView(R.id.rv_line_detail)
    RecyclerView rv_line_detail;
    @BindView(R.id.srl_my_message)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tv_no_info)
    ImageView tv_no_info;

    private Model instance;
    private UserSP userSP;
    private String starttime;
    private String endtime;
    private int type;
    private int subtype;
    private FundsCathecticDealAdapter fundsCathecticDealAdapter;
    private LineDetailAdapter lineDetailAdapter;
    private DrawHistoryAdapter drawHistoryAdapter;
    private OtherQuestAdapter otherQuestAdapter;
    private Map<String, String> map = new HashMap<>();
    private List<QueryMessageListBean> list;
    private List<LineDetailBean.DataBean.ListBean> lineList;
    private String name;
    private List<QuestDealBean.QuestDealData.CommonBean> commonList;
    private List<QuestDealBean.QuestDealData.OtherBean> otherList;


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        list = new ArrayList<>();
        lineList = new ArrayList<>();
        commonList = new ArrayList<>();
        otherList = new ArrayList<>();
        MessageType message = (MessageType) getIntent().getSerializableExtra("MESSAGE");
        name = message.getName();
        setTitle(name);
        starttime = message.getStarttime();
        endtime = message.getEndtime();
        type = message.getType();
        subtype = message.getContent();

        fundsCathecticDealAdapter = new FundsCathecticDealAdapter(DetailQuestDealActivity.this, type);
        lineDetailAdapter = new LineDetailAdapter(this);
        drawHistoryAdapter = new DrawHistoryAdapter(this, type);
        otherQuestAdapter = new OtherQuestAdapter(this, type);
        rv_line_detail.setLayoutManager(new LinearLayoutManager(this));

        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getQuestInfo();

    }

    private void getQuestInfo() {
        if (type == 7) {
            rv_line_detail.setAdapter(lineDetailAdapter);
            fundslistview.setVisibility(View.GONE);
            rv_line_detail.setVisibility(View.VISIBLE);
            requestLineDetail(type);
        } else if (type == 2 || type == 0 || type == 1) {
            rv_line_detail.setAdapter(drawHistoryAdapter);
            fundslistview.setVisibility(View.GONE);
            rv_line_detail.setVisibility(View.VISIBLE);
            requestLineDetail(type);
        } else if (type == 4 || type == 3 || type == 5 || type == 6) {
            rv_line_detail.setAdapter(otherQuestAdapter);
            fundslistview.setVisibility(View.GONE);
            rv_line_detail.setVisibility(View.VISIBLE);
            requestLineDetail(type);
        } else {
            rv_line_detail.setVisibility(View.GONE);
            fundslistview.setVisibility(View.VISIBLE);
            requestMessage(type);
        }
    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_detail_quest_deal, R.layout.base_toolbar_back_btn);
        ButterKnife.bind(this);

        instance = Model.getInstance();
        userSP = instance.getUserSP();
    }

    private void requestLineDetail(final int type) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        String queryId = "0";
        if (map.size() != 0) {
            map.clear();
        }

        map.put("type", type + "");
        map.put("queryid", queryId);
        map.put("queryCnt", queryCnt + "");
        map.put("start", starttime);
        map.put("end", endtime);

        instance.apiFundQuery(type, queryId, queryCnt, starttime, endtime, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LogUtil.e("转换信息查询完成");
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(DetailQuestDealActivity.this, "网络异常");
                LogUtil.e("转换信息查询异常：" + e.getMessage());
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    Log.i("linearResponse", string);
                    final JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    swipeRefresh.setRefreshing(false);
                    if (status == 200) {
                        if (type == 7) {
                            if (jsonObject.optJSONObject("data").optJSONArray("list") == null) {
//                                ToastUtil.showShort(DetailQuestDealActivity.this, "近期无转换记录...");
                                tv_no_info.setVisibility(View.VISIBLE);
                            } else {
                                tv_no_info.setVisibility(View.GONE);
                                final LineDetailBean lineDetailBean = instance.getGson().fromJson(string, LineDetailBean.class);
                                lineList = lineDetailBean.getData().getList();
                                if (lineList.size() != 0) {
                                    lineDetailAdapter.setData(lineList);
                                }
                            }
                        } else if (type == 0 || type == 2 || type == 1) {
                            JSONObject data = jsonObject.optJSONObject("data");
                            JSONArray getcommonList = data.optJSONArray("commonList");
                            if (jsonObject.optJSONObject("data").optJSONArray("commonList") == null) {
//                                ToastUtil.showShort(DetailQuestDealActivity.this, "近期无金额记录...");
                                tv_no_info.setVisibility(View.VISIBLE);
                            } else {
                                tv_no_info.setVisibility(View.GONE);
//                                QuestDealBean lineDetailBean = new Gson().fromJson(string, QuestDealBean.class);
//                                commonList = lineDetailBean.getData().getCommonList();
                                commonList = instance.getGson().fromJson(getcommonList.toString(), new TypeToken<List<QuestDealBean.QuestDealData.CommonBean>>() {
                                }.getType());
                                if (commonList.size() != 0) {
                                    drawHistoryAdapter.setData(commonList);
                                }
                            }
                        } else if (type == 4 || type == 3 || type == 5 || type == 6) {
                            JSONObject data = jsonObject.optJSONObject("data");
                            JSONArray getcommonList = data.optJSONArray("otherList");
                            if (getcommonList == null) {
//                                ToastUtil.showShort(DetailQuestDealActivity.this, "近期无金额记录...");
                                tv_no_info.setVisibility(View.VISIBLE);
                            } else {
                                tv_no_info.setVisibility(View.GONE);
                                otherList = instance.getGson().fromJson(getcommonList.toString(), new TypeToken<List<QuestDealBean.QuestDealData.OtherBean>>() {
                                }.getType());
                                if (otherList.size() != 0) {
                                    otherQuestAdapter.setData(otherList);
                                }
                            }
                        }

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(DetailQuestDealActivity.this, LoginActivity.class,"DetailQuestDealActivity");
                        } else {
                            ToastUtil.showShort(DetailQuestDealActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // ToastUtil.showShort(DetailQuestDealActivity.this, "额度转换记录获取异常");
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  ToastUtil.showShort(DetailQuestDealActivity.this, "额度转换记录获取异常");
                }
            }
        });
    }


    private void requestMessage(final int type) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
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
                        LogUtil.e("交易記錄查詢完成");
                        swipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShort(DetailQuestDealActivity.this, "网络异常");
                        LogUtil.e("交易記錄查詢異常：" + e.getMessage());
                        swipeRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ResponseBody json) {
                        try {
                            final String string = json.string();
                            Log.i("globaBean", string);
                            final JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            LogUtil.e("交易記錄查詢成功");
                            swipeRefresh.setRefreshing(false);
                            if (status == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setDataItem(jsonObject, type);
                                        setTitle(name + "  ( " + list.size() + "条记录 )");
                                        if (list.size() != 0) {
                                            tv_no_info.setVisibility(View.GONE);
                                            if (type == 2 || type == 0) {
                                                drawHistoryAdapter.setData(commonList);
                                                rv_line_detail.setAdapter(drawHistoryAdapter);
                                            } else {
                                                fundsCathecticDealAdapter.setData(list);
                                                fundslistview.setAdapter(fundsCathecticDealAdapter);
                                            }
                                        } else {
                                            tv_no_info.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(DetailQuestDealActivity.this, LoginActivity.class,"DetailQuestDealActivity");
                                } else {
                                    ToastUtil.showShort(DetailQuestDealActivity.this, errorMsg);
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

    private void setDataItem(final JSONObject json, final int type) {

        JSONObject data = json.optJSONObject("data");
        switch (type) {
            case 0:
            case 1:
            case 2:
                JSONArray getcommonList = data.optJSONArray("commonList");
                if (getcommonList == null) {
                    ToastUtil.showShort(DetailQuestDealActivity.this, name + "无记录");
                } else {
                    commonList = instance.getGson().fromJson(getcommonList.toString(), new TypeToken<List<QuestDealBean.QuestDealData.CommonBean>>() {
                    }.getType());
                }
                break;
            case 3:
                JSONArray otherList = data.optJSONArray("otherList");
                if (otherList == null) {
                    ToastUtil.showShort(DetailQuestDealActivity.this, name + "无记录");
                } else {
                    list = instance.getGson().fromJson(otherList.toString(), new TypeToken<List<QueryMessageListBean>>() {
                    }.getType());
                }
                break;
        }
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
        getQuestInfo();
    }


}
