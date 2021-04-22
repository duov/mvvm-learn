package com.ld.baselibrary.binding.viewadapter.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableList;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingCollectionAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * adapter继承或使用BaseBindingRecyclerViewAdapter
 * 继承adapter bindViewHolder改为editViewHolder
 * viewModel添加
 *
 * @param <T>
 * @Override protected OnNoMoreDataListener initNoMoreDataListener() {
 * return new OnNoMoreDataListener() {
 * @Override public void onNoMoreData(boolean noMoreData) {
 * fansAndFollowsAdapter.showNoMoreFooter(noMoreData);
 * }
 * };
 * }
 * v层
 * mViewModel.setRefresh(mBinding.refreshView);
 * 刷新数据setData加入第二个参数 固定传true
 */
public class BaseBindingRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BindingCollectionAdapter<T> {
    private static final Object DATA_INVALIDATION = new Object();

    private ItemBinding<? super T> itemBinding;
    private final WeakReferenceOnListChangedCallback<T> callback = new WeakReferenceOnListChangedCallback<>(this);
    private List<T> items;
    private LayoutInflater inflater;
    private BaseBindingRecyclerViewAdapter.ItemIds<? super T> itemIds;
    private BaseBindingRecyclerViewAdapter.ViewHolderFactory viewHolderFactory;
    // Currently attached recyclerview, we don't have to listen to notifications if null.
    @Nullable
    private RecyclerView recyclerView;

//    //无数据 底部布局文件
//    public int noMoreFooterLayout = R.layout.layout_no_more_footer;
//    private boolean isShowNoMore = false;
//    private int noMoreTxt = R.string.no_more_data;
//    private View.OnClickListener onClickListener;
//    private boolean isBackColorWhite = false;
//
//    /**
//     * 显示无更多数据 footer布局
//     */
//    public void showNoMoreFooter(boolean isShowNoMore) {
//        if (this.isShowNoMore != isShowNoMore) {
//            this.isShowNoMore = isShowNoMore;
//            notifyDataSetChanged();
//        }
//    }
//
//    /**
//     * 自定义 footer布局 文字
//     */
//    public void setNoMoreText(@StringRes int noMoreTxt) {
//        this.noMoreTxt = noMoreTxt;
//    }
//
//    /**
//     * 设置背景颜色为白色
//     */
//    public void setisBackColorWhite() {
//        isBackColorWhite = true;
//    }
//
//    /**
//     * 自定义 footer布局
//     */
//    public void setNoMoreLayout(@LayoutRes int layoutRes) {
//        this.noMoreFooterLayout = layoutRes;
//    }
//
//    /**
//     * 设置底部footer 无更多数据 点击监听
//     */
//    public void setOnNoMoreClickListener(View.OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
//
//    private boolean isFooter(int position) {
//        return isShowNoMore && position == getItemCount() - 1;
//    }

    @Override
    public void setItemBinding(@NonNull ItemBinding<? super T> itemBinding) {
        this.itemBinding = itemBinding;
    }

    @Override
    public ItemBinding<? super T> getItemBinding() {
        return itemBinding;
    }

