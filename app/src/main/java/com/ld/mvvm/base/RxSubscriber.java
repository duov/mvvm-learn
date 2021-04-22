package com.ld.mvvm.base;

import com.ld.baselibrary.http.exception.ServerException;
import com.ld.baselibrary.http.response.BaseResponse;
import com.ld.baselibrary.http.util.NetworkUtil;
import com.ld.baselibrary.util.ContextUtils;

import java.net.UnknownHostException;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Describe:订阅网络请求
 */
public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {

    public RxSubscriber() {
        super();
    }

    /**
     * 获取网络数据开始，判断无网络抛出异常，取消请求
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtil.isNetworkAvailable(ContextUtils.getContext())) {
            onError(new UnknownHostException());
            cancel();
            return;
        }
        onBefore();
    }

    /**
     * 获取网络数据结束
     */
    @Override
    public void onComplete() {

    }

    /**
     * 获取网络数据异常
     */
    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseResponse) {
            if (((BaseResponse) t).isSuccess()) {
                onSuccess(t);
            } else {
                onServiceError(t);
            }
        } else {
            onSuccess(t);
        }
    }

    /**
     * 在请求之前的方法，一般用于加载框展示
     */
    public abstract void onBefore();

    /**
     * 在请求之后成功回调
     */
    public abstract void onSuccess(T t);

    /**
     * 请求失败的回调
     */
    public abstract void onFailure(Throwable throwable);

    /**
     * 请求成功，服务器下发异常
     *
     * @param response
     */
    protected void onServiceError(T response) {
        onFailure(new ServerException(((BaseResponse) response).getCode(), ((BaseResponse) response).getMessage()));
    }
}
