package com.wss.module.wan.ui.main.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Template;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.main.mvp.contract.HomeContract;
import com.wss.module.wan.ui.main.mvp.model.HomeModel;
import com.wss.module.wan.ui.project.ProjectActivity;
import com.wss.module.wan.ui.search.SearchActivity;
import com.wss.module.wan.ui.series.SeriesActivity;
import com.wss.module.wan.ui.wxnumber.WXNumberActivity;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Describe：首页Presenter
 * Created by 吴天强 on 2018/10/17.
 */

public class HomePresenter extends BasePresenter<HomeModel, HomeContract.View> implements
        HomeContract.Presenter {

    @Override
    protected HomeModel createModule() {
        return new HomeModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getBanner();
        getMenuList();
        getWxNumber();
        getArticleList();
    }

    /**
     * 获取Banner
     */
    private void getBanner() {
        getModel().getBanner().subscribe(bannerInfos -> getView().refreshBannerList(bannerInfos));
    }

    /**
     * 获取菜单
     */
    private void getMenuList() {
        List<Template> list = new ArrayList<>();
        list.add(new Template("系列", R.drawable.wan_icon_3, SeriesActivity.class));
        list.add(new Template("项目", R.drawable.wan_icon_2, ProjectActivity.class));
        list.add(new Template("公众号", R.drawable.wan_icon_1, WXNumberActivity.class));
        list.add(new Template("搜索", R.drawable.wan_icon_4, SearchActivity.class));
        getView().refreshMenuList(list);

    }

    /**
     * 获取微信公众号
     */
    private void getWxNumber() {
//        getModel().getWxNumber().subscribe(wxNumbers -> getView().refreshWxNumber(wxNumbers));
    }


    @Override
    public void getArticleList() {
        getView().showLoading();
        getModel().getArticleList(getView().getPage()).subscribe(
                string -> {
                    dismissLoading();
                    List<Article> datas = JsonUtils.getList(JsonUtils.getString(string, "datas"), Article.class);
                    if (ValidUtils.isValid(datas)) {
                        getView().refreshArticleList(datas);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> getView().onError("", t.getMessage()));
    }
}
