package com.wss.module.wan.ui.main.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.module.wan.bean.BannerInfo;
import com.wss.module.wan.bean.WXNumber;
import com.wss.module.wan.ui.main.mvp.contract.HomeContract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：首页Module
 * Created by 吴天强 on 2018/10/17.
 */
public class HomeModel extends BaseModel implements HomeContract.Model {

    public HomeModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<List<BannerInfo>> getBanner() {
        return NetworkManage.createGet().requestList(getLifecycleOwner(), Api.GET_BANNER_LIST, BannerInfo.class);
    }

    @Override
    public Observable<String> getArticleList(int page) {
        return NetworkManage.createGet().request(getLifecycleOwner(), String.format(Api.GET_ARTICLE_LIST, page));
    }

    @Override
    public Observable<List<WXNumber>> getWxNumber() {
        return NetworkManage.createGet().requestList(getLifecycleOwner(), Api.WX_NUMBER, WXNumber.class);
    }
}
