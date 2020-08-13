package com.wss.module.wan.ui.series.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.series.mvp.contract.SeriesContract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：体系Module
 * Created by 吴天强 on 2018/11/21.
 */
public class SeriesModel extends BaseModel implements SeriesContract.Model {


    public SeriesModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<List<Classification>> getSystem() {
        return NetworkManage.createGet().requestList(getLifecycleOwner(), Api.TREE, Classification.class);
    }
}
