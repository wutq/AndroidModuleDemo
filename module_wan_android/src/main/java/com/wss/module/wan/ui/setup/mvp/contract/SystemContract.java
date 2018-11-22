package com.wss.module.wan.ui.setup.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：体系契约
 * Created by 吴天强 on 2018/11/21.
 */

public interface SystemContract {

    interface Model extends IBaseModel {

        /**
         * 请求体系
         *
         * @param callback 回调
         */
        void getSystem(OnResultCallBack callback);

    }

    interface View extends IBaseModel, IBaseView {

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
