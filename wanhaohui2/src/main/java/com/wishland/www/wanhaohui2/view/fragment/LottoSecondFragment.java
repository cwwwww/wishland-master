package com.wishland.www.wanhaohui2.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.CaipiaoBean;
import com.wishland.www.wanhaohui2.bean.MindCollectionBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.activity.LoginActivity;
import com.wishland.www.wanhaohui2.view.activity.LottoDeluxeActivity;
import com.wishland.www.wanhaohui2.view.adapter.LottoFirstAdapter1;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/10/16.
 */

public class LottoSecondFragment extends UmengFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    Unbinder unbinder;
    private LottoFirstAdapter1 lottoFirstAdapter1;
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
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
                        model.skipLoginActivity(getActivity(), LoginActivity.class, "LottoSecondFragment");
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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit, container, false);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(lottoFirstAdapter1);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHandler.removeCallbacksAndMessages(null);
    }

    public boolean isNeedRefresh() {
        if (lottoFirstAdapter1 != null) {
            return lottoFirstAdapter1.isNeedRefresh();
        }
        return false;
    }
}
