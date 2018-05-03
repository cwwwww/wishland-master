package com.wishland.www.wanhaohui2.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.wanhaohui2.R;
import com.wishland.www.wanhaohui2.bean.AppUpdateBean;

import okhttp3.Call;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";
    FrameLayout flWv;
    View_WebView webView;
    ProgressBar pb;
    private String url;
    private View bg_errorpage;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        url = getIntent().getStringExtra("url");
        WebView.setWebContentsDebuggingEnabled(true);
        checkUpdate("https://tpfw.083075.com/system/getAppLastChange");
        initView();
    }

    private void checkUpdate(String versionHost) {
        String code = getResources().getString(R.string.VersionCode);
        Log.e("cww", code);
        if (TextUtils.isEmpty(code) || TextUtils.isEmpty(versionHost)) {
            return;
        }
        OkGo.post(versionHost)
                .params("code", code)
                .execute(new AbsCallback<String>() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        final AppUpdateBean appUpdate = new Gson().fromJson(s, AppUpdateBean.class);
                        int appVersion = getAPPRealVersion();
                        int currentVersion = dealVersion(appUpdate.getEntity().getVersion());
                        if (appVersion < currentVersion) {
                            MaterialDialog.Builder builder = new MaterialDialog.Builder(WebActivity.this);
                            builder.title("发现新版本");
                            if (4 == appUpdate.getEntity().getVersionType()) {
                                builder.cancelable(false)
                                        .autoDismiss(false);
                                builder.content("重大版本更新，请下载安装新版本后继续使用");
                            } else if (3 == appUpdate.getEntity().getVersionType()) {
                                builder.negativeText("暂不升级")
                                        .negativeColor(getResources().getColor(R.color.text_hint))
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                            }
                                        });
                            }
                            builder.positiveText("点击升级")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            try {
                                                Uri uri = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next());
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }
                });
    }

    public int getAPPRealVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private int dealVersion(String s) {
        if (s.contains(".")) {
            s = s.replace(".", "");
        }
        return Integer.valueOf(s);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemNavigationBar();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 加入竖屏要处理的代码
            showSystemNavigationBar();
        }
    }

    private void showSystemNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void hideSystemNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }


    protected void initView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        if (webView == null) {
            webView = new View_WebView(this);
        }
        flWv = (FrameLayout) findViewById(R.id.fl_wv);
        flWv.addView(webView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bg_errorpage = findViewById(R.id.bg_errorpage);
        pb = (ProgressBar) findViewById(R.id.pb);
        webView.setProgressBar(pb);
        if (url != null) {
            if (!url.isEmpty()) {
                if (!(url.contains("https://") || url.contains("http://"))) {
                    url = "http://" + url;
                }
                webView.setErrorPage(bg_errorpage, false);
                webView.loadUrl(url);
            } else {
                webView.setErrorPage(bg_errorpage, true);
            }
        } else {
            webView.setErrorPage(bg_errorpage, true);
        }
    }

    @Override
    public void onBackPressed() {
        webView.onBackPressed();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.finish();
    }
}

