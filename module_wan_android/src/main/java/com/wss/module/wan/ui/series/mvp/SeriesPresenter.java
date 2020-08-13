package com.wss.module.wan.ui.series.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.ui.series.mvp.contract.SeriesContract;
import com.wss.module.wan.ui.series.mvp.model.SeriesModel;

/**
 * Describe：体系Presenter
 * Created by 吴天强 on 2018/11/21.
 */
public class SeriesPresenter extends BasePresenter<SeriesModel, SeriesContract.View>
        implements SeriesContract.Presenter {


    @Override
    protected SeriesModel createModule() {
        return new SeriesModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        showLoading();
        getModel().getSystem().subscribe(
                classification -> {
                    dismissLoading();
                    if (ValidUtils.isValid(classification)) {
                        getView().refreshSystemList(classification);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> getView().onError("", t.getMessage()));
    }
}
