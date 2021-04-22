package com.ld.baselibrary.base;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author: liangduo
 * @date: 2021/3/2 4:24 PM
 * ApplicationObserver类，我们定义这个类来实现LifecycleObserver接口，负责对应用程序生命周期的监听
 *
 * 当应用程序从后台回到前台，或者应用程序首次打开，
 * 会依次调用Lifecycle.Event.ON_START，Lifecycle.Event.ON_RESUME。
 * 当应用程序从前台退到后台（用户按下home键或任务菜单键），
 * 会依次调用Lifecycle.Event.ON_PAUSE，Lifecycle.Event.ON_STOP。
 */
public class ApplicationObserver implements LifecycleObserver {
    private String TAG = this.getClass().getName();

    public ApplicationObserver() {
    }


    /**
     * ON_CREATE 在应用程序的整个生命周期中只会被调用一次
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate()
    {
        Log.d(TAG, "Lifecycle.Event.ON_CREATE");
    }

    /**
     * 应用程序出现到前台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart()
    {
        Log.d(TAG, "Lifecycle.Event.ON_START");
    }

    /**
     * 应用程序出现到前台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume()
    {
        Log.d(TAG, "Lifecycle.Event.ON_RESUME");
    }

    /**
     * 应用程序退出到后台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause()
    {
        Log.d(TAG, "Lifecycle.Event.ON_PAUSE");
    }

    /**
     * 应用程序退出到后台时调用
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop()
    {
        Log.d(TAG, "Lifecycle.Event.ON_STOP");
    }

    /**
     * 永远不会被调用到，系统不会分发调用ON_DESTROY事件
     * */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy()
    {
        Log.d(TAG, "Lifecycle.Event.ON_DESTROY");
    }
}
