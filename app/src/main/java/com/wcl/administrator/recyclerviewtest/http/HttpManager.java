package com.wcl.administrator.recyclerviewtest.http;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wcl.administrator.recyclerviewtest.ApiService;
import com.wcl.administrator.recyclerviewtest.activity.App;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Created by Administrator on 2021/4/2.
 * 邮箱：
 */
public class HttpManager {
    static String BASE_URL = "https://www.wanandroid.com";
    private Retrofit mRetrofit;
    private static ApiService apiService;
    private static HttpManager instance = null;
    private PersistentCookieJar cookieJar;

    public static HttpManager getInstance() {
        if (instance == null) {
            instance = new HttpManager();
        }
        return instance;
    }

    public ApiService HttpManager() {
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));
        // init okhttp 3 logger
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .cookieJar(cookieJar)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = mRetrofit.create(ApiService.class);
        return apiService;
    }

    /**
     * 设置是否保存Cookies
     *
     * @param isSaveCookies
     */
    public ApiService setCookies(boolean isSaveCookies) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(55, TimeUnit.SECONDS)
                .addInterceptor(new ReadCookieInterceptor())
                .addInterceptor(new WriteCookieInterceptor(isSaveCookies));

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = mRetrofit.create(ApiService.class);
        return apiService;
    }

    public class ReadCookieInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            Request.Builder builder = chain.request().newBuilder();
            long expire = SPUtils.getInstance("cookie").getLong("cookie_expire", 0);
            if (expire > System.currentTimeMillis()) {
                String cookies = SPUtils.getInstance("cookie").getString("user");
                if (!TextUtils.isEmpty(cookies)) {
                    for (String cookie : cookies.split(";")) {
                        builder.addHeader("Cookie", cookie);
                    }
                    return chain.proceed(builder.build());
                }
            }
            return response;
        }
    }

    public class WriteCookieInterceptor implements Interceptor {
        private boolean mSaveCookie;

        public WriteCookieInterceptor(boolean saveCookie) {
            this.mSaveCookie = saveCookie;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (mSaveCookie) {
                List<String> headers = response.headers("Set-Cookie");
                if (!headers.isEmpty()) {
                    final StringBuilder sb = new StringBuilder();
                    headers.stream().forEach(new Consumer<String>() {
                        @Override
                        public void accept(String s) {
                            sb.append(s).append(";");
                        }
                    });
                    SPUtils.getInstance("cookie").put("user", sb.toString());
                    SPUtils.getInstance("cookie").put("cookie_expire", System.currentTimeMillis() + 30 * 24 * 3600 * 1000L);
                }
            }
            return response;
        }
    }
}
