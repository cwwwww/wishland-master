package com.shellapp.h5shell;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by admin on 2017/10/13.
 */

@RuntimePermissions
public class WelcomeActivity extends AppCompatActivity {
    private String baseUrl = "";
    private Timer timer;
    private TimerTask timerTask;
    private int recLen = 2;
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (recLen < 1) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        if (timerTask != null) {
                            timerTask.cancel();
                        }
                        baseUrl = getString(R.string.AliUrl);
                        Intent intent = new Intent(WelcomeActivity.this, DetailsHtmlPageActivityNew.class);
                        intent.putExtra("url", baseUrl);
                        startActivity(intent);
                        finish();
                    }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否有网络
        if (!JudgeNewWorkUtil.isNetworkAvailable(this)) {
            setContentView(R.layout.view_nonetwork);
            ToastUtil.showUI(this, "网络异常,请检查设置！");
            return;
        }
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        WelcomeActivityPermissionsDispatcher.doCallWithCheck(this);
    }

    private void init() {
        setTimer();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void doCall() {
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDialog(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void refuse() {
        init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}