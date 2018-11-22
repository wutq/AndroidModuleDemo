package com.wss.module.main.ui.selector.mvp.contract;

import android.content.Context;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.main.bean.Province;

import java.util.List;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface SelectContract {

    interface Module extends IBaseModel {
        /**
         * 获取省市区
         */
        void getAddress(Context mContext, OnResultCallBack<String> callBack);
    }

    interface View extends IBaseView {

        /**
         * 省份数据
         *
         * @param options1Items 省
         * @param options2Items 市
         * @param options3Items 区
         */
        void addressList(List<Province> options1Items, List<List<String>> options2Items, List<List<List<String>>> options3Items);

        /**
         * 三级非动态数据
         *
         * @param userList userList
         * @param userFrom userFrom
         * @param userDes  userDes
         */
        void userList(List<String> userList, List<String> userFrom, List<String> userDes);
    }

    interface Presenter {
        /**
         * 获取省份
         */
        void getAddressList();

        /**
         * 获取用户
         */
        void getUserList();
    }
}
