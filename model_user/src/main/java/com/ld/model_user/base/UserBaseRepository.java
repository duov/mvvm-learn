package com.ld.model_user.base;

import com.ld.baselibrary.base.AbsRepository;
import com.ld.baselibrary.http.HttpClient;

/**
 * @author: liangduo
 * @date: 2021/4/21 10:24 AM
 */
public class UserBaseRepository extends AbsRepository {
    protected UserBaseApiService userBaseApiService;

    public UserBaseRepository() {
        if (userBaseApiService == null){
            userBaseApiService = HttpClient.getInstance().create(UserBaseApiService.class);
        }
    }
}
