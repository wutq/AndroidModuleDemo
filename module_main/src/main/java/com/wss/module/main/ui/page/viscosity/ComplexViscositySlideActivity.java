package com.wss.module.main.ui.page.viscosity;

import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wss.common.adapter.FragmentPagerAdapter;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.BaseFragment;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Banner;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.widget.PagerSlidingTabStrip;
import com.wss.common.widget.ViewPagerForScrollView;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.viscosity.bean.TabItem;
import com.wss.module.main.ui.page.viscosity.fragment.ChildListFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * Describe：复杂黏性滑动
 * Created by 吴天强 on 2020/8/13.
 */
public class ComplexViscositySlideActivity extends BaseActionBarActivity {

    @BindView(R2.id.top_banner)
    ConvenientBanner<Banner> topBanners;

    @BindView(R2.id.pst_tab)
    PagerSlidingTabStrip pstTab;

    @BindView(R2.id.middle_banner)
    ImageView middleBanner;

    @BindView(R2.id.viewPager)
    ViewPagerForScrollView viewPager;

    @BindView(R2.id.pull_to_refresh)
    SmartRefreshLayout pullToRefreshLayout;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_complex_viscosity_slide;
    }

    @Override
    protected void initView() {
        setCenterText("复杂黏性滑动");
        viewPager.setMinHeight(PxUtils.getScreenHeight(context) - PxUtils.dp2px(170));
        //禁用外层Fragment加载更多功能，交给具体每个子Fragment来操作
        pullToRefreshLayout.setEnableLoadMore(false);
        pullToRefreshLayout.setOnRefreshListener(refreshLayout -> {
            //下拉刷新，相当于个页面重新重构
            initBanner();
            initTabView();
        });
        initBanner();
        initTabView();
    }

    /**
     * 初始化TabView
     */
    private void initTabView() {
        List<HorizontalTabTitle> tabTitleList = new ArrayList<>();
        tabTitleList.add(new HorizontalTabTitle<>("高渐离", new TabItem(10, 0)));
        tabTitleList.add(new HorizontalTabTitle<>("辛弃疾", new TabItem(11, 1)));
        tabTitleList.add(new HorizontalTabTitle<>("柳宗元", new TabItem(12, 2)));
        tabTitleList.add(new HorizontalTabTitle<>("张之洞", new TabItem(13, 3)));

        viewPager.setOffscreenPageLimit(tabTitleList.size());
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), tabTitleList) {
            @NotNull
            @Override
            public BaseFragment getTabFragment() {
                return ChildListFragment.newInstance(viewPager);
            }
        };
        try {
            viewPager.setAdapter(fragmentPagerAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                viewPager.setScroll(true);

            }

            @Override
            public void onPageSelected(int position) {
                //切换页面重新计算ViewPager的高度
                viewPager.resetHeight(position);
                viewPager.setScroll(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pstTab.setViewPager(viewPager);
        stopRefresh(pullToRefreshLayout);
    }

    /**
     * 初始化Banner
     */
    private void initBanner() {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597401112079&di=e423a1a4356794e5758aced9191ec5d8&imgtype=0&src=http%3A%2F%2Fdpic.tiankong.com%2Fjg%2Flw%2FQJ8876084221.jpg";

        List<Banner> topBanner = new ArrayList<>();
        topBanner.add(new Banner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597323934304&di=c190243926ba5d4efd6db79df2fa33d4&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20150205%2Fmp1204595_1423121484082_2_th_fv23.jpeg"));
        topBanner.add(new Banner("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1385848743,443183150&fm=26&gp=0.jpg"));
        topBanner.add(new Banner("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597323969074&di=f382fe44b9da6fc0e889c794118387c6&imgtype=0&src=http%3A%2F%2Fimg.improve-yourmemory.com%2Fpic%2F585996137799f8e9868acfaaeda61258-0.jpg"));
        topBanner.add(new Banner(url));
        ImageUtils.loadBanner(topBanners, topBanner, true, position -> {

        });
        ImageUtils.loadImageCircleBead(middleBanner, url, 4);
    }

}
