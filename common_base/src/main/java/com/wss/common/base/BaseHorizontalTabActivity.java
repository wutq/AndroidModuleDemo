package com.wss.common.base;


import com.wss.common.adapter.FragmentPagerAdapter;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.widget.PagerSlidingTabStrip;

import java.util.List;

import androidx.annotation.CallSuper;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Describe：带水平选项卡的Activity
 * Created by 吴天强 on 2018/10/22.
 */
public abstract class BaseHorizontalTabActivity<P extends BasePresenter> extends BaseActionBarActivity<P> {

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
                return BaseHorizontalTabActivity.this.getTabFragment();
            }
        });
        tabStrip.setViewPager(viewPager);
    }

    /**
     * 选项卡List
     *
     * @return List<HorizontalTabTitle>
     */
    protected abstract List<HorizontalTabTitle> getTabTitles();

    /**
     * 滑动Fragment
     *
     * @return BaseFragment
     */
    protected abstract BaseFragment getTabFragment();

}
