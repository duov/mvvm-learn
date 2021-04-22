package com.ld.baselibrary.binding.viewadapter.smartrefresh;

import androidx.databinding.BindingAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Created ld time
 * <p>
 * Description：
 */
public class ViewAdapter {
    @BindingAdapter(value = {"onRefreshLoadMoreCommand", "autoRefresh"}, requireAll = false)
    public static void onRefreshLoadMoreCommand(SmartRefreshLayout smartRefreshLayout, final OnRefreshLoadMoreListener onRefreshLoadMoreListener, final boolean autoRefresh) {
        if (onRefreshLoadMoreListener != null) {
            smartRefreshLayout.setOnRefreshLoadMoreListener(onRefreshLoadMoreListener);

            if (autoRefresh)
                smartRefreshLayout.autoRefresh();
        }
    }

    @BindingAdapter(value = {"refreshEnable"}, requireAll = false)
    public static void onRefreshEnabled(SmartRefreshLayout smartRefreshLayout, boolean refreshEnabled) {
        smartRefreshLayout.setEnabled(refreshEnabled);
    }

    @BindingAdapter(value = {"loadMoreEnable"})
    public static void srlEnableLoadMore(SmartRefreshLayout smartRefreshLayout, boolean refreshEnabled) {
        smartRefreshLayout.setEnabled(refreshEnabled);
    }
}
