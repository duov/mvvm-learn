package com.ld.mvvm.test;

import com.ld.baselibrary.base.BaseRepository;
import com.ld.baselibrary.http.RxSubscriber;
import com.ld.baselibrary.http.response.BaseResponse;
import com.ld.mvvm.base.AppRepository;

import java.util.Map;

import static com.ld.baselibrary.http.RxSchedulers.flowableTransformerMain;

/**
 * @author: liangduo
 * @date: 2021/4/13 4:13 PM
 */
public class TestRepository extends AppRepository {

    public void getList(Map<String, Object> map , RxSubscriber subscriber){
        addSubscribe(appApiService.getList(map).compose(flowableTransformerMain()).subscribeWith(subscriber));
    }
}
