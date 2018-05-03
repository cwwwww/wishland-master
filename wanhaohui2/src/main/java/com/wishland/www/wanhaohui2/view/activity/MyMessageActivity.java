package com.wishland.www.wanhaohui2.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemClickListener;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.MessageBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.MyMessageRefreshAdapter;
import com.wishland.www.wanhaohui2.view.customlayout.refreshfootlayout.RefreshFooterLayout;

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
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by admin on 2017/10/10.
 */

public class MyMessageActivity extends BaseStyleActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_my_message)
    RecyclerView rv_my_message;
    @BindView(R.id.srl_my_message)
    SwipeRefreshLayout srl_my_message;
    @BindView(R.id.tv_no_info)
    TextView tv_no_info;
    @BindView(R.id.cb_check_all)
    CheckBox cb_check_all;
    @BindView(R.id.rl_change_view)
    RelativeLayout rl_change_view;

    private MyMessageRefreshAdapter adapter;
    private List<MessageBean.DataBean.DataListBean> myMessageBeanList = new ArrayList<>();
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map = new HashMap<>();
    private Map<String, String> map2 = new HashMap<>();
    private TextView tv_btn;
    private LinearLayoutManager linearLayoutManager;
    private int queryId = 0;
    private int queryCnt = 500;
    private RefreshFooterLayout mFooterView;
    private View loadMoreView;
    private int IS_LOAD_MORE = 0;
    private int IS_REFARSH = 1;
    private String msgIds = "";
    private boolean flag = true;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        setTitle("我的消息");
        setTvTitle("编辑");
        getMyMessageData(IS_REFARSH);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_message, R.layout.base_toolbar_back_btn);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        ButterKnife.bind(this);

        instance = Model.getInstance();
        userSP = instance.getUserSP();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_my_message.setItemAnimator(new DefaultItemAnimator());
        rv_my_message.setLayoutManager(linearLayoutManager);

        adapter = new MyMessageRefreshAdapter(this, null, true);
        //初始化 开始加载更多的loading View
