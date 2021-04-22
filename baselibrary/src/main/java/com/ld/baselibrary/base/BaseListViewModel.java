package com.ld.baselibrary.base;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.ref.WeakReference;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.collections.MergeObservableList;

/**
 * @author: liangduo
 * @date: 2021/4/13 2:44 PM
 */
public abstract class BaseListViewModel<M extends AbsRepository, T, VM extends MultiItemViewModel> extends BaseViewModel<M>{
    // 当前页数
    public int pageSize = 10;
    // 当前页数
    public int pageNo = 1;
    // 下拉刷新控件
    protected WeakReference<RefreshLayout> mRefreshLayout;
    // 是否在刷新/加载中
    public boolean isLoadAndRefresh = false;

    // 数据源
    public MergeObservableList<T> mList = new MergeObservableList<>();
    // databing
    public ItemBinding<VM> itemBinding = initItemBinding();
    // 多布局数据源(多数据源)
    public MergeObservableList<VM> observableList = new MergeObservableList<>();
    // adapter
    private BindingRecyclerViewAdapter<VM> adapter;
    // 刷新，加载更多监听
    public OnRefreshLoadMoreListener onRefreshLoadMoreListener;

    public BaseListViewModel(@NonNull Application application) {
        super(application);
        // 刷新，加载更多监听
        initRefreshLoadMoreListener();
    }

    protected abstract ItemBinding initItemBinding();

    protected abstract void conversionItemViewModel(ObservableList<T> list);

    protected void addList(VM itemViewModel) {
        observableList.insertItem(itemViewModel);
    }

    public void delItem(VM item) {
        observableList.removeItem(item);
    }

    /**
     * 填充数据
     */
    public void setData(List<T> data) {
        this.setData(data, false);
    }

    /**
     * 填充数据
     */
    public void setData(List<T> data, boolean isShow) {
        if (data != null && data.size() > 0) {
            if (pageNo == 1 || (mRefreshLayout != null && mRefreshLayout.get().getState() == RefreshState.Refreshing)) {
                mList.removeAll();
                // 先移除监听，防止闪烁占位图
//                observableList.removeOnListChangedCallback(onListChangedCallback);
                observableList.removeAll();
//                observableList.addOnListChangedCallback(onListChangedCallback);
            }

            // 转变类型
            ObservableList observableList = new ObservableArrayList();
            observableList.addAll(data);
            conversionItemViewModel(observableList);

            initItemBinding();
//            if (isShow && data.size() < getPageSize()) {
//                setNoMoreData(true);
//            } else {
//                setNoMoreData(false);
//                if (mRefreshLayout != null && (mRefreshLayout.get().getState() == RefreshState.Refreshing
//                        || mRefreshLayout.get().getState() == RefreshState.Loading)) {
//                    onFinishRefreshLoadMore();
//                }
//            }
//            if (mStatus != PageStatus.SUCCSE) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        showSuccessView();
//                    }
//                }, 1000);
//            }
        } else {
            if (mRefreshLayout != null && mRefreshLayout.get().getState() == RefreshState.Loading) {
                pageNo--;
//                if (isShow) {
//                    setNoMoreData(true);
//                } else {
//                    setNoMoreData(false);
//                    onFinishRefreshLoadMore();
//                    showToast(getString(R.string.all_information_loaded));
//                }
            } else {
                mList.removeAll();
                observableList.removeAll();
//                showModelEmptyView();
//                setNoMoreData(false);
            }
        }
        onFinishRefreshLoadMore();
    }

    public void initRefreshLoadMoreListener(){
        onRefreshLoadMoreListener = new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                isLoadAndRefresh = true;
                setRefresh(refreshLayout);
                onRefreshLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo = 1;
                isLoadAndRefresh = true;
                setRefresh(refreshLayout);
                onRefreshLoadMore();
            }
        };
    }

    public void setRefresh(RefreshLayout refreshLayout) {
        if (mRefreshLayout == null) {
            this.mRefreshLayout = new WeakReference<>(refreshLayout);
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 刷新动作
     */
    public abstract void onRefreshLoadMore();

    /**
     * 执行结束动作
     */
    protected void onFinishRefreshLoadMore() {
        if (mRefreshLayout != null) {
            if (mRefreshLayout.get().getState() == RefreshState.Refreshing) {
                mRefreshLayout.get().finishRefresh();
            } else {
                mRefreshLayout.get().finishLoadMore();
            }
        }
    }
}
