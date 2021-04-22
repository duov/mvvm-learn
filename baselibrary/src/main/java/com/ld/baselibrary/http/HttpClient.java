package com.ld.baselibrary.http;

import android.content.Context;
import android.text.TextUtils;

import com.ld.baselibrary.BuildConfig;
import com.ld.baselibrary.http.interceptor.BaseInterceptor;
import com.ld.baselibrary.http.interceptor.CacheInterceptor;
import com.ld.baselibrary.http.interceptor.HeaderInterceptor;
import com.ld.baselibrary.http.logging.Level;
import com.ld.baselibrary.http.logging.LoggingInterceptor;
import com.ld.baselibrary.http.ssl.HttpsUtils;
import com.ld.baselibrary.util.ContextUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 * @author: liangduo
 * @date: 2020/9/1 1:57 PM
 */
public class HttpClient {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 30;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;
    //服务端根路径
    public static String baseUrl = "https://www.dxzjjl.com.cn";

    private static Context mContext = ContextUtils.getContext();

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    //缓存对象
    private Cache cache = null;
    //缓存存放的文件
    private File httpCacheDirectory;

    private static class SingletonHolder {
        private static HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HttpClient() {
        this(baseUrl);
    }

    private HttpClient(String url) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            //缓存存放的文件
            httpCacheDirectory = new File(mContext.getCacheDir(), "ld_cache");
        }

        try {
            if (cache == null) {
                //缓存对象
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            Logger.e("Could not create http cache" + e);
        }

        // debug 情况不校验证书
        HttpsUtils.SSLParams sslParams;
//        if (BuildConfig.DEBUG){
            sslParams = HttpsUtils.getSslSocketFactory();
//        }else {
//            sslParams = HttpsUtils.getSslSocketFactory(SslContextFactory.certificates);
//        }
        okHttpClient = new OkHttpClient.Builder()
//                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
//                .cache(cache)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new BaseInterceptor())
                .addInterceptor(new CacheInterceptor(mContext))
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> T execute(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        return null;
    }

}
