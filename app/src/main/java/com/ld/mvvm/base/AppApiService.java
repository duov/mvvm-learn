package com.ld.mvvm.base;

import com.ld.baselibrary.http.response.BaseResponse;
import com.ld.mvvm.AppUrls;
import com.ld.mvvm.test.TestListBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author: liangduo
 * @date: 2020/9/1 4:14 PM
 */
public interface AppApiService{

    // @GET注解的作用:采用Get方法发送网络请求

    // getCall() = 接收网络请求数据的方法
    // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>

    @GET("openapi.do?keyfrom=abc&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<BaseResponseBean> getCall();

    // 获取精选信息流接口
    @POST(AppUrls.HOME_SELECTION_LIST)
    Flowable<BaseResponse<TestListBean>> getList(@Body Map<String, Object> map);
}
