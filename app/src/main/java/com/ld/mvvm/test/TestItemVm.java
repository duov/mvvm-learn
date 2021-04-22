package com.ld.mvvm.test;

import androidx.annotation.NonNull;

import com.ld.baselibrary.base.BaseViewModel;
import com.ld.baselibrary.base.MultiItemViewModel;

/**
 * @author: liangduo
 * @date: 2021/4/13 4:15 PM
 */
public class TestItemVm extends MultiItemViewModel {

    public TestItemVm(@NonNull BaseViewModel viewModel, Object multiType, Object sourcesData) {
        super(viewModel, multiType, sourcesData);
    }
}
