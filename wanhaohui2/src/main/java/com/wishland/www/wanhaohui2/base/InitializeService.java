package com.wishland.www.wanhaohui2.base;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.wanjian.cockroach.Cockroach;
import com.wishland.www.wanhaohui2.api.BaseRetrofit;
import com.wishland.www.wanhaohui2.utils.LogUtil;

import java.io.InputStream;

/**
 * Created by admin on 2017/11/23.
 */

public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.anly.githubapp.service.action.INIT";
    public static Context baseContext;

    private static int DISK_CACHE_SIZE_HIGH = 1024 * 1024 * 10;
    private static int DISK_CACHE_SIZE_LOW = 1024 * 1024;
    private static int DISK_CACHE_SIZE_VERY_LOW = 1024;

    public InitializeService() {
        super("InitializeService");
        baseContext = getApplicationContext();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        LogUtil.d("performInit begin:" + System.currentTimeMillis());

        initGlide();
        initCockroach();
        initFresco();

        LogUtil.d("performInit end:" + System.currentTimeMillis());
    }

    private void initGlide() {
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(BaseRetrofit.getOkHttp()));
    }

    private void initCockroach() {
        Cockroach.install(new Cockroach.ExceptionHandler() {
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime", "--->CockroachException:" + thread + "<---", throwable);
                            Toast.makeText(baseContext, "Exception Happend\n" + thread + "\n" + throwable.toString(),
                                    Toast.LENGTH_LONG).show();
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }

    private void initFresco() {
        //缓存
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(MyApplication.baseContext)
                .setMaxCacheSize(DISK_CACHE_SIZE_HIGH)
                .setMaxCacheSizeOnLowDiskSpace(DISK_CACHE_SIZE_LOW)
                .setMaxCacheSizeOnVeryLowDiskSpace(DISK_CACHE_SIZE_VERY_LOW)
                .build();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, BaseRetrofit.getOkHttp()).setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(this, config);
    }

}
