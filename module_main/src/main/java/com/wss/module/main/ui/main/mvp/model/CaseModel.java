package com.wss.module.main.ui.main.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.module.main.ui.main.mvp.contract.CaseContract;

import androidx.lifecycle.LifecycleOwner;

/**
 * Describe：首页Model
 * Created by 吴天强 on 2020/8/11.
 */
public class CaseModel extends BaseModel implements CaseContract.Model {

    public CaseModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }
}
