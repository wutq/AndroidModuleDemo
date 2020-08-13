package com.wss.module.wan.ui.series.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Classification;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：体系契约
 * Created by 吴天强 on 2018/11/21.
 */
public interface SeriesContract {

    interface Model {

        /**
         * 请求体系
         *
         * @return 体系
         */
        Observable<List<Classification>> getSystem();

    }

    interface View extends IBaseView {

        /**
         * 体系列表
         *
         * @param systems systems
         */
        void refreshSystemList(List<Classification> systems);
    }

    interface Presenter {

    }
}
