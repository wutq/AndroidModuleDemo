package com.wss.module.wan.ui.wxnumber;

import com.wss.common.adapter.FragmentPagerAdapter;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.BaseFragment;
import com.wss.common.base.R2;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.widget.PagerSlidingTabStrip;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.WXNumber;
import com.wss.module.wan.ui.wxnumber.fragment.WXArticleFragment;
import com.wss.module.wan.ui.wxnumber.mvp.WXNumberPresenter;
import com.wss.module.wan.ui.wxnumber.mvp.contract.WXNumberContract;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Describe：微信公众号
 * Created by 吴天强 on 2018/11/15.
 */
public class WXNumberActivity extends BaseActionBarActivity<WXNumberPresenter> implements WXNumberContract.View {

    @BindView(R2.id.pst_tab)
    PagerSlidingTabStrip tabStrip;

    @BindView(R2.id.vp_list)
    ViewPager viewPager;


    @Override
    protected WXNumberPresenter createPresenter() {
        return new WXNumberPresenter();
    }

    @Override
    public void refreshWxNumber(List<WXNumber> numberList) {
        List<HorizontalTabTitle> tabTitleList = new ArrayList<>();
        for (WXNumber wx : numberList) {
            tabTitleList.add(new HorizontalTabTitle<>(wx.getName(), wx));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), tabTitleList) {
            @Override
            public BaseFragment getTabFragment() {
                return new WXArticleFragment();
            }
        });
        tabStrip.setViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_tab;
    }

    @Override
    protected void initView() {
        setCenterText("微信公众号");
        getPresenter().start();
    }
}
