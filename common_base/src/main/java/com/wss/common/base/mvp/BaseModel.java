package com.wss.common.base.mvp;


import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：网络请求基类
 * Created by 吴天强 on 2019/7/11.
 */
public class BaseModel {

    private LifecycleOwner lifecycleOwner;

    public BaseModel(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    /**
     * 返回生命周期所有者
     *
     * @return LifecycleOwner
     */
    protected LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

}
