package com.ld.mvvm.base;

import com.ld.baselibrary.base.AbsRepository;
import com.ld.baselibrary.base.BaseApiService;
import com.ld.baselibrary.base.BaseRepository;
import com.ld.baselibrary.http.HttpClient;

/**
 * @author: liangduo
 * @date: 2021/4/20 4:50 PM
 */
public class AppRepository extends AbsRepository {
    protected AppApiService  appApiService;

    // 获取ApiService和网络请求订阅容器，方便管理网络请求
    public AppRepository(){
        if (appApiService == null){
            appApiService = HttpClient.getInstance().create(AppApiService.class);
        }
    }

}
