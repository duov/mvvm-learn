package com.ld.model_user.base;

import com.ld.baselibrary.base.BaseApplication;

/**
 * @author: liangduo
 * @date: 2020/8/24 1:43 PM
 */
public class UserBaseApplication extends BaseApplication {
    private static UserBaseApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static UserBaseApplication getInstance() {
        if (null == myApplication) {
            myApplication = new UserBaseApplication();
        }
        return myApplication;
    }
}