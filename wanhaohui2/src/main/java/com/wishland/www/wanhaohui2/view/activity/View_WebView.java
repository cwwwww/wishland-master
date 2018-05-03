package com.wishland.www.wanhaohui2.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wishland.www.wanhaohui2.base.MyApplication;
import com.wishland.www.wanhaohui2.model.AntiHijackingUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JayCruz on 2017/9/21.
 */

public class View_WebView extends WebView {
    Context context;
    private ProgressBar pb;
    private View errorPage;
    private boolean isPageError;
    private int currentProgress;
    private Map<Integer, Integer> index = new HashMap<>();

    public View_WebView(Context context) {
        super(context);
        setWebViewDefaults();
        this.context = context;

    }

    public View_WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWebViewDefaults();
        this.context = context;

    }

    /***
     * 设定Activity ErrorPage
     * @param errorPage
     */
    public void setErrorPage(View errorPage, boolean isPageError) {
        this.errorPage = errorPage;
        this.isPageError = isPageError;

    }

    /***
     * 设定Activity ProgressBar
     * @param progressBar
     */
    public void setProgressBar(ProgressBar progressBar) {
        this.pb = progressBar;

    }

    /***
     * 读取 新URL
     * @param url
     */
    @Override
    public void loadUrl(String url) {
        super.loadUrl(url);
        index = new HashMap<>();
        currentProgress = 0;
    }

    /***
     * Activity完成时 清除所有记录
     */
    public void finish() {
        this.removeAllViews();
        this.setTag(null);
        this.clearHistory();
        this.destroy();
        index = new HashMap<>();
        currentProgress = 0;
    }

    /***
     * 取得当前progress
     * @return
     */
    public int getCurrentProgress() {
        return currentProgress;
    }

    /***
     * 当Activity回退 或 结束时呼叫
     */
    public void onBackPressed() {
        // By Jay 09-19 修改 回退 程序问题
        try {
            if (this.canGoBack()) {
                int currentIndex = this.copyBackForwardList().getCurrentIndex();
                if (currentIndex >= 3) {
                    this.goBackOrForward(-currentIndex);
                } else if (currentIndex == 2) {

                    this.goBackOrForward(-1);
                } else if (currentIndex >= 1) {
//                    if (currentIndex >= 1 && index.get(currentIndex) != null) {
                    /***
                     * 如果非第一层，直接回退到 第一层即可
                     */
                    this.goBackOrForward(-1);
//                    this.goBackOrForward(-currentIndex);
                } else {
                    exitApp();
//                    this.onBackPressed();
                }
            } else {
                exitApp();
            }
        } catch (Exception ex) {
            exitApp();
        }
    }


    private long lastBackTime = 0;

    public void exitApp() {
        long current = System.currentTimeMillis();
        if (current - lastBackTime > 1000) {
            Toast.makeText(context, "再按一次后退键退出应用程序", Toast.LENGTH_SHORT).show();
        } else {
            finish();
            lastBackTime = 0;
            System.exit(0);
        }
        lastBackTime = current;
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewDefaults() {
        if (errorPage != null) {
            errorPage.setVisibility(isPageError ? View.VISIBLE : View.GONE);
        }
        this.requestFocusFromTouch();
        WebSettings settings = this.getSettings();
        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //Android webview 从Lollipop开始webview默认不允许混合模式，https当中不能加载http资源，需要设置开启。
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);

//        webView.setWebContentsDebuggingEnabled(true);
        this.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        this.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorPage != null)
                    errorPage.setVisibility(View.VISIBLE);
            }

//            @TargetApi(android.os.Build.VERSION_CODES.M)
//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
//                if (errorPage != null)
//                    errorPage.setVisibility(View.VISIBLE);
//            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                if (errorPage != null)
                    errorPage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.getSettings().setBlockNetworkImage(false);
//                int currentIndex = view.copyBackForwardList().getCurrentIndex();
//                boolean b = index.containsKey(currentIndex);
//                if (b) {
//                    int i = index.get(currentIndex) + 1;
//                    index.put(currentIndex, i);
//                } else {
//                    index.put(currentIndex, 1);
//                }
            }
        });
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                currentProgress = progress;
                if (pb != null) {
                    pb.setVisibility(View.VISIBLE);
                    pb.setProgress(currentProgress);
                    if (currentProgress == 100)
                        pb.setVisibility(View.GONE);

                }
            }
        });
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.setAcceptFileSchemeCookies(true);
            cookieManager.setAcceptThirdPartyCookies(this, true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        AntiHijackingUtil.checkActivity(context);
    }
}
