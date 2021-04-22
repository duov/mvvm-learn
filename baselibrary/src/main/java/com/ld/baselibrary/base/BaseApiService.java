package com.ld.baselibrary.base;

import com.ld.baselibrary.http.response.BaseResponse;
import com.ld.baselibrary.http.urls.BaseUrls;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 网络请求
 * 不同模块创建自己的APIService
 * @author: liangduo
 * @date: 2020/9/14 2:46 PM
 */
public interface BaseApiService {
    /**
     * 提交json数据，调用方法示例
     */
    @POST(BaseUrls.LOGIN)
    Flowable<BaseResponse<String>> getLogin(@Body Map<String, Object> map);

}
