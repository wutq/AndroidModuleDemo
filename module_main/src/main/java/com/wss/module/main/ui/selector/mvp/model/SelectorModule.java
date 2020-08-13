package com.wss.module.main.ui.selector.mvp.model;

import android.content.Context;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.Utils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.main.bean.Province;
import com.wss.module.main.ui.selector.mvp.contract.SelectContract;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe：获取数据
 * Created by 吴天强 on 2018/10/24.
 */
public class SelectorModule extends BaseModel implements SelectContract.Module {


    public SelectorModule(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<List<Province>> getAddress(Context mContext) {
        return Observable.<List<Province>>create(
                subscriber -> {
                    String assetFileData = Utils.getAssetFileData(mContext, "province.json");
                    if (ValidUtils.isValid(assetFileData)) {
                        subscriber.onNext(JsonUtils.getList(assetFileData, Province.class));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
