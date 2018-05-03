package com.wishland.www.wanhaohui2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.DiscountBean;
import com.wishland.www.wanhaohui2.bean.DiscountTypeBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.JudgeNewWorkUtil;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.StatusBarHightUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.adapter.DiscountAdapter;
import com.wishland.www.wanhaohui2.view.adapter.DiscountTypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/7.
 */

public class DiscountFragment extends UmengFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    Unbinder unbinder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_type)
    RecyclerView recyclerType;
    //    @BindView(R.id.ll_activity)
//    LinearLayout llActivity;
    @BindView(R.id.refreshView)
    SwipeRefreshLayout refreshView;
    @BindView(R.id.fl_view_top)
    FrameLayout flVieTop;
    private DiscountAdapter discountAdapter;
    private DiscountTypeAdapter discountTypeAdapter;
    private List<DiscountTypeBean> typeList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    private LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
    private Model instance;
    private UserSP userSP;
    private String limit = "";
    List<DiscountBean.DataBean> discountBeenList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = Model.getInstance();
        userSP = instance.getUserSP();

        typeList.add(new DiscountTypeBean("体育"));
        typeList.add(new DiscountTypeBean("娱乐"));
        typeList.add(new DiscountTypeBean("政治"));
        discountAdapter = new DiscountAdapter(getActivity());
        discountAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String url = discountBeenList.get(position).getUrl();
                WebUtil.toWebDiscountActivity(getContext(), url, "", String.valueOf(discountBeenList.get(position).getAid()));
            }
        });
        discountTypeAdapter = new DiscountTypeAdapter(getActivity());
//        discountAdapter.addData(discountBeenList);
        discountTypeAdapter.addData(typeList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discounts, null);
        unbinder = ButterKnife.bind(this, view);
        initStatusBar();
        tvTitle.setText("优惠活动");
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(discountAdapter);

        recyclerType.setLayoutManager(linearLayoutManager1);
        recyclerType.setAdapter(discountTypeAdapter);
//
//        llActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (recyclerType.getVisibility() == View.GONE) {
//                    recyclerType.setVisibility(View.VISIBLE);
//                } else {
//                    recyclerType.setVisibility(View.GONE);
//                }
//            }
//        });
        initView();
        requestData();
        return view;
    }

    private void requestData() {
        if (JudgeNewWorkUtil.isNetworkAvalible(getContext())) {
            getDiscountDataList();
        } else {
            ToastUtil.showShort(getContext(), "网络异常，请检查网络设置！");
            refreshView.setRefreshing(false);
        }
    }

    private void initStatusBar() {
        int padding = StatusBarHightUtil.getStatusBarHeight();
        tvTitle.setPadding(padding, padding, padding, padding / 3);
    }

    private void initView() {
        refreshView.setRefreshing(true);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        setRefreshStyle();
    }

    private void setRefreshStyle() {
        refreshView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright), getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light), getResources().getColor(android.R.color.holo_red_light));
    }

    private void getDiscountDataList() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
//        String limit = "4";
//        String type = "content";
        Map<String, String> map = new HashMap<>();
//        map.put("limit", limit);
//        map.put("type", type);
        map.put("location", "local");
        instance.apiActivity(token, NetUtil.getParamsPro(map).get("signature"), limit, "", "", "local",
                new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        refreshView.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshView.setRefreshing(false);
                        LogUtil.e("cww", e.getMessage());
//                        ToastUtil.showLong(getContext(), "优惠活动列表请求异常！");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        refreshView.setRefreshing(false);
                        try {
                            String discountData = responseBody.string();
                            JSONObject jsonObject = instance.getJsonObject(discountData);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            if (status == 200) {
                                DiscountBean discountBean = new Gson().fromJson(discountData, DiscountBean.class);
                                discountBeenList = new ArrayList<>();
                                discountBeenList.addAll(discountBean.getData());
                                discountAdapter.setData(discountBeenList);
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(getActivity(), LoginActivity.class, "DiscountFragment");
                                } else {
                                    ToastUtil.showShort(getContext(), "优惠活动列表请求异常！");
                                }
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            //  ToastUtil.showLong(getContext(), "请求数据出错！");
                        }

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
