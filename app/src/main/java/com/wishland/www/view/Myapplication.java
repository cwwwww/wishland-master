package com.wishland.www.view;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.wanjian.cockroach.Cockroach;
import com.wishland.www.R;
import com.wishland.www.api.OkHttpClientManager;
import com.wishland.www.controller.base.BastRetrofit;
import com.wishland.www.model.Model;
import com.wishland.www.model.sp.UserSP;
import com.wishland.www.utils.AppUtils;
import com.wishland.www.utils.ImagePipelineConfigUtils;
import com.wishland.www.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


/**
 * Created by Administrator on 2017/4/14.
 */

public class Myapplication extends Application {
    public static Context Mcontext;
    private UserSP userSP;
    private Model instance;
    public Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        Mcontext = getApplicationContext();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        initUmeng();
        initSSL();
        initError();
        initGlide();
        initFresco();
        initAppUtils();
        //程序异常捕获处理
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            Toast.makeText(Mcontext, "Exception Happend\n" + thread + "\n" + throwable.toString(),
                                    Toast.LENGTH_LONG).show();
                            AppUtils.getInstance().onCrash((Exception) throwable);
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }

    private void initGlide() {
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(BastRetrofit.getOkhttp()));
    }

    private void initSSL() {
        try {
            OkHttpClientManager.getInstance().setCertificates(getAssets().open("v6693.cer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAppUtils() {
        AppUtils.getInstance().init(getBaseContext());
    }

    private void initFresco() {
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, BastRetrofit.getOkhttp()).build();
        Fresco.initialize(this, config);
//        Fresco.initialize(this, ImagePipelineConfigUtils.getDefaultImagePipelineConfig(this));

    }

    private void initError() {
        CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
    }

    private void initUmeng() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        //动态设置
        // MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, BuildConfig.APPLICATION_ID, BuildConfig.FLAVOR));
        // sdk关闭通知声音
        //mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtil.e("deviceToken:" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtil.e("deviceToken_" + "error:" + s + s1);
            }
        });

        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            /**
             * 自定义消息的回调方法
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        LogUtil.e("umeng_custom:" + msg.custom);
                    }
                });
            }

            /**
             * 自定义通知栏样式的回调方法
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.upush_notification);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon1, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_large_icon2, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                LogUtil.e("umeng_custom:" + msg.custom);
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html

        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    public static void onUMEvent(Context context, String id, HashMap<String, String> m, int value) {
        m.put("__ct__", String.valueOf(value));
        MobclickAgent.onEvent(context, id, m);
        //MobclickAgent.onEventValue(context, id, m, value);
    }


//    final Handler handler1 = new Handler(){
//        public void handleMessage(Message msg){         // handle message
//            switch (msg.what) {
//                case 1:
//                    requestUserStatus();
//                    if (andInt==false){
//                        handler1.removeMessages(1);
//                    }
//                    Message message = handler1.obtainMessage(1);
//                    handler1.sendMessageDelayed(message, 30000);     //发送message , 这样消息就能循环发送
//            }
//            super.handleMessage(msg);
//        }
//    };

//        /**
//         * 验证用户状态,暂时用请求消息来做验证处理
//         */
//    private void requestUserStatus() {
//        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
//        instance.apiAccount(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>()  {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.e("消息页面请求完成");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e("消息页面请求失败" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody message) {
//                        try {
//                            String string = message.string();
//                            LogUtil.e("test 获取用户状态 " + string);
//                            JSONObject jsonObject = instance.getJsonObject(string);
//                            instance.setToken_SP(jsonObject.optString("token"));
//                            int status = jsonObject.optInt("status");
//                            if (status == 200) {
//                                Myapplication.andInt = true;
//                            } else {
//                                LogUtil.e("test andInt");
//                                Myapplication.andInt = false;
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        );
//    }

}

