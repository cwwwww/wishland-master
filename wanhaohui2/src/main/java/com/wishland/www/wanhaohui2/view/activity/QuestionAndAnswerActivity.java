package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.base.BaseStyleActivity;
import com.wishland.www.wanhaohui2.bean.QuestionBean;
import com.wishland.www.wanhaohui2.model.Model;
import com.wishland.www.wanhaohui2.model.UserSP;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.NetUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.adapter.QuestionAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class QuestionAndAnswerActivity extends BaseStyleActivity {

    @BindView(R.id.rv_question_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_question)
    TextView tv_question;
    @BindView(R.id.tv_game_tz)
    TextView tv_game_tz;
    @BindView(R.id.tv_jishu)
    TextView tv_jishu;

    private Model instance;
    private UserSP userSP;
    private QuestionAdapter questionAdapter;
    private List<QuestionBean.QuestionData.ContentBean> list;
    private int type = 0;
    private List<QuestionBean.QuestionData> data;


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initDate() {
        list = new ArrayList<>();
        data = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionAdapter = new QuestionAdapter(this);
        recyclerView.setAdapter(questionAdapter);
        getQuestionList();

    }


    @Override
    protected void initView() {
        setContentView(R.layout.activity_question_and_answer, R.layout.base_toolbar_back_btn);
        ButterKnife.bind(this);
        instance = Model.getInstance();
        userSP = instance.getUserSP();

    }

    private void getQuestionList() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiHelp(token, NetUtil.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("cww", e.getMessage());
                ToastUtil.showShort(QuestionAndAnswerActivity.this, "获取消息列表请求异常！");
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
                        QuestionBean questionBean = new Gson().fromJson(lineMoneyData, QuestionBean.class);
                        LogUtil.i(questionBean.toString());
                        data = questionBean.getData();
                        changeView();
                    } else if (status == 301 || status == 332) {
                        Intent intent = new Intent(QuestionAndAnswerActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @OnClick({R.id.tv_question, R.id.tv_game_tz, R.id.tv_jishu})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_question:
                tv_question.setBackgroundResource(R.drawable.shape_query_two);
                tv_game_tz.setBackgroundResource(R.drawable.shape_query_one);
                tv_jishu.setBackgroundResource(R.drawable.shape_query_one);
                tv_question.setTextColor(ContextCompat.getColor(this, R.color.white));
                tv_game_tz.setTextColor(ContextCompat.getColor(this, R.color.text_color1));
                tv_jishu.setTextColor(ContextCompat.getColor(this, R.color.text_color1));
                type = 0;
                changeView();
                break;

            case R.id.tv_game_tz:
                tv_question.setBackgroundResource(R.drawable.shape_query_one);
                tv_game_tz.setBackgroundResource(R.drawable.shape_query_two);
                tv_jishu.setBackgroundResource(R.drawable.shape_query_one);
                tv_question.setTextColor(ContextCompat.getColor(this, R.color.text_color1));
                tv_game_tz.setTextColor(ContextCompat.getColor(this, R.color.white));
                tv_jishu.setTextColor(ContextCompat.getColor(this, R.color.text_color1));
                type = 1;
                changeView();
                break;

            case R.id.tv_jishu:
                tv_question.setBackgroundResource(R.drawable.shape_query_one);
                tv_game_tz.setBackgroundResource(R.drawable.shape_query_one);
                tv_jishu.setBackgroundResource(R.drawable.shape_query_two);
                tv_question.setTextColor(ContextCompat.getColor(this, R.color.text_color1));
                tv_game_tz.setTextColor(ContextCompat.getColor(this, R.color.text_color1));
                tv_jishu.setTextColor(ContextCompat.getColor(this, R.color.white));
                type = 2;
                changeView();
                break;

        }
    }

    private void changeView() {
        if (type == 0) {
            questionAdapter.setData(data.get(0).getContent());
        } else if (type == 1) {
            questionAdapter.setData(data.get(1).getContent());
        } else {
            questionAdapter.setData(data.get(2).getContent());
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
}
