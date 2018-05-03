package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.BaseActivity;
import com.wishland.www.wanhaohui2.utils.FileUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/12/22.
 */

public class StartUpActivity extends BaseActivity {

    @BindView(R.id.iv_start_up)
    ImageView ivStartUp;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private Timer timer;
    private TimerTask timerTask;
    private int recLen = 4;
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvTime.setText("跳过 " + recLen);
                    if (recLen < 1) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        startActivity(new Intent(StartUpActivity.this, MainActivity.class));
                        finish();
                    }
            }
        }
    };


    @Override
    protected void initVariable() {
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_start_up);
        ButterKnife.bind(this);
        ivStartUp.setImageBitmap(FileUtil.getLocalBitmap(BaseApi.IMAGE_URL + "ad.jpg"));
        setTimer();
    }


    private void setTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }


    @OnClick(R.id.tv_time)
    public void onViewClicked() {
        if (timer != null) {
            timer.cancel();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
        startActivity(new Intent(StartUpActivity.this, MainActivity.class));
        finish();
    }
}
