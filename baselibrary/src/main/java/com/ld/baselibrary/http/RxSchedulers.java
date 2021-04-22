package com.ld.baselibrary.http;

import com.ld.baselibrary.http.response.BaseResponse;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Use Observable when you have relatively few items over time (<1000) and/or
 * there's no risk of producer overflooding consumers and thus causing OOM.
 *
 * Use Flowable when you have relatively large amount of items and you need
 * to carefully control how Producer behaves in order to to avoid resource exhaustion and/or congestion
 * 线程调度器
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> observableTransformerMain(String s) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * FlowableTransformer
     * Flowable背压支持
     * 只有上下游运行在各自的线程中，且上游发射数据速度大于下游接收处理数据的速度时，才会产生背压，
     * 由于添加了背压支持，附加了额外的逻辑,其运行效率要比Observable低得多。
     *
     * Flowable的异步缓存池不同于Observable，Observable的异步缓存池没有大小限制，
     * 可以无限制向里添加数据，直至OOM,而Flowable的异步缓存池有个固定容量，其大小为128。
     * BackpressureStrategy的作用便是用来设置Flowable通过异步缓存池存储数据的策略。
     * https://blog.csdn.net/speverriver/article/details/79036434?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0&spm=1001.2101.3001.4242
     */
    public static <T> FlowableTransformer<T, T> flowableTransformerMain() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> io_main() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
//                        .map(new HandleFuc<T>())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private static class HandleFuc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) {
            if (!response.isSuccess())
                throw new RuntimeException(!"".equals(response.getCode() + "" + response.getMessage()) ? response.getMessage() : "");
            return response.getResult();
        }
    }
}
