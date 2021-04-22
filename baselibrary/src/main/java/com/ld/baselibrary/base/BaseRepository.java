package com.ld.baselibrary.base;

import com.ld.baselibrary.http.HttpClient;

/**
 * Repository:仓库
 * @author: liangduo
 * @date: 2020/9/14 2:38 PM
 * Describe: M层-获取网络数据基类
 * 在ViewModel中调用Model层
 * Repository只负责数据处理，提供干净的api，方便切换数据来源。
 * 各自model中定义repository extends AbsRepository
 */
public abstract class BaseRepository extends AbsRepository {
    protected BaseApiService  apiService;

    // 获取ApiService和网络请求订阅容器，方便管理网络请求
    public BaseRepository(){
        if (apiService == null){
            apiService = HttpClient.getInstance().create(BaseApiService.class);
        }
    }

}
