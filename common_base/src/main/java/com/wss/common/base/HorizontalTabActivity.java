package com.wss.common.base;

import android.support.annotation.CallSuper;
import android.support.v4.view.ViewPager;

import com.wss.common.adapter.FragmentPagerAdapter;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.widget.PagerSlidingTabStrip;

import java.util.List;

import butterknife.BindView;

/**
 * Describe：带水平选项卡的Activity
 * Created by 吴天强 on 2018/10/22.
 */

public abstract class HorizontalTabActivity<P extends BasePresenter> extends ActionBarActivity<P> {


    @BindView(R2.id.pst_tab)
    PagerSlidingTabStrip tabStrip;

    @BindView(R2.id.vp_list)
    ViewPager viewPager;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_tab;
    }

    @CallSuper
    @Override
    protected void initView() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), getTabTitles()) {
            @Override
            public BaseFragment getTabFragment() {
                return HorizontalTabActivity.this.getTabFragment();
            }
        });
        tabStrip.setViewPager(viewPager);
    }

    protected abstract List<HorizontalTabTitle> getTabTitles();

    protected abstract BaseFragment getTabFragment();


}
