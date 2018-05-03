package com.shellapp.h5shell;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2017/10/17.
 */

public class DetailsHtmlPageActivity extends Activity {
    private String stringExtra;
    private WebView webview;
    private ProgressBar pb;
    private Map<Integer, Integer> index = new HashMap<>();
    private PopupWindow popupWindow;
    private WindowManager wm;
    private FrameLayout fl;
    private String imgUrl = "";

    //    private TextView tvTitle;
//    private ImageButton btnBack;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getWindow().setContentView(R.layout.detailspagel);

        webview = (WebView) findViewById(R.id.webview);
        pb = (ProgressBar) findViewById(R.id.pb);
        fl = (FrameLayout) findViewById(R.id.fl_main_view);
//        tvTitle = (TextView) findViewById(R.id.tv_title);
//        btnBack = (ImageButton) findViewById(R.id.btn_back);


        initStatusBar();
        init(webview);
        if (intent.getStringExtra("url").isEmpty()) {
            ToastUtil.showShort(this, "暂未开放");
        } else {
            stringExtra = intent.getStringExtra("url");
            webview.loadUrl(stringExtra);

        }

        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE) {
                    imgUrl = result.getExtra();
                    setDataPopup(imgUrl);
                }
                return false;
            }
        });
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void init(final WebView webView) {
        webview.requestFocusFromTouch();
        WebSettings settings = webView.getSettings();
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        //缩放开关，仅仅支持双击缩放
        settings.setSupportZoom(true);
        //设置是否可缩放，会出现缩放工具
        settings.setBuiltInZoomControls(true);
        //隐藏缩放工具
        settings.setDisplayZoomControls(false);
        //WebView.setWebContentsDebuggingEnabled(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(progress);
                if (progress == 100)
                    pb.setVisibility(View.GONE);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                //加这段可以证webview中的alert弹出来
                return super.onJsAlert(view, url, message, result);
            }

        });
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.setAcceptFileSchemeCookies(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        webView.setWebViewClient(setWebViewClient());
    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemNavigationBar();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 加入竖屏要处理的代码
            showSystemNavigationBar();
            initStatusBar();
        }
    }

    private void showSystemNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void hideSystemNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onBackPressed() {
        try {
            if (webview.canGoBack()) {
                int currentIndex = webview.copyBackForwardList().getCurrentIndex();
                if (currentIndex == 1 && index.get(0) > 1) {
                    finish();
                } else if (index.get(currentIndex) > 1) {
                    while (currentIndex >= 0 && index.get(currentIndex) > 0) {
                        currentIndex--;
                    }
                    webview.goBackOrForward(currentIndex - 1);
                } else {
                    webview.goBack();
                }
            } else {
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }


    private long lastBackTime = 0;

    public void exitApp() {
        long current = System.currentTimeMillis();
        if (current - lastBackTime > 1000) {
            Toast.makeText(this, "再按一次后退键退出应用程序", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            lastBackTime = 0;
            System.exit(0);
        }
        lastBackTime = current;
    }

    public static void clearCookies(Context context) {
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr =
                CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.removeAllViews();
            webview.destroy();
        }
        clearCookies(this);
        System.exit(0);

    }

    private WebViewClient setWebViewClient() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView wv, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                            || url.startsWith("mqqapi://")//QQ钱包
                        //其他自定义的scheme
                            ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        Log.e("cww", "url1" + url);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }

                //处理http和https开头的url
                Log.e("cww", "url3" + url);
                // wv.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.getSettings().setBlockNetworkImage(false);
                int currentIndex = view.copyBackForwardList().getCurrentIndex();
                boolean b = index.containsKey(currentIndex);
                if (b) {
                    int i = index.get(currentIndex) + 1;
                    index.put(currentIndex, i);
                } else {
                    index.put(currentIndex, 1);
                }
            }
        };
        return webViewClient;
    }

    public void setDataPopup(final String imgUrl) {
        final String fileName1 = UUID.randomUUID().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("点击确定保存图片");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                ToastUtil.showShort(DetailsHtmlPageActivity.this, "地图地址：" + imgUrl);
                saveImage(imgUrl, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/", fileName1 + ".png");
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void saveImage(String imgUrl, String destFileDir, String destFileName) {
        OkGo.get(imgUrl).tag(this).execute(new FileCallback(destFileDir, destFileName) {
            @Override
            public void onSuccess(File file, Call call, Response response) {
                //通知图库更新
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                DetailsHtmlPageActivity.this.sendBroadcast(intent);
                ToastUtil.showShort(DetailsHtmlPageActivity.this, "保存成功");
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showShort(DetailsHtmlPageActivity.this, "保存失败");
            }
        });
    }

    protected void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//
    }

    @Override
    protected void onPause() {
        super.onPause();
//
    }


}
