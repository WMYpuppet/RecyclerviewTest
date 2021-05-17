package com.wcl.administrator.recyclerviewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wcl.administrator.recyclerviewtest.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class X5WebView extends AppCompatActivity {

    @BindView(R.id.X5WebView)
    WebView X5WebView;


    private String mUrl = "http://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5_web_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mUrl = getIntent().getStringExtra("mUrl");
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.white), 1);
        myWeb(mUrl);
    }

    private void myWeb(String url) {
        WebChromeClient webChromeClient = new WebChromeClient();
        X5WebView.setWebChromeClient(webChromeClient);
        WebSettings webSettings = X5WebView.getSettings();
        displaySetting(webSettings);
        webSetting(webSettings);
        //去除QQ浏览器推广广告
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器",
                        View.FIND_VIEWS_WITH_TEXT);
                if (outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        X5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                //这里可以对特殊scheme进行拦截处理
                X5WebView.loadUrl(s);
                //要返回true否则内核会继续处理
                return true;
            }
        });
        X5WebView.loadUrl(url);

    }

    /**
     * 屏幕适配
     */
    private void displaySetting(WebSettings webSettings) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
    }

    /**
     * WebView设置
     */
    private void webSetting(WebSettings webSettings) {
        // 保存表单数据
        webSettings.setSaveFormData(true);
        //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        X5WebView.requestFocus(); //此句可使html表单可以接收键盘输入
        X5WebView.setFocusable(true);
        webSettings.setSavePassword(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        // 启动应用缓存
        webSettings.setAppCacheEnabled(false);
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // WebView是否支持多个窗口。
        webSettings.setSupportMultipleWindows(true);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //其他细节操作

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //JS在HTML里面设置了本地存储localStorage，java中使用localStorage
        webSettings.setDomStorageEnabled(true);
        // 则必须打开
        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
        //webView.setLayerType();
        X5WebView.setDrawingCacheEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (X5WebView != null) {
            X5WebView.destroy();
        }
    }
}
