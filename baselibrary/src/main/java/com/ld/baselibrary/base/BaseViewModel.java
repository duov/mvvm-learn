package com.ld.baselibrary.base;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ld.baselibrary.util.TUtil;
import com.trello.rxlifecycle4.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liangduo
 * @date: 2020/9/14 1:56 PM
 */
public class BaseViewModel<M extends AbsRepository> extends AndroidViewModel implements IBaseViewModel {

    public M mRepository;

    private UIChangeLiveData uc;

    //弱引用持有---当一个对象只被弱引用实例引用（持有）时，这个对象就会被GC回收
    private WeakReference<LifecycleProvider> lifecycle;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    /**
     * 当Activity/Fragment销毁时，调用此方法
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unSubscribe();
        }
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    /* ---------- 以下 初始化数据 -------*/
    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 初始化数据
     */
    public void initData(Intent intent) {

    }

    /**
     * 初始化数据
     */
    public void initData(Bundle bundle) {
    }

    /* ---------- 以下 LifecycleObserver -------*/
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }
    /* ---------- 以上 LifecycleObserver -------*/

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startContainerActivityEvent.postValue(params);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }


    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    public final class UIChangeLiveData extends MutableLiveData {
        private MutableLiveData<String> showDialogEvent;
        private MutableLiveData<Void> dismissDialogEvent;
        private MutableLiveData<Map<String, Object>> startActivityEvent;
        private MutableLiveData<Map<String, Object>> startContainerActivityEvent;
        private MutableLiveData<Void> finishEvent;
        private MutableLiveData<Void> onBackPressedEvent;

        public MutableLiveData<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public MutableLiveData<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public MutableLiveData<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public MutableLiveData<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
        }

        public MutableLiveData<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public MutableLiveData<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private <T> MutableLiveData<T> createLiveData(MutableLiveData<T> liveData) {
            if (liveData == null) {
                liveData = new MutableLiveData<>();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String BUNDLE = "BUNDLE";
        public static String INTENT = "INTENT";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String REQUEST_CODE = "REQUEST_CODE";
    }
}
