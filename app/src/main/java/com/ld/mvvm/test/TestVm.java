package com.ld.mvvm.test;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ld.baselibrary.arouter.ARouterPath;
import com.ld.baselibrary.base.BaseListViewModel;
import com.ld.baselibrary.base.BaseRepository;
import com.ld.baselibrary.base.BaseViewModel;
import com.ld.baselibrary.base.MultiItemViewModel;
import com.ld.baselibrary.http.RxSubscriber;
import com.ld.baselibrary.http.response.BaseResponse;
import com.ld.baselibrary.util.ContextUtils;
import com.ld.mvvm.BR;
import com.ld.mvvm.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * @author: liangduo
 * @date: 2021/3/2 1:43 PM
 */
public class TestVm extends BaseListViewModel<TestRepository, TestListBean.ModelData, MultiItemViewModel> {

    public ObservableField<String> testTv = new ObservableField<>("test");
    public MutableLiveData<Boolean> turn = new MutableLiveData<>();

    public TestVm(@NonNull Application application) {
        super(application);
    }


    @Override
    public void initData() {
        super.initData();
        Map<String, Object> maps = new HashMap<>();
        maps.put("source", 2);
        maps.put("type", 2);
        maps.put("mode", 2);
        maps.put("authority", 1);
        maps.put("channel", 0);
        maps.put("bannerId", "Banner2");
        maps.put("pageUrl", "/homePage");

        Map<String, Object> map = new HashMap<>();
        map.put("adCondition", maps);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("condition", map);
        map1.put("pageNo", pageNo);
        map1.put("pageSize", pageSize);
        mRepository.getList(map, new RxSubscriber<BaseResponse<TestListBean>>() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(BaseResponse<TestListBean> stringBaseResponse) {
                setData(stringBaseResponse.getResult().getModelData());

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    @Override
    protected ItemBinding initItemBinding() {
        return ItemBinding.of(new OnItemBind() {
            @Override
            public void onItemBind(@NonNull ItemBinding itemBinding, int position, Object item) {
                itemBinding.set(BR.itemTestVm , R.layout.item_test_activity);
                itemBinding.bindExtra(BR.position, position);
            }
        });
    }

    @Override
    protected void conversionItemViewModel(ObservableList<TestListBean.ModelData> list) {
        for (TestListBean.ModelData b : list) {
            addList(new ItemTestActivityVm(this, 0, b));
        }
    }

    @Override
    public void onRefreshLoadMore() {
        initData();

    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_test:
//                    testTv.set("ok");
//                    ARouter.getInstance().build(ARouterPath.UserActivity).navigation();
                    turn.postValue(true);
                    break;
            }

        }
    };
}
