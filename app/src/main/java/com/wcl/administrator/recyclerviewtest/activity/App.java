package com.wcl.administrator.recyclerviewtest.activity;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.wcl.administrator.recyclerviewtest.util.SharedPreferencesUtil;

/**
 * 作者：Created by Administrator on 2021/4/8.
 * 邮箱：
 */
public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "f2b2a4af86", false);
        instance = this;
        LoadWebX5();

        SharedPreferencesUtil.getInstance(this, "WanAndroid");
    }


    public static synchronized App getInstance() {
        return instance;
    }


    private void LoadWebX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
