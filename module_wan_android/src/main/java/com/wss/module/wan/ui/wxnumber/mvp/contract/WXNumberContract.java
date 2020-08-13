package com.wss.module.wan.ui.wxnumber.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.WXNumber;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：微信公众号 契约类
 * Created by 吴天强 on 2018/11/21.
 */
public interface WXNumberContract {


    interface Model {

        /**
         * 获取微信公众号
         *
         * @return 公众号列表
         */
        Observable<List<WXNumber>> getWxNumber();

    }

    interface View extends IBaseView {

        /**
         * 刷新公众号列表
         *
         * @param numberList 公众号列表
         */
        void refreshWxNumber(List<WXNumber> numberList);
    }

    interface Presenter {

    }
}
