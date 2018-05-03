package com.wishland.www.wanhaohui2.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.LineMoneyDataBean;
import com.wishland.www.wanhaohui2.bean.WalletBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.BalanceDetailAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;
import com.wishland.www.wanhaohui2.view.customlayout.MyDividerItemDecoration;

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
 * Created by admin on 2017/10/9.
 */

public class BalanceDetailsActivity extends BaseStyleActivity implements SwipeRefreshLayout.OnRefreshListener {
    Unbinder unbinder;
    @BindView(R.id.rv_balance_detail_list)
    RecyclerView rv_balance_detail_list;
    @BindView(R.id.tv_account_money)
    TextView tv_account_money;
    @BindView(R.id.srl_refresh_account_money)
    SwipeRefreshLayout srl_refresh_account_money;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private BalanceDetailAdapter adapter;
    private List<LineMoneyDataBean.LineMoneyData.WalletBean> list = new ArrayList<>();
    private Model instance;
    private UserSP userSP;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("余额详情");
    }

    @Override
    public void onResume() {
        super.onResume();
        lineRequestGridData();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_balance_detail, R.layout.base_toolbar_back);
        unbinder = ButterKnife.bind(this);

        instance = Model.getInstance();
        userSP = instance.getUserSP();
        rv_balance_detail_list.setLayoutManager(new GridLayoutManager(this, 3));
        rv_balance_detail_list.addItemDecoration(new MyDividerItemDecoration(this, GridLayoutManager.VERTICAL));
        rv_balance_detail_list.addItemDecoration(new MyDividerItemDecoration(this, GridLayoutManager.HORIZONTAL));
        adapter = new BalanceDetailAdapter(this);
        rv_balance_detail_list.setAdapter(adapter);
        srl_refresh_account_money.setOnRefreshListener(this);

        adapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                lineRequestItemData(list.get(position).getWallettype(), position);
            }
        });
    }

    /**
     * 请求账户余额详情
     */
    private void lineRequestGridData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLineMoneyData(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                srl_refresh_account_money.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(BalanceDetailsActivity.this, "获取余额详情请求异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String lineMoneyData = responseBody.string();
                    LogUtil.i("linearResponse", lineMoneyData);
                    JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        list.clear();
                        LineMoneyDataBean lineMoneyDataBean = new Gson().fromJson(lineMoneyData, LineMoneyDataBean.class);
                        List<LineMoneyDataBean.LineMoneyData.WalletBean> walletList = lineMoneyDataBean.getData().getWallet();
                        list.addAll(walletList);
                        adapter.setData(list);
                        tv_account_money.setText(lineMoneyDataBean.getData().getTotal() + "");
//                        setBanner(imgList);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(BalanceDetailsActivity.this, LoginActivity.class, "BalanceDetailsActivity");
                        } else {
                            ToastUtil.showShort(BalanceDetailsActivity.this, errorMsg);
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
    public void onRefresh() {
        lineRequestGridData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void lineRequestItemData(String type, final int position) {
        Map<String, String> map = new HashMap<>();
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("type", type);
        instance.apiItem(type, token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                emptyLayout.hide();
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(BalanceDetailsActivity.this, "获取账户余额异常！");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                emptyLayout.hide();
                try {
                    String lineMoneyData = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(lineMoneyData);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        WalletBean lineMoneyDataBean = new Gson().fromJson(lineMoneyData, WalletBean.class);
                        WalletBean.WalletData data = lineMoneyDataBean.getData();
                        for (int i = 0; i < list.size(); i++) {
                            if (i == position) {
                                list.get(i).setAmout(data.getBalance());
                                adapter.setData(list);
                            }
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(BalanceDetailsActivity.this, LoginActivity.class, "LinearFragment");
                        } else {
                            ToastUtil.showShort(BalanceDetailsActivity.this, errorMsg);
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
