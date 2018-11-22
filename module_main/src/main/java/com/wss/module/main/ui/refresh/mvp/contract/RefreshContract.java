package com.wss.module.main.ui.refresh.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;

import java.util.List;

/**
 * Describe：刷新契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface RefreshContract {

    interface Model extends IBaseModel {

    }

    interface View extends IBaseView {
        /**
         * 页码
         */
        int getPage();

        /**
         * 数据
         */
        void stringList(List<String> articles);

    }

    interface Presenter {
        /**
         * 获取列表数据
         */
        void getStringList();
    }
}
