package com.wss.module.main.ui.home.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;
import com.wss.module.main.bean.MainBlock;
import com.wss.module.main.ui.count.CountViewActivity;
import com.wss.module.main.ui.dialog.DialogActivity;
import com.wss.module.main.ui.flow.FlowLayoutActivity;
import com.wss.module.main.ui.hortab.OrderListActivity;
import com.wss.module.main.ui.item.MultipleItemActivity;
import com.wss.module.main.ui.observer.ObserverButtonActivity;
import com.wss.module.main.ui.refresh.ArticleListActivity;
import com.wss.module.main.ui.selector.SelectorActivity;

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
            list.add(new MainBlock("水平Tab", R.drawable.main_icon_8, OrderListActivity.class, "水平滑动选项卡"));
            list.add(new MainBlock("下拉刷新", R.drawable.main_icon_9, ArticleListActivity.class, "下拉刷新，上拉加载更多，可自定义加载View"));
            list.add(new MainBlock("多功能选择器", R.drawable.main_icon_10, SelectorActivity.class, "时间、日期、地址、自定义数据滑动选择器"));
            list.add(new MainBlock("数量加减控件", R.drawable.main_icon_11, CountViewActivity.class, "数量加减、支持手动输入"));
            list.add(new MainBlock("多功能横向Item", R.drawable.main_icon_12, MultipleItemActivity.class, "左右文字+Icon的Item  支持右输入框显示"));
            list.add(new MainBlock("观察者按钮", R.drawable.main_icon_13, ObserverButtonActivity.class, "利用按钮对多个输入框监听，如：注册、修改信息等"));
            list.add(new MainBlock("自定义多功能对话框", R.drawable.main_icon_14, DialogActivity.class, "开发中可能会遇到的对话框封装，支持输入 单按钮等"));
            list.add(new MainBlock("流式布局", R.drawable.main_icon_15, FlowLayoutActivity.class, "流式布局，从左上角位置开始，自动换行"));
            getView().tabList(list);
        }
    }


    @Override
    protected CenterModule createModule() {
        return new CenterModule();
    }
}
