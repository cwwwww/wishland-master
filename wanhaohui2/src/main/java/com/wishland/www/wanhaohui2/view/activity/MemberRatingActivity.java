package com.wishland.www.wanhaohui2.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.MemberRatingBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.view.adapter.MemberRatingAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.MyDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/15.
 */

public class MemberRatingActivity extends BaseStyleActivity {
    @BindView(R.id.rv_vip_ranking)
    RecyclerView rv_vip_ranking;


    private Model instance;
    private UserSP userSP;
    private MemberRatingAdapter adapter;
    private List<MemberRatingBean.DataBean> list=new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("会员等级");
        getLeverInfo();

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_member_rating, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        instance = Model.getInstance();
        userSP = instance.getUserSP();

        rv_vip_ranking.setLayoutManager(layoutManager);
        adapter=new MemberRatingAdapter(this,this);
        rv_vip_ranking.setAdapter(adapter);
        rv_vip_ranking.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
    }

    private void getLeverInfo() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLeverList(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String s = responseBody.string();
                    Log.i("globaBean", s);
                    JSONObject jsonObject = instance.getJsonObject(s);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        MemberRatingBean levelBean = new Gson().fromJson(s, MemberRatingBean.class);
                        List<MemberRatingBean.DataBean> data = levelBean.getData();
                        list.addAll(data);
                        adapter.setData(list);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(MemberRatingActivity.this, LoginActivity.class,"MemberRatingActivity");
                        } else {
                            ToastUtil.showShort(MemberRatingActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                  //  ToastUtil.showLong(MemberRatingActivity.this, "请求数据出错！");
                } catch (JSONException e) {
                    e.printStackTrace();
                   // ToastUtil.showLong(MemberRatingActivity.this, "请求数据出错！");
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
}
