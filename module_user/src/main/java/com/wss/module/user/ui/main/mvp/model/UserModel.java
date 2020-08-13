package com.wss.module.user.ui.main.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.module.user.ui.main.mvp.contract.UserContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：我的Module
 * Created by 吴天强 on 2018/11/21.
 */
public class UserModel extends BaseModel implements UserContract.Model {

    public UserModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
