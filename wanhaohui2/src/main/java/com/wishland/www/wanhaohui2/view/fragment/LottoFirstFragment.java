package com.wishland.www.wanhaohui2.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.CaipiaoBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.activity.LottoDeluxeActivity;
import com.wishland.www.wanhaohui2.view.activity.SportsEventActivity;
import com.wishland.www.wanhaohui2.view.adapter.LottoFirstAdapter1;
import com.wishland.www.wanhaohui2.view.adapter.LottoFirstAdapter2;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/10/16.
 */

public class LottoFirstFragment extends UmengFragment {

    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.tv_official)
    TextView tvOfficial;
    @BindView(R.id.tv_credit)
    TextView tvCredit;
    Unbinder unbinder;
    @BindView(R.id.recycler_view_official)
    RecyclerView recyclerViewOfficial;
    @BindView(R.id.recycler_view_credit)
    RecyclerView recyclerViewCredit;
    private LottoFirstAdapter1 lottoFirstAdapter1;
    private LottoFirstAdapter2 lottoFirstAdapter2;
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
    private GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 3);
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    emptyLayout.showLoading();
                }
                break;
                case 1: {
                    emptyLayout.hide();
                }
                break;
            }
        }
    };


    public void setItemsBean(final CaipiaoBean.ItemsBean itemsBean, final LottoDeluxeActivity lottoDeluxeActivity, final String token, final Model model, UserSP userSP) {
        lottoFirstAdapter1 = new LottoFirstAdapter1(lottoDeluxeActivity, model, userSP, mHandler);
        lottoFirstAdapter1.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String url = itemsBean.getSubgame().get(position).getPara().replace("[token]", token);
                if (model.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (!BaseApi.GAME_MODEL_OPEN) {
                        model.skipLoginActivity(getActivity(), LoginActivity.class, "LottoFirstFragment");
                    } else if (BaseApi.GAME_MODEL_OPEN && !itemsBean.isTrymode()) {
                        ToastUtil.showShort(getContext(), "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebGameActivity(
                                lottoDeluxeActivity,
                                url,
                                HomeFragment.GAME_CATEGORY.LOTTERY_GAME.getTitle(),
                                itemsBean.getName(),
                                "官方玩法_" + itemsBean.getSubgame().get(position).getName());
                    }
                } else {
                    WebUtil.toWebGameActivity(
                            lottoDeluxeActivity,
                            url,
                            HomeFragment.GAME_CATEGORY.LOTTERY_GAME.getTitle(),
                            itemsBean.getName(),
                            "官方玩法_" + itemsBean.getSubgame().get(position).getName());
                }

            }
        });
        lottoFirstAdapter1.setData(itemsBean.getSubgame());

        lottoFirstAdapter2 = new LottoFirstAdapter2(lottoDeluxeActivity, model, userSP, mHandler);
        lottoFirstAdapter2.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                String url = itemsBean.getSubcredit().get(position).getPara().replace("[token]", token);
                if (model.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (!BaseApi.GAME_MODEL_OPEN) {
                        model.skipLoginActivity(getActivity(), LoginActivity.class, "LottoFirstFragment");
                    } else if (BaseApi.GAME_MODEL_OPEN && !itemsBean.isTrymode()) {
                        ToastUtil.showShort(getContext(), "该游戏不能试玩！");
                    } else {
                        WebUtil.toWebGameActivity(
                                lottoDeluxeActivity,
                                url,
                                HomeFragment.GAME_CATEGORY.LOTTERY_GAME.getTitle(),
                                itemsBean.getName(),
                                "信用玩法_" + itemsBean.getSubcredit().get(position).getName());
                    }
                } else {
                    WebUtil.toWebGameActivity(
                            lottoDeluxeActivity,
                            url,
                            HomeFragment.GAME_CATEGORY.LOTTERY_GAME.getTitle(),
                            itemsBean.getName(),
                            "信用玩法_" + itemsBean.getSubcredit().get(position).getName());
                }
            }
        });
        lottoFirstAdapter2.setData(itemsBean.getSubcredit());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_official, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerViewOfficial.setLayoutManager(gridLayoutManager);
        recyclerViewOfficial.setAdapter(lottoFirstAdapter1);

        recyclerViewCredit.setLayoutManager(gridLayoutManager2);
        recyclerViewCredit.setAdapter(lottoFirstAdapter2);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
    }

    public boolean isNeedRefresh() {
        if (lottoFirstAdapter1 != null || lottoFirstAdapter2 != null) {
            return lottoFirstAdapter1.isNeedRefresh() || lottoFirstAdapter2.isNeedRefresh();
        }
        return false;
    }

    @OnClick({R.id.tv_official, R.id.tv_credit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_official:
                tvOfficial.setTextColor(getResources().getColor(R.color.white));
                tvOfficial.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_card));
                tvCredit.setTextColor(getResources().getColor(R.color.black));
                tvCredit.setBackgroundColor(getResources().getColor(R.color.view_color));
                recyclerViewOfficial.setVisibility(View.VISIBLE);
                recyclerViewCredit.setVisibility(View.GONE);
                break;
            case R.id.tv_credit:
                tvCredit.setTextColor(getResources().getColor(R.color.white));
                tvCredit.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_card));
                tvOfficial.setTextColor(getResources().getColor(R.color.black));
                tvOfficial.setBackgroundColor(getResources().getColor(R.color.view_color));
                recyclerViewOfficial.setVisibility(View.GONE);
                recyclerViewCredit.setVisibility(View.VISIBLE);
                break;
        }
    }
}
