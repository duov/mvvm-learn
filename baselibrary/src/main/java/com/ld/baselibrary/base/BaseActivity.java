package com.ld.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author: liangduo
 * @date: 2020/9/14 11:29 AM
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView {
    protected V mBinding;
    protected VM mViewModel;
    private int mViewModelId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        // 私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //数据初始化
        initData();
        //UI初始化
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinding != null){
            mBinding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true },
        // 同步后会自动关联android.databinding包
        mBinding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        mViewModelId = initVariableId();
        mViewModel = initViewModel();
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        mBinding.setVariable(mViewModelId, mViewModel);

        /*LiveData组件，帮助我们完成ViewModel与页面组件之间的通信。所以，LiveData通常是被放在ViewModel中使用。
        LiveData是一个可被观察的数据容器类。我们可以将LiveData理解为一个数据的容器，
        它将数据包装起来，使得数据成为“被观察者”，页面成为“观察者”。
        这样，当该数据发生变化时，页面能够获得通知，进而更新UI。
        LiveData能够感知页面的生命周期。它可以检测页面当前的状态是否为激活状态，或者页面是否被销毁。
        只有在页面处于激活状态（Lifecycle.State.ON_STARTED或Lifecycle.State.ON_RESUME）时，
        页面才会收到来自LiveData的通知，如果页面被销毁（Lifecycle.State.ON_DESTROY），
        那么LiveData会自动清除与页面的关联，从而避免了可能引发的内存泄漏问题。
         */
        //支持LiveData绑定xml，数据改变，UI自动会更新
        mBinding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(mViewModel);
        //注入RxLifecycle生命周期
        mViewModel.injectLifecycleProvider(this);
    }

    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {

        // toast
        mViewModel.getUC().getShowDialogEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String str) {
                showToast(str);
            }
        });

        //跳入新页面
        mViewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = null;
                if (params.containsKey(BaseViewModel.ParameterField.CLASS)) {
                    clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                }

                if (clz != null) {
                    if (params.containsKey(BaseViewModel.ParameterField.INTENT)) {
                        // 直接使用Intent对象跳转
                        Intent intent = (Intent) params.get(BaseViewModel.ParameterField.INTENT);
                        startActivity(clz, intent);
                    } else {
                        Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                        startActivity(clz, bundle);
                    }
                }
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return  ViewModelProviders.of(activity).get(cls);
    }

    /*------------------------一下内容为页面共通-----------------------------------------------*/

    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param intent 跳转intent
     */
    public void startActivity(Class<?> clz, Intent intent) {
        intent.setClass(this, clz);
        startActivity(intent);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
//        Intent intent = new Intent(this, ContainerActivity.class);
//        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
//        if (bundle != null) {
//            intent.putExtra(ContainerActivity.BUNDLE, bundle);
//        }
//        startActivity(intent);
    }
}
