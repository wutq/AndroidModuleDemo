package com.wss.module.main.ui.page.selector.mvp.contract;

import android.content.Context;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.main.bean.Province;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */
public interface SelectContract {

    interface Module {
        /**
         * 获取省市区
         *
         * @param mContext ctx
         * @return 省份
         */
        Observable<List<Province>> getAddress(Context mContext);
    }

    interface View extends IBaseView {

        /**
         * 省份数据
         *
         * @param options1Items 省
         * @param options2Items 市
         * @param options3Items 区
         */
        void refreshAddressList(List<Province> options1Items, List<List<String>> options2Items, List<List<List<String>>> options3Items);

        /**
         * 三级非动态数据
         *
         * @param userList userList
         * @param userFrom userFrom
         * @param userDes  userDes
         */
        void refreshNonLinkageList(List<String> userList, List<String> userFrom, List<String> userDes);
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
