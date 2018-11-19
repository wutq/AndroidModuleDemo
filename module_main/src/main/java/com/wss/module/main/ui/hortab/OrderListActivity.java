package com.wss.module.main.ui.hortab;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.BaseFragment;
import com.wss.common.base.HorizontalTabActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.constants.ARouterConfig;
import com.wss.module.main.ui.hortab.fragment.OrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：水平滑动Tab
 * Created by 吴天强 on 2018/10/22.
 */
@Route(path = ARouterConfig.MAIN_ORDER_LIST)
public class OrderListActivity extends HorizontalTabActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleText("我的订单");
    }

    @Override
    protected List<HorizontalTabTitle> getTabTitles() {
        List<HorizontalTabTitle> titles = new ArrayList<>();
        titles.add(new HorizontalTabTitle<>("全部", 0));
        titles.add(new HorizontalTabTitle<>("待支付", 1));
        titles.add(new HorizontalTabTitle<>("待发货", 2));
        titles.add(new HorizontalTabTitle<>("待确认", 3));
        titles.add(new HorizontalTabTitle<>("已失效", -1));
        return titles;
    }

    @Override
    protected BaseFragment getTabFragment() {
        return new OrderFragment();
    }


}
