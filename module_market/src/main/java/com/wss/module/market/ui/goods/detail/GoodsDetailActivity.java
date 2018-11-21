package com.wss.module.market.ui.goods.detail;

import android.view.View;
import android.widget.TextView;

import com.wss.common.adapter.FragmentPagerAdapter;
import com.wss.common.base.BaseActivity;
import com.wss.common.base.BaseFragment;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.widget.NoScrollViewPager;
import com.wss.common.widget.PagerSlidingTabStrip;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.ui.goods.detail.fragment.GoodsInfoDetailMainFragment;
import com.wss.module.market.ui.goods.detail.fragment.GoodsInfoMainFragment;
import com.wss.module.market.ui.goods.detail.fragment.child.GoodsCommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：商品详情 Activity
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsDetailActivity extends BaseActivity {

    @BindView(R2.id.psts_tabs)
    public PagerSlidingTabStrip pstsTabs;

    @BindView(R2.id.tv_title)
    public TextView tvTitle;

    @BindView(R2.id.vp_content)
    public NoScrollViewPager vpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.market_activity_goods_details;
    }

    @Override
    protected void initView() {
        List<HorizontalTabTitle> title = new ArrayList<>();
        title.add(new HorizontalTabTitle("商品"));
        title.add(new HorizontalTabTitle("详情"));
        title.add(new HorizontalTabTitle("评价"));


        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new GoodsInfoMainFragment());
        fragmentList.add(new GoodsInfoDetailMainFragment());
        fragmentList.add(new GoodsCommentFragment());
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
        vpContent.setOffscreenPageLimit(3);
        pstsTabs.setViewPager(vpContent);
    }

    @OnClick(R2.id.ll_back)
    public void onClick(View v) {
        finish();
    }

    public void setViewContent(boolean scrollToBottom) {
        if (scrollToBottom) {
            // 图文详情
            vpContent.setNoScroll(true);
            tvTitle.setVisibility(View.VISIBLE);
            pstsTabs.setVisibility(View.GONE);
        } else {
            //商品详情
            vpContent.setNoScroll(false);
            tvTitle.setVisibility(View.GONE);
            pstsTabs.setVisibility(View.VISIBLE);
        }
    }

    public void setCurrentFragment(int position) {
        vpContent.setCurrentItem(position);
    }
}
