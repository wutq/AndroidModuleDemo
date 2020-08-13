package com.wss.module.wan.ui.wxnumber.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXArticleContract;
import com.wss.module.wan.ui.wxnumber.mvp.model.WXArticleModel;

import java.util.List;

/**
 * Describe：微信文章控制器
 * Created by 吴天强 on 2020/8/13.
 */
public class WXArticlePresenter extends BasePresenter<WXArticleModel, WXArticleContract.View>
        implements WXArticleContract.Presenter {
    @Override
    protected WXArticleModel createModule() {
        return new WXArticleModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        getArticle();
    }

    @Override
    public void getArticle() {
        showLoading();
        getModel().getWxArticle(getView().getWxId(), getView().getPage()).subscribe(
                string -> {
                    dismissLoading();
                    List<Article> datas = JsonUtils.getList(JsonUtils.getString(string, "datas"), Article.class);
                    if (ValidUtils.isValid(datas)) {
                        getView().refreshWxArticle(datas);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> getView().onError("", t.getMessage())
        );
    }
}
