package com.wss.module.main.ui.home.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.Constant;
import com.wss.common.net.callback.OnResultListCallBack;
import com.wss.module.main.R;
import com.wss.module.main.bean.BannerInfo;
import com.wss.module.main.bean.MainBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：首页Presenter
 * Created by 吴天强 on 2018/10/17.
 */

public class HomePresenter extends BasePresenter<HomeModule, IHomeView> {


    public void getBanner() {
        if (isViewAttached()) {
            getView().showLoading();
            getModule().getBanner(getView().getContext(),
                    new OnResultListCallBack<List<BannerInfo>>() {

                        @Override
                        public void onSuccess(boolean success, int code, String msg, Object tag, List<BannerInfo> response) {
                            if (code == 0) {
                                if (response != null && response.size() > 0) {
                                    getView().bannerList(response);
                                } else {
                                    getView().onEmpty(tag);
                                }
                            } else {
                                getView().onError(tag, msg);
                            }
                        }

                        @Override
                        public void onFailure(Object tag, Exception e) {
                            getView().onError(tag, Constant.ERROR_MESSAGE);
                        }

                        @Override
                        public void onCompleted() {
                            getView().dismissLoading();
                        }
                    });
        }
    }

    public void getBlock() {
        if (isViewAttached()) {
            getView().showLoading();
            //模拟请求数据
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(10);
                        List<MainBlock> list = new ArrayList<>();
                        list.add(new MainBlock("玩安卓", ARouterConfig.WAN_MAIN_ACTIVITY, R.drawable.main_icon_1));
                        list.add(new MainBlock("商城", ARouterConfig.MARKET_MAIN_ACTIVITY, R.drawable.main_icon_4));
                        list.add(new MainBlock("花花", "https://www.jianshu.com/p/8703343be072", R.drawable.main_icon_3));
                        list.add(new MainBlock("树木", "https://www.jianshu.com/p/fadfd7965865", R.drawable.main_icon_2));
                        list.add(new MainBlock("草草", "https://www.jianshu.com/p/3977a1c2d07b", R.drawable.main_icon_6));
                        list.add(new MainBlock("猫猫", "https://www.jianshu.com/p/bce49dccc962", R.drawable.main_icon_5));
                        getView().blockList(list);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    @Override
    protected HomeModule createModule() {
        return new HomeModule();
    }
}
