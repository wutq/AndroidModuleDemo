package com.wss.module.main.ui.home.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;
import com.wss.module.main.bean.MainBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：中间Presenter
 * Created by 吴天强 on 2018/10/22.
 */

public class CenterPresenter extends BasePresenter<CenterModule, ICenterView> {


    public void getTabList() {
        if (isViewAttached()) {

            List<MainBlock> list = new ArrayList<>();
            list.add(new MainBlock("水平Tab", R.drawable.main_icon_8, "水平滑动选项卡"));
            list.add(new MainBlock("下拉刷新", R.drawable.main_icon_9, "下拉刷新，上拉加载更多，可自定义加载View"));
            list.add(new MainBlock("多功能选择器", R.drawable.main_icon_10, "时间、日期、地址、自定义数据滑动选择器"));
            list.add(new MainBlock("数量加减控件", R.drawable.main_icon_11, "数量加减、支持手动输入"));
            getView().tabList(list);
        }
    }


    @Override
    protected CenterModule createModule() {
        return new CenterModule();
    }
}
