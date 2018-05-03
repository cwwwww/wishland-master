package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.CaipiaoBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.FrescoUtil;
import com.wishland.www.wanhaohui2.view.fragment.LottoFirstFragment;
import com.wishland.www.wanhaohui2.view.fragment.LottoSecondFragment;
import com.wishland.www.wanhaohui2.view.fragment.LottoThirdFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/10/10.
 */

public class LottoDeluxeActivity extends BaseStyleActivity {
    @BindView(R.id.fl_game)
    FrameLayout flGame;
    LottoFirstFragment lottoFirstFragment;
    LottoSecondFragment lottoSecondFragment;
    LottoThirdFragment lottoThirdFragment;
    @BindView(R.id.sdv_whh)
    SimpleDraweeView sdvWHH;
    @BindView(R.id.tv_whh)
    TextView tvWhh;
    @BindView(R.id.sdv_ig)
    SimpleDraweeView sdvIG;
    @BindView(R.id.tv_ig)
    TextView tvIg;
    @BindView(R.id.sdv_vr)
    SimpleDraweeView sdvVR;
    @BindView(R.id.tv_vr)
    TextView tvVr;
    private FragmentTransaction fragmentTransaction;
    private String data;
    private CaipiaoBean caipiaoBean;
    private Model instance;
    private UserSP userSP;


    @Override
    protected void initVariable() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        caipiaoBean = new Gson().fromJson(data, CaipiaoBean.class);
    }

    private void setViewData() {
        FrescoUtil.loadGifPicOnNet(sdvWHH, caipiaoBean.getItems().get(0).getImg());
        FrescoUtil.loadGifPicOnNet(sdvIG, caipiaoBean.getItems().get(1).getImg());
        FrescoUtil.loadGifPicOnNet(sdvVR, caipiaoBean.getItems().get(2).getImg());

        tvWhh.setText(caipiaoBean.getItems().get(0).getName());
        tvIg.setText(caipiaoBean.getItems().get(1).getName());
        tvVr.setText(caipiaoBean.getItems().get(2).getName());
    }

    @Override
    protected void initDate() {
        setTitle("彩票游戏");
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_lottso_deluxe, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        setViewData();
        showFragment(R.id.ll_whh);
    }

    @OnClick({R.id.ll_whh, R.id.ll_ig, R.id.ll_vr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_whh:
                showFragment(R.id.ll_whh);
                break;
            case R.id.ll_ig:
                showFragment(R.id.ll_ig);
                break;
            case R.id.ll_vr:
                showFragment(R.id.ll_vr);
                break;
        }
    }

    private void showFragment(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment();
        switch (id) {
            case R.id.ll_whh: {
                if (lottoFirstFragment == null) {
                    CaipiaoBean.ItemsBean itemsBean = caipiaoBean.getItems().get(0);
                    lottoFirstFragment = new LottoFirstFragment();
                    lottoFirstFragment.setItemsBean(itemsBean, this, userSP.getToken(UserSP.LOGIN_TOKEN), instance, userSP);
                    fragmentTransaction.add(R.id.fl_game, lottoFirstFragment);
                }
                fragmentTransaction.show(lottoFirstFragment);
                fragmentTransaction.commit();
            }
            break;
            case R.id.ll_ig: {
                if (lottoSecondFragment == null) {
                    CaipiaoBean.ItemsBean itemsBean = caipiaoBean.getItems().get(1);
                    lottoSecondFragment = new LottoSecondFragment();
                    lottoSecondFragment.setItemsBean(itemsBean, this, userSP.getToken(UserSP.LOGIN_TOKEN), instance, userSP);
                    fragmentTransaction.add(R.id.fl_game, lottoSecondFragment);
                }
                fragmentTransaction.show(lottoSecondFragment);
                fragmentTransaction.commit();
            }
            break;
            case R.id.ll_vr: {
                if (lottoThirdFragment == null) {
                    CaipiaoBean.ItemsBean itemsBean = caipiaoBean.getItems().get(2);
                    lottoThirdFragment = new LottoThirdFragment();
                    lottoThirdFragment.setItemsBean(itemsBean, this, userSP.getToken(UserSP.LOGIN_TOKEN), instance, userSP);
                    fragmentTransaction.add(R.id.fl_game, lottoThirdFragment);
                }
                fragmentTransaction.show(lottoThirdFragment);
                fragmentTransaction.commit();

            }
            break;
        }

    }


    private void hideAllFragment() {
        if (lottoFirstFragment != null) {
            fragmentTransaction.hide(lottoFirstFragment);
        }
        if (lottoSecondFragment != null) {
            fragmentTransaction.hide(lottoSecondFragment);
        }
        if (lottoThirdFragment != null) {
            fragmentTransaction.hide(lottoThirdFragment);
        }
    }

    @Override
    public void finish() {
        if (lottoFirstFragment != null) {
            if (lottoFirstFragment.isNeedRefresh()) {
                setResult(1);
                super.finish();
            }
        }
        if (lottoSecondFragment != null) {
            if (lottoSecondFragment.isNeedRefresh()) {
                setResult(1);
                super.finish();
            }
        }
        if (lottoThirdFragment != null) {
            if (lottoThirdFragment.isNeedRefresh()) {
                setResult(1);
                super.finish();
            }
        }
        super.finish();
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
