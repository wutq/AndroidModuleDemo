package com.wss.module.wan.ui.setup.mvp.contract;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.IBaseModule;
import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：体系契约
 * Created by 吴天强 on 2018/11/21.
 */

public interface SystemContract {

    interface Module extends IBaseModule {

        /**
         * 请求体系
         *
         * @param callback 回调
         */
        void getSystem(ResponseCallback callback);

    }

    interface View extends IBaseModule, IBaseView {

        /**
         * 体系列表
         *
         * @param systems systems
         */
        void systemList(List<Classification> systems);
    }

    interface Presenter {
        /**
         * 获取体系
         */
        void getSystem();
    }
}
