package com.wss.module.main.ui.main.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Template;
import com.wss.common.constants.ARouterConfig;
import com.wss.module.main.R;
import com.wss.module.main.ui.hortab.OrderListActivity;
import com.wss.module.main.ui.im.IMActivity;
import com.wss.module.main.ui.page.CountViewActivity;
import com.wss.module.main.ui.page.DialogActivity;
import com.wss.module.main.ui.page.FlowLayoutActivity;
import com.wss.module.main.ui.page.MultipleItemActivity;
import com.wss.module.main.ui.page.ObserverButtonActivity;
import com.wss.module.main.ui.page.ScaleImageActivity;
import com.wss.module.main.ui.refresh.RefreshStringActivity;
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

            List<Template> list = new ArrayList<>();
            list.add(new Template("商城", R.drawable.main_icon_17, ARouterConfig.MARKET_MAIN_ACTIVITY, 1, "电商商品模板，和JD相似"));
            list.add(new Template("水平Tab", R.drawable.main_icon_8, OrderListActivity.class, "水平滑动选项卡"));
            list.add(new Template("下拉刷新", R.drawable.main_icon_9, RefreshStringActivity.class, "下拉刷新，上拉加载更多，可自定义加载View"));
            list.add(new Template("多功能选择器", R.drawable.main_icon_10, SelectorActivity.class, "时间、日期、地址、自定义数据滑动选择器"));
            list.add(new Template("数量加减控件", R.drawable.main_icon_11, CountViewActivity.class, "数量加减、支持手动输入"));
            list.add(new Template("多功能横向Item", R.drawable.main_icon_12, MultipleItemActivity.class, "左右文字+Icon的Item  支持右输入框显示"));
            list.add(new Template("观察者按钮", R.drawable.main_icon_13, ObserverButtonActivity.class, "利用按钮对多个输入框监听，如：注册"));
            list.add(new Template("多功能对话框", R.drawable.main_icon_14, DialogActivity.class, "常用对话框，支持输入 单按钮等"));
            list.add(new Template("流式布局", R.drawable.main_icon_15, FlowLayoutActivity.class, "流式布局，从左上角位置开始，自动换行"));
            list.add(new Template("聊天", R.drawable.main_icon_16, IMActivity.class, "聊天布局样式，用于测试多功能列表适配器"));
            list.add(new Template("图片查看器", R.drawable.main_icon_18, ScaleImageActivity.class, "大图查看器、支持下载、删除、缩放等操作"));
            getView().tabList(list);
        }
    }


    @Override
    protected CenterModule createModule() {
        return new CenterModule();
    }
}
