package com.ld.mvvm.test;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.ld.baselibrary.base.MultiItemViewModel;

/**
 * @author: liangduo
 * @date: 2021/4/16 10:30 AM
 */
public class ItemTestActivityVm extends MultiItemViewModel<TestVm> {
    public TestListBean.ModelData data;
    public ObservableField<String> url = new ObservableField<>();

    public ItemTestActivityVm(@NonNull TestVm viewModel, Object multiType, TestListBean.ModelData sourcesData) {
        super(viewModel, multiType, sourcesData);
        data = sourcesData;
        url.set("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201410%2F20%2F20141020162058_UrMNe.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1621505923&t=d05d1cd81e0b07aae75e8e814d4128a4");
    }
}
