package com.ld.model_user.base;

import com.ld.baselibrary.http.response.BaseResponse;
import com.ld.model_user.bean.UserListBean;
import com.ld.model_user.http.UserUrls;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *
 * @author: liangduo
 * @date: 2020/9/14 2:53 PM
 */
public interface UserBaseApiService {
    // 获取精选信息流接口
    @POST(UserUrls.HOME_SELECTION_LIST)
    Flowable<BaseResponse<UserListBean>> getList(@Body Map<String, Object> map);
}