    @Override
    public void setItems(@Nullable List<T> items) {
        if (this.items == items) {
            return;
        }
        // If a recyclerview is listening, set up listeners. Otherwise wait until one is attached.
        // No need to make a sound if nobody is listening right?
        if (recyclerView != null) {
            if (this.items instanceof ObservableList) {
                ((ObservableList<T>) this.items).removeOnListChangedCallback(callback);
            }
            if (items instanceof ObservableList) {
                ((ObservableList<T>) items).addOnListChangedCallback(callback);
            }
        }
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public T getAdapterItem(int position) {
//        if (isFooter(position)) {
//            return null;
//        }
        return items.get(position);
    }

    @Override
    public ViewDataBinding onCreateBinding(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup viewGroup) {
        return DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, @LayoutRes int layoutRes, int position, T item) {
        if (itemBinding.bind(binding, item)) {
            binding.executePendingBindings();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView == null && items != null && items instanceof ObservableList) {
            ((ObservableList<T>) items).addOnListChangedCallback(callback);
        }
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (this.recyclerView != null && items != null && items instanceof ObservableList) {
            ((ObservableList<T>) items).removeOnListChangedCallback(callback);
        }
        this.recyclerView = null;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int layoutId) {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.getContext());
        }
//        if (layoutId == noMoreFooterLayout) {
//            return new MyViewHolder(inflater.inflate(noMoreFooterLayout, viewGroup, false));
//        }
        ViewDataBinding binding = onCreateBinding(inflater, layoutId, viewGroup);
        final RecyclerView.ViewHolder holder = onCreateViewHolder(binding);
        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                return recyclerView != null && recyclerView.isComputingLayout();
            }

            @Override
            public void onCanceled(ViewDataBinding binding) {
                if (recyclerView == null || recyclerView.isComputingLayout()) {
                    return;
                }
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position, DATA_INVALIDATION);
                }
            }
        });
        return holder;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        if (viewHolderFactory != null) {
            return viewHolderFactory.createViewHolder(binding);
        } else {
            return new BindingViewHolder(binding);
        }
    }

    private static class BindingViewHolder extends RecyclerView.ViewHolder {
        public BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        if (isFooter(position)) {
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickListener != null) {
//                        onClickListener.onClick(v);
//                    }
//                }
//            });
//            View view = viewHolder.itemView.findViewById(R.id.tv_txt);
//            LinearLayout linearLayout = viewHolder.itemView.findViewById(R.id.foot_ll);
//            if (view != null) {
//                ((TextView) view).setText(noMoreTxt);
//            }
//            if (isBackColorWhite) {
//                linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//            }
//            return;
//        }
        T item = items.get(position);
        ViewDataBinding binding = DataBindingUtil.getBinding(viewHolder.itemView);
        onBindBinding(binding, itemBinding.variableId(), itemBinding.layoutRes(), position, item);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
//        if (isFooter(position)) {
//            super.onBindViewHolder(holder, position, payloads);
//            return;
//        }
        if (isForDataBinding(payloads)) {
            ViewDataBinding binding = DataBindingUtil.getBinding(holder.itemView);
            binding.executePendingBindings();
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
        editViewHolder(holder, position, payloads);
    }

    public void editViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {

    }

    private boolean isForDataBinding(List<Object> payloads) {
        if (payloads == null || payloads.size() == 0) {
            return false;
        }
        for (int i = 0; i < payloads.size(); i++) {
            Object obj = payloads.get(i);
            if (obj != DATA_INVALIDATION) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final int getItemViewType(int position) {
//        if (isFooter(position)) {
//            return noMoreFooterLayout;
//        }
        itemBinding.onItemBind(position, items.get(position));
        return itemBinding.layoutRes();
    }

    /**
     * Set the item id's for the items. If not null, this will set {@link
     * RecyclerView.Adapter#setHasStableIds(boolean)} to true.
     */
    public void setItemIds(@Nullable ItemIds<? super T> itemIds) {
        if (this.itemIds != itemIds) {
            this.itemIds = itemIds;
            setHasStableIds(itemIds != null);
        }
    }

    /**
     * Set the factory for creating view holders. If null, a default view holder will be used. This
     * is useful for holding custom state in the view holder or other more complex customization.
     */
    public void setViewHolderFactory(@Nullable ViewHolderFactory factory) {
        viewHolderFactory = factory;
    }

    @Override
    public final int getItemCount() {
//        int count = (items != null && items.size() > 0 ? items.size() : 0);
//        if (isShowNoMore) {
//            return count + 1;
//        } else {
//            return count;
//        }
        return items == null ? 0 : items.size();
    }

    @Override
    public long getItemId(int position) {
        return itemIds == null ? position : itemIds.getItemId(position, items.get(position));
    }

    private static class WeakReferenceOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
        final WeakReference<BaseBindingRecyclerViewAdapter<T>> adapterRef;

        WeakReferenceOnListChangedCallback(BaseBindingRecyclerViewAdapter<T> adapter) {
            this.adapterRef = new WeakReference<>(adapter);
        }

        @Override
        public void onChanged(ObservableList sender) {
            BaseBindingRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList sender, final int positionStart, final int itemCount) {
            BaseBindingRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList sender, final int positionStart, final int itemCount) {
            BaseBindingRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList sender, final int fromPosition, final int toPosition, final int itemCount) {
            BaseBindingRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            Utils.ensureChangeOnMainThread();
            for (int i = 0; i < itemCount; i++) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i);
            }
        }

        @Override
        public void onItemRangeRemoved(ObservableList sender, final int positionStart, final int itemCount) {
            BaseBindingRecyclerViewAdapter<T> adapter = adapterRef.get();
            if (adapter == null) {
                return;
            }
            Utils.ensureChangeOnMainThread();
            adapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    public interface ItemIds<T> {
        long getItemId(int position, T item);
    }

    public interface ViewHolderFactory {
        RecyclerView.ViewHolder createViewHolder(ViewDataBinding binding);
    }
}
