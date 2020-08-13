package com.wss.module.wan.ui.search.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：搜索契类
 * Created by 吴天强 on 2018/11/21.
 */
public interface SearchContract {

    interface Module {

        /**
         * 搜索
         *
         * @param page 页码
         * @param word 关键字
         * @return string
         */
        Observable<String> searchData(int page, String word);
    }

    interface View extends IBaseView {
        /**
         * 返回搜索关键字
         *
         * @return 搜索关键字
         */
        String getWord();

        /**
         * 返回分页页码
         *
         * @return 页码
         */
        int getPage();

        /**
         * 搜索数据列表
         *
         * @param searchs 文章
         */
        void refreshSearchData(List<Article> searchs);
    }

    interface Presenter {

        /**
         * 搜索
         */
        void search();
    }

}
