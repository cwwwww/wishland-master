package com.wishland.www.wanhaohui2.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.LevelBean;
import com.wishland.www.wanhaohui2.bean.MyDiscountBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.activity.MyMessageActivity;
import com.wishland.www.wanhaohui2.view.adapter.MyDiscountAdapter;

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
 * Created by admin on 2017/10/14.
 */

public class MyDiscountListFragment extends UmengFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_my_discount)
    RecyclerView rv_my_discount;
    @BindView(R.id.srl_discount)
    SwipeRefreshLayout srl_discount;
    @BindView(R.id.tv_no_info)
    ImageView tv_no_info;

    Unbinder unbinder;
    private MyDiscountBean.MyDiscountData allData;

    public static MyDiscountListFragment newInstance(String type) {
        MyDiscountListFragment newFragment = new MyDiscountListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    private LinearLayoutManager linearLayoutManager;
    private String type;
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;
    private MyDiscountAdapter adapter;
    private MyDiscountAdapter adapter2;
    private MyDiscountAdapter adapter3;
    private List<MyDiscountBean.MyDiscountData.NewDiscountBean> newList = new ArrayList<>();
    private List<MyDiscountBean.MyDiscountData.NewDiscountBean> discountList = new ArrayList<>();
    private List<MyDiscountBean.MyDiscountData.NewDiscountBean> finishList = new ArrayList<>();
    private CallBackValue callBackValue;
    private boolean isCreate = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = (CallBackValue) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_discount_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        map = new HashMap<>();
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_my_discount.setLayoutManager(linearLayoutManager);
        adapter = new MyDiscountAdapter(getActivity(), getContext());
        rv_my_discount.setAdapter(adapter);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        srl_discount.setRefreshing(true);
        srl_discount.setOnRefreshListener(this);
        srl_discount.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        Bundle args = getArguments();
        if (args != null) {
            type = args.getString("type");
        }
        initData();
        isCreate = true;
        return view;
    }

    public void initData() {
        getCouponInfo();
        adapter.setOnItemClickListener(new MyDiscountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showDialog(position);
//                sendDiscountInfo(position);
            }
        });
    }

    private void showDialog(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("优惠申请");
        alertDialogBuilder.setMessage("申请优惠需达到提款条件，确定申请吗？");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendDiscountInfo(position);
            }
        });
        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void sendDiscountInfo(int position) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String string = newList.get(position).getId();
        int id = Integer.parseInt(string);
        if (map != null) {
            map.clear();
        }
        map.put("id", id + "");

        instance.apiCouponApply(id, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                srl_discount.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                srl_discount.setRefreshing(false);
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String s = responseBody.string();
                    Log.i("globaBean", s);
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    srl_discount.setRefreshing(false);
                    if (status == 200) {
                        ToastUtil.showShort(getActivity(), "申请提交成功！");
                        getCouponInfo();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "MyDiscountListFragment");
                        } else {
                            ToastUtil.showShort(getActivity(), errorMsg);
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

    private void getCouponInfo() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiCoupon(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                srl_discount.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                srl_discount.setRefreshing(false);
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String s = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    srl_discount.setRefreshing(false);
                    if (status == 200) {
                        MyDiscountBean levelBean = new Gson().fromJson(s, MyDiscountBean.class);
                        allData = levelBean.getData();
                        setDiscountView();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginActivity.class, "MyDiscountFragment");
                        } else {
                            ToastUtil.showShort(getActivity(), errorMsg);
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

    private void setDiscountView() {
        List<MyDiscountBean.MyDiscountData.NewDiscountBean> apply = allData.getApply();
        List<MyDiscountBean.MyDiscountData.NewDiscountBean> discount = allData.getDiscount();
        List<MyDiscountBean.MyDiscountData.NewDiscountBean> invalid = allData.getInvalid();
        List<MyDiscountBean.MyDiscountData.NewDiscountBean> pass = allData.getPass();
        List<MyDiscountBean.MyDiscountData.NewDiscountBean> reject = allData.getReject();

        if ("0".equals(type)) {
            newList.clear();
            newList.addAll(discount);
            if (newList.size() == 0) {
                tv_no_info.setVisibility(View.VISIBLE);
            } else {
                tv_no_info.setVisibility(View.GONE);
            }
            adapter.setData(newList);
        } else if ("1".equals(type)) {
            discountList.clear();
            discountList.addAll(apply);
            discountList.addAll(pass);
            discountList.addAll(reject);
            if (discountList.size() == 0) {
                tv_no_info.setVisibility(View.VISIBLE);
            } else {
                callBackValue.SendCounts1(discountList.size() + "");
                tv_no_info.setVisibility(View.GONE);
            }
            adapter.setData(discountList);
        } else if ("2".equals(type)) {
            finishList.clear();
            finishList.addAll(invalid);
            if (finishList.size() == 0) {
                tv_no_info.setVisibility(View.VISIBLE);
            } else {
                callBackValue.SendCounts2(finishList.size() + "");
                tv_no_info.setVisibility(View.GONE);
            }
            adapter.setData(finishList);
        }

    }

    @Override
    public void onRefresh() {
        getCouponInfo();
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i("onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i("onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("onResume");
//        getCouponInfo();
    }


    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i("onStop");
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreate && isVisibleToUser) {
            getCouponInfo();
        }
    }

    //定义一个回调接口
    public interface CallBackValue {
        void SendCounts1(String counts);

        void SendCounts2(String counts);
    }

    //申请优惠活动
    public void applyDiscountActivity(String id) {
        String token = UserSP.getSPInstance().getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        Model.getInstance().apiCouponApply(Integer.valueOf(id), token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                if (srl_discount != null) {
                    srl_discount.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (srl_discount != null) {
                    srl_discount.setRefreshing(false);
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String s = responseBody.string();
                    Log.i("globaBean", s);
                    JSONObject jsonObject = Model.getInstance().getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (srl_discount != null) {
                        srl_discount.setRefreshing(false);
                    }
                    if (status == 200) {
                        ToastUtil.showShort(getActivity(), "申请提交成功！");
                        getCouponInfo();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            Model.getInstance().skipLoginActivity(getActivity(), LoginActivity.class, "MyDiscountListFragment");
                        } else {
                            ToastUtil.showShort(getActivity(), errorMsg);
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
}
