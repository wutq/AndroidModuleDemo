package com.wss.module.wan.ui.collection.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.ui.collection.mvp.contract.CollectionContract;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：收藏Module
 * Created by 吴天强 on 2018/11/16.
 */
public class CollectionModel extends BaseModel implements CollectionContract.Model {


    public CollectionModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }


    @Override
    public Observable<String> getCollectionList(int page) {
        return NetworkManage.createGet().request(getLifecycleOwner(), String.format(Api.COLLECTION_LIST, page));
    }
}
