package com.wss.common.base.mvp;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：Presenter基类
 * Created by 吴天强 on 2018/10/17.
 */
@SuppressWarnings("unchecked")
public abstract class BasePresenter<M extends BaseModel, V extends IBaseView> {

    private V mProxyView;
    private M model;
    private WeakReference<V> weakReference;

    /**
     * 绑定View
     *
     * @param view view
     */
    public void attachView(V view) {
        weakReference = new WeakReference<>(view);
        mProxyView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new MvpViewHandler(weakReference.get()));
        if (this.model == null) {
            this.model = createModule();
        }
    }

    /**
     * 解绑View
     */
    public void detachView() {
        this.model = null;
        if (!isViewDetached()) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * 是否与View断开连接
     */
    protected boolean isViewDetached() {
        return weakReference == null || weakReference.get() == null;
    }

    /**
     * 返回View
     *
     * @return view
     */
    protected V getView() {
        return mProxyView;
    }

    /**
     * 返回Model
     *
     * @return model
     */
    protected M getModel() {
        return model;
    }

    /**
     * 返回Context
     *
     * @return context
     */
    protected Context getContext() {
        return getView().getContext();
    }

    /**
     * 返回持有View的生命周期所有者
     *
     * @return LifecycleOwner
     */
    protected LifecycleOwner getLifecycleOwner() {
        return getView().getLifecycleOwner();
    }

    /**
     * 显示加载框
     */
    protected void showLoading() {
        getView().showLoading();
    }

    /**
     * 隐藏加载框
     */
    protected void dismissLoading() {
        getView().dismissLoading();
    }

    /**
     * 通过该方法创建Module
     */
    protected abstract M createModule();

    /**
     * 初始化方法
     */
    public abstract void start();


    /**
     * View代理类  防止 页面关闭P异步操作调用V 方法 空指针问题
     */
    private class MvpViewHandler implements InvocationHandler {

        private IBaseView mvpView;

        MvpViewHandler(IBaseView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (!isViewDetached()) {
                return method.invoke(mvpView, args);
            } //P层不需要关注V层的返回值
            return null;
        }
    }
}