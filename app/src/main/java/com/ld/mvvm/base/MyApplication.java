package com.ld.mvvm.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ld.baselibrary.base.BaseApplication;
import com.ld.mvvm.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liangduo
 * @date: 2020/8/24 1:43 PM
 */
public class MyApplication extends BaseApplication {
    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static MyApplication getInstance() {
        if (null == myApplication) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }
}