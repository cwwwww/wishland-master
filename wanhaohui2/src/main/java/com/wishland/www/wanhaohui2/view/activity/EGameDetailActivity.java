package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.base.SuperAdapter;
import com.wishland.www.wanhaohui2.bean.HomeGameBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.utils.WebUtil;
import com.wishland.www.wanhaohui2.view.adapter.EGameAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/10/10.
 */

public class EGameDetailActivity extends BaseStyleActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_filter_data)
    LinearLayout llFilterData;
    @BindView(R.id.ll_background)
    LinearLayout llBackground;
    @BindView(R.id.ll_filter)
    LinearLayout llFilter;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
    @BindView(R.id.ll_checkbox)
    LinearLayout mLlCheckBox;
    private EGameAdapter eGameAdapter;
    private GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
    private HomeGameBean.DataBean.GameBean gameBean;
    private String data;
    private Model model;
    private String search;
    private String plat = "";
    private List<String> platList = new ArrayList<>();
    private int llFilterWidth = 0;
    private int llFilterHeight = 0;

    @Override
    protected void initVariable() {
        model = Model.getInstance();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        gameBean = new Gson().fromJson(data, HomeGameBean.DataBean.GameBean.class);
        eGameAdapter = new EGameAdapter(this);
    }

    @Override
    protected void initDate() {
        setTitle("电子游艺");
        eGameAdapter.setData(gameBean.getItems());
        eGameAdapter.setOnItemClickListener(new SuperAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (model.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    if (BaseApi.GAME_MODEL_OPEN && !gameBean.getItems().get(position).isTrymode()) {
                        ToastUtil.showShort(EGameDetailActivity.this, "该游戏不能试玩！");
                        return;
                    }
                }
                Intent intent = new Intent(EGameDetailActivity.this, EGameListActivity.class);
                intent.putExtra("platName", gameBean.getItems().get(position).getName());
                intent.putExtra("plat", gameBean.getItems().get(position).getKey());
                startActivityForResult(intent, 0);
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(eGameAdapter);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_egame_detail, R.layout.base_toolbar_back);
        ButterKnife.bind(this);
        initFilterView();
    }

    private void initFilterView() {
        ViewTreeObserver vto = llFilterData.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    llFilterData.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    llFilterWidth = llFilterData.getMeasuredWidth();
                    llFilterHeight = llFilterData.getMeasuredHeight();
                }
                LinearLayout llCheckBox = null;
                for (int i = 0; i < gameBean.getItems().size(); i++) {
                    if (i % 3 == 0) {
                        llCheckBox = new LinearLayout(EGameDetailActivity.this);
                        llCheckBox.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        llCheckBox.setLayoutParams(lp);
                        mLlCheckBox.addView(llCheckBox);
                    }
                    final CheckBox cb = new CheckBox(EGameDetailActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(llFilterWidth / 3 - 40, llFilterHeight / (gameBean.getItems().size() / 3));
                    lp.setMargins(20, 25, 20, 25);
                    cb.setLayoutParams(lp);
                    cb.setGravity(Gravity.CENTER);
                    cb.setText(gameBean.getItems().get(i).getName().replace("电子游艺", ""));
                    cb.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cb.setTextColor(getResources().getColor(R.color.text_color1));
                    final int finalI = i;
                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            String key = gameBean.getItems().get(finalI).getKey();
                            if (b) {
                                cb.setTextColor(getResources().getColor(R.color.blue2));
                                if (!platList.contains(key)) {
                                    platList.add(key);
                                }
                            } else {
                                cb.setTextColor(getResources().getColor(R.color.text_color1));
                                if (platList.contains(key)) {
                                    platList.remove(key);
                                }
                            }
                        }
                    });

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        cb.setBackground(ContextCompat.getDrawable(EGameDetailActivity.this, R.drawable.cb_egame_selector)
                        );

                    }
                    if (llCheckBox != null) {
                        llCheckBox.addView(cb);
                    }
                }
                llFilterData.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void finish() {
        if (isNeedRefresh) {
            setResult(1);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isNeedRefresh = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            isNeedRefresh = true;
        }
    }

    @OnClick({R.id.ll_filter, R.id.iv_search, R.id.bt_search, R.id.ll_background})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_filter: {
                if (llFilterData.getVisibility() == View.VISIBLE) {
                    ivFilter.setImageDrawable(ContextCompat.getDrawable(EGameDetailActivity.this, R.drawable.ic_flod));

                    llFilterData.setVisibility(View.GONE);
                    llBackground.setVisibility(View.GONE);
                } else {
                    ivFilter.setImageDrawable(ContextCompat.getDrawable(EGameDetailActivity.this, R.drawable.ic_less));
                    llBackground.setClickable(true);
                    llFilterData.setClickable(true);
                    llBackground.setVisibility(View.VISIBLE);
                    llFilterData.setVisibility(View.VISIBLE);
                }
            }
            break;
            case R.id.iv_search:
            case R.id.bt_search: {
//                if (model.getUserSP().getInt(UserSP.LOGIN_SUCCESS) == -1) {
//                    if (BaseApi.GAME_MODEL_OPEN) {
//                        ToastUtil.showShort(EGameDetailActivity.this, "处于试玩模式，不能筛选游戏！");
//                        return;
//                    }
//                }
                initPlat();
                search = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(plat)) {
                    ToastUtil.showShort(EGameDetailActivity.this, "请选择游戏平台！");
                    return;
                }
                if (TextUtils.isEmpty(search)) {
                    ToastUtil.showShort(EGameDetailActivity.this, "请输入游戏名称！");
                    return;
                }
                Intent intent = new Intent(EGameDetailActivity.this, EGameFilterActivity.class);
                intent.putExtra("plat", plat);
                intent.putExtra("search", search);
                startActivityForResult(intent, 0);
            }
            break;
            case R.id.ll_background: {
                ivFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_flod));
                llFilterData.setVisibility(View.GONE);
                llBackground.setVisibility(View.GONE);
            }
            break;
        }
    }

    private void initPlat() {
        plat = "";
        if (platList.size() == 1) {
            if (TextUtils.isEmpty(plat)) {
                plat = platList.get(0);
            } else {
                plat += "," + platList.get(0);
            }
        } else if (platList.size() > 1) {
            if (!TextUtils.isEmpty(plat)) {
                plat += ",";
            }
            for (int i = 0; i < platList.size() - 1; i++) {
                plat += platList.get(i) + ",";
            }
            plat += platList.get(platList.size() - 1);
        }
    }
}
