package com.ld.baselibrary.base;


import androidx.annotation.NonNull;

/**
 * Description：RecycleView多布局ItemViewModel是封装
 */

public class MultiItemViewModel<VM extends BaseViewModel> extends ItemViewModel<VM> {
    // 多布局类型
    protected Object multiType;
    // 多布局数据
    protected Object sourcesData;

    public MultiItemViewModel(@NonNull VM viewModel) {
        super(viewModel);
    }

    public MultiItemViewModel(@NonNull VM viewModel, Object multiType) {
        super(viewModel);
        this.multiType = multiType;
    }

    public MultiItemViewModel(@NonNull VM viewModel, Object multiType, Object sourcesData) {
        super(viewModel);
        this.multiType = multiType;
        this.sourcesData = sourcesData;
    }

    public Object getItemType() {
        return multiType;
    }

    public void multiItemType(@NonNull Object multiType) {
        this.multiType = multiType;
    }

    public Object getSourcesData() {
        return sourcesData;
    }

    public void setSourcesData(Object sourcesData) {
        this.sourcesData = sourcesData;
    }


}
