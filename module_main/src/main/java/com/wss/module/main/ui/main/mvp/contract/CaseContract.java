package com.wss.module.main.ui.main.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.Template;

import java.util.List;

/**
 * Describe：案例契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface CaseContract {

    interface Model {

    }

    interface View extends IBaseView {


        /**
         * 刷新案例列表
         *
         * @param caseList 案例列表
         */
        void refreshCaseList(List<Template> caseList);
    }

    interface Presenter {
    }
}
