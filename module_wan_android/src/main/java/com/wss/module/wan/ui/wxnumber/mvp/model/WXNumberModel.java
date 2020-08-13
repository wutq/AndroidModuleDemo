package com.wss.module.wan.ui.wxnumber.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.bean.WXNumber;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXNumberContract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：公众号文章Module
 * Created by 吴天强 on 2018/11/15.
 */
public class WXNumberModel extends BaseModel implements WXNumberContract.Model {


    public WXNumberModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<List<WXNumber>> getWxNumber() {
        return NetworkManage.createGet().requestList(getLifecycleOwner(), Api.WX_NUMBER, WXNumber.class);
    }
}
