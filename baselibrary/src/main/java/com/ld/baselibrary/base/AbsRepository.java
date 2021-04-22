package com.ld.baselibrary.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Describe:网络请求订阅容器，方便管理网络请求
 *
 * @author: liangduo
 * @date: 2020/9/14 2:28 PM
 */
public class AbsRepository {
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    /*
     * 如果在请求过程中，UI层destroy了怎么办，不及时取消订阅，可能会造成内存泄漏。
     * 因此，CompositeDisposable就上场了，它可以对我们订阅的请求进行统一管理。
     * 大致三步走：
     * 1、在UI层创建的时候（比如onCreate之类的），实例化CompositeDisposable；
     * 2、把subscribe订阅返回的Disposable对象加入管理器；
     * 3、UI销毁时清空订阅的对象。
     * */
    private CompositeDisposable mCompositeDisposable;
    private Map<Object, List<Disposable>> cancelRequest;

    public AbsRepository() {

    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unSubscribe() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
            cancelRequest = null;
        }
    }

    /**
     * 添加 网络请求同时  添加标签
     * 用于需要取消请求情况
     *
     * @param tag
     * @param disposable
     */
    protected void addSubscribe(Object tag, Disposable disposable) {
        if (cancelRequest == null) {
            cancelRequest = new HashMap<>();
        }
        List<Disposable> disposables = cancelRequest.get(tag);
        if (disposables == null) {
            disposables = new ArrayList<>();
            cancelRequest.put(tag, disposables);
        }
        disposables.add(disposable);
        addSubscribe(disposable);
    }

    /**
     * 取消对应tag的订阅
     * @param tag
     */
    public void removeSubscribe(Object tag) {
        if (cancelRequest != null && cancelRequest.get(tag) != null && mCompositeDisposable != null) {
            for (Disposable dp : cancelRequest.get(tag)) {
                mCompositeDisposable.remove(dp);
            }
        }
    }
}
