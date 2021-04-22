package com.ld.baselibrary.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ld.baselibrary.BuildConfig;
import com.ld.baselibrary.http.HttpClient;
import com.ld.baselibrary.util.ContextUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * @author: liangduo
 * @date: 2020/9/1 3:41 PM
 */
public class BaseApplication extends Application implements LifecycleOwner {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;

        // 对应用程序生命周期的监听
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());

        // 注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(AppManager.getAppManager());

        // 初始化工具类
        ContextUtils.init(application);

        // ARouter==== 尽可能早，推荐在Application中初始化
        if (BuildConfig.DEBUG) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);

        // 网络
        HttpClient.getInstance();

        // 日志
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("mvvm")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
