package com.ld.baselibrary.base;

/**
 *
 * @author: liangduo
 * @date: 2020/9/14 3:11 PM
 */
public interface IBaseView {
    /**
     * 初始化界面
     */
    void initView();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
