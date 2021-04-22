package com.ld.mvvm.test;

import android.os.Bundle;

import com.ld.baselibrary.base.BaseActivity;
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
    public void initData() {
        super.initData();
        mViewModel.initData();
    }
}