//        adapter.setLoadingView(R.layout.item_foot);
//        adapter.setOnItemClickListener(new OnItemClickListener<MessageBean.DataBean.DataListBean>() {
//            @Override
//            public void onItemClick(ViewHolder viewHolder, MessageBean.DataBean.DataListBean dataListBean, int position) {
//                Intent intent = new Intent(MyMessageActivity.this, MessageDetailActivity.class);
//                MessageBean.DataBean.DataListBean data = myMessageBeanList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("message", data);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        //设置加载更多触发的事件监听
//        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(boolean b) {
//                loadMore();
//            }
//        });
//        adapter.delateItemListener(new MyMessageRefreshAdapter.DelateItemListener() {
//            @Override
//            public void delateItemClick(View view, int position) {
//                showDelateDialog(position);
//            }
//        });

        adapter.setCheckInterface(new MyMessageRefreshAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {
                myMessageBeanList.get(position).setChoosed(isChecked);
                if (isAllCheck()) {
                    cb_check_all.setChecked(true);
                } else {
                    cb_check_all.setChecked(false);
                }
            }
        });
        rv_my_message.setAdapter(adapter);

        srl_my_message.setOnRefreshListener(this);
        srl_my_message.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        adapter.setOnItemClickListener(new MyMessageAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(MyMessageActivity.this, MessageDetailActivity.class);
//                MessageBean.DataBean.DataListBean data = myMessageBeanList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("message", data);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                //                if (!"0".equals(myMessageBeanList.get(position).getIsNew() + "")) {
////                    //消息为未读时，请求已读接口
////                    readMessage(myMessageBeanList.get(position).getMsgId() + "");
////                }
//            }
//        });


    }

    private void loadMore() {
        queryId = queryCnt + queryId + 1;
        getMyMessageData(IS_LOAD_MORE);
    }


    private void getMyMessageData(final int type) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");
        instance.apiMessage(queryCnt + "", queryId + "", token, NetUtil.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                srl_my_message.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(MyMessageActivity.this, "获取消息列表请求异常！");
                srl_my_message.setRefreshing(false);
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
                        msgIds = "";
                        JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("dataList");
                        if (jsonArray == null) {
                            myMessageBeanList.clear();
                            adapter.setNewData(myMessageBeanList);
                            tv_no_info.setVisibility(View.VISIBLE);
//                            if (myMessageBeanList.size() == 0) {
//                                tv_no_info.setVisibility(View.VISIBLE);
//                            } else {
//                                //加载完成，更新footer view提示
//                                adapter.setLoadEndView(R.layout.item_foot_end);
//                            }
                        } else {
                            tv_no_info.setVisibility(View.GONE);
                            MessageBean messageBean = new Gson().fromJson(lineMoneyData, MessageBean.class);
                            List<MessageBean.DataBean.DataListBean> dataList = messageBean.getData().getDataList();
                            if (type == IS_LOAD_MORE) {
                                myMessageBeanList.addAll(dataList);
                                adapter.setLoadMoreData(myMessageBeanList);
                            } else {
                                myMessageBeanList.clear();
                                myMessageBeanList.addAll(dataList);
                                adapter.setNewData(myMessageBeanList);
                                cb_check_all.setChecked(false);
                            }
                        }
                    } else if (status == 301 || status == 332) {
                        Intent intent = new Intent(MyMessageActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        ToastUtil.showShort(MyMessageActivity.this, "获取消息列表请求异常！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void showDelateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyMessageActivity.this);
        alertDialogBuilder.setMessage("确定删除选中的消息吗？");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                statistics();
                delateItemData();
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

    private void showReadAllMsg() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyMessageActivity.this);
        alertDialogBuilder.setMessage("确定标记所有消息为已读吗？");
        alertDialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                readAllMsg();
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

    private void readAllMsg() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("Msgid", "0");

        instance.apiReadMessage("0", token, NetUtil.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("cww", e.getMessage());
                        ToastUtil.showShort(MyMessageActivity.this, "读取消息请求异常！");
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
                                ToastUtil.showShort(MyMessageActivity.this, "标记成功");
                                getMyMessageData(IS_REFARSH);
                                tv_btn.performClick();
                            } else {
                                ToastUtil.showShort(MyMessageActivity.this, "读取消息请求异常！");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void delateItemData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map2.size() != 0) {
            map2.clear();
        }
        map2.put("msgid", msgIds);
        instance.apiDelMessage(msgIds, token, NetUtil.getParamsPro(map2).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(MyMessageActivity.this, "删除消息请求异常！");
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
                        getMyMessageData(IS_REFARSH);
                    } else {
                        ToastUtil.showShort(MyMessageActivity.this, "删除消息请求异常！");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @OnClick({R.id.cb_check_all, R.id.tv_read_all, R.id.tv_delete_item, R.id.tv_btn})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.cb_check_all:
                if (myMessageBeanList.size() != 0) {
                    if (cb_check_all.isChecked()) {
                        for (int i = 0; i < myMessageBeanList.size(); i++) {
                            myMessageBeanList.get(i).setChoosed(true);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < myMessageBeanList.size(); i++) {
                            myMessageBeanList.get(i).setChoosed(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                break;

            case R.id.tv_read_all:
                if (myMessageBeanList.size() != 0) {
                    showReadAllMsg();
                } else {
                    ToastUtil.showShort(MyMessageActivity.this, "您没有消息可标记");
                }

                break;

            case R.id.tv_delete_item:
                if (isAllNotCheck()) {
                    showDelateDialog();
                } else {
                    ToastUtil.showShort(MyMessageActivity.this, "请选中要删除的消息");
                }
                break;

            case R.id.tv_btn:
                flag = !flag;
                cleanView();
                if (flag) {
                    tv_btn.setText("编辑");
                    adapter.isShow(false);
                    rl_change_view.setVisibility(View.GONE);
                } else {
                    tv_btn.setText("完成");
                    adapter.isShow(true);
                    rl_change_view.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void cleanView() {
        cb_check_all.setChecked(false);
        msgIds = "";
    }

    @Override
    public void onResume() {
        super.onResume();

//        getMyMessageData(IS_REFARSH);
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    //    private void createLoadMoreView() {
//        loadMoreView = LayoutInflater
//                .from(MyMessageActivity.this)
//                .inflate(R.layout.item_foot, rv_my_message, false);
//        adapter.addFooterView(loadMoreView);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onRefresh() {
        queryId = 0;
        getMyMessageData(IS_REFARSH);
    }


    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (MessageBean.DataBean.DataListBean group : myMessageBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllNotCheck() {

        for (MessageBean.DataBean.DataListBean group : myMessageBeanList) {
            if (group.isChoosed())
                return true;
        }
        return false;
    }

    public void statistics() {
        for (int i = 0; i < myMessageBeanList.size(); i++) {
            MessageBean.DataBean.DataListBean dataListBean = myMessageBeanList.get(i);
            if (dataListBean.isChoosed()) {
                if ("".equals(msgIds)) {
                    msgIds = dataListBean.getMsgId() + "";
                } else {
                    msgIds = msgIds + "," + dataListBean.getMsgId();
                }

            }
        }
    }


}
