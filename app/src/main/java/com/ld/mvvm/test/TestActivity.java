package com.ld.mvvm.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.lifecycle.Observer;

import com.ld.baselibrary.base.BaseActivity;
import com.ld.baselibrary.util.ContextUtils;
import com.ld.baselibrary.util.NotificationUtil;
import com.ld.mvvm.BR;
import com.ld.mvvm.R;
import com.ld.mvvm.databinding.TestActivityTestBinding;

/**
 * @author: liangduo
 * @date: 2021/3/2 1:42 PM
 */
public class TestActivity extends BaseActivity<TestActivityTestBinding, TestVm> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.test_activity_test;
    }

    @Override
    public int initVariableId() {
        return BR.testVm;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();

        // 跳转应用设置
        mViewModel.turn.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                NotificationUtil.toNotificationSetting(TestActivity.this);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mViewModel.initData();
    }
}
