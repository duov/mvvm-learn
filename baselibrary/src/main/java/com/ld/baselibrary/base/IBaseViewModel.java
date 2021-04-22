package com.ld.baselibrary.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * （1）前景
 *
 * 在使用rxjava的时候，如果没有及时解除订阅，在退出activity的时候，异步线程还在执行。
 * 对activity还存在引用，此时就会产生内存泄漏。
 * RxLifecycle就是为了解决rxjava导致的内存泄漏而产生的。
 *
 * （2）作用
 *
 * 它可以让Observable发布的事件和当前的组件绑定，实现生命周期同步。
 * 从而实现当前组件生命周期结束时，自动取消对Observable订阅。
 * 核心思想：通过监听Activity、Fragment的生命周期，来自动断开订阅防止内存泄漏。
 *
 * @author: liangduo
 * @date: 2020/9/14 3:22 PM
 */
public interface IBaseViewModel extends LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();
}
