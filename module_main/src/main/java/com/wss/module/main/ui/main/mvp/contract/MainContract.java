package com.wss.module.main.ui.main.mvp.contract;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.AppInfo;
import com.wss.common.bean.Template;

import java.util.List;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface MainContract {

    interface Model extends IBaseModel {

        /**
         * 检查更新
         *
         * @param callback 回调函数
         */
        void checkUpdate(ResponseCallback callback);
    }

    interface View extends IBaseView {

        /**
         * 需要更新
         *
         * @param appInfo appInfo
         */
        void needUpdate(AppInfo appInfo);

        /**
         * 控件tab数据列表
         *
         * @param blockList blockList
         */
        void tabList(List<Template> blockList);
    }

    interface Presenter {
        /**
         * 检查更新
         */
        void checkUpdate();

        /**
         * 获取控件tab数据
         */
        void getTabList();
    }
}
