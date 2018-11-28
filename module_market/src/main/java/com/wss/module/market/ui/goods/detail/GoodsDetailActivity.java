package com.wss.module.market.ui.goods.detail;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wss.common.adapter.FragmentPagerAdapter;
import com.wss.common.base.BaseActivity;
import com.wss.common.base.BaseFragment;
import com.wss.common.bean.Event;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.ActivityToActivity;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.NoScrollViewPager;
import com.wss.common.widget.PagerSlidingTabStrip;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.ui.goods.cart.ShoppingCartActivity;
import com.wss.module.market.ui.goods.detail.fragment.GoodsInfoDetailMainFragment;
import com.wss.module.market.ui.goods.detail.fragment.GoodsInfoMainFragment;
import com.wss.module.market.ui.goods.detail.fragment.child.GoodsCommentFragment;
import com.wss.module.market.utils.ShoppingCartUtils;

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

    @BindView(R2.id.tv_count)
    TextView tvCount;//购物车数量

    //TODO 测试使用列表造的伪数据表示不同商品做加入购物车操作
    private GoodsInfo goodsInfo;

    private GoodsInfoMainFragment goodsInfoMainFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.market_activity_goods_details;
    }

    @Override
    protected void initView() {
        if (getIntent() != null) {
            goodsInfo = (GoodsInfo) getIntent().getSerializableExtra("GoodsInfo");
        }

        List<HorizontalTabTitle> title = new ArrayList<>();
        title.add(new HorizontalTabTitle("商品"));
        title.add(new HorizontalTabTitle("详情"));
        title.add(new HorizontalTabTitle("评价"));


        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(goodsInfoMainFragment = new GoodsInfoMainFragment());
        fragmentList.add(new GoodsInfoDetailMainFragment());
        fragmentList.add(new GoodsCommentFragment());
        vpContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), title, fragmentList));
        vpContent.setOffscreenPageLimit(3);
        pstsTabs.setViewPager(vpContent);

        setCartNumber();
    }

    /**
     * 设置内容
     */
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

    @Override
    protected boolean regEvent() {
        return true;
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (TextUtils.equals(EventAction.EVENT_SHOPPING_CART_REFRESH, event.getAction())) {
            setCartNumber();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartNumber();
    }

    @OnClick({R2.id.ll_back, R2.id.rl_cart, R2.id.tv_add_cart, R2.id.tv_buy_now})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ll_back) {
            finish();
        } else if (i == R.id.rl_cart) {
            //去购物车
            ActivityToActivity.toActivity(mContext, ShoppingCartActivity.class);
        } else if (i == R.id.tv_add_cart) {
            //加入购物车
            if (goodsInfo != null) {
                goodsInfo.setNum(goodsInfoMainFragment.getGoodsCount());
                ShoppingCartUtils.addCartGoods(goodsInfo);
                EventBusUtils.sendEvent(new Event(EventAction.EVENT_SHOPPING_CART_REFRESH));
            }else {
                ToastUtils.showToast(mContext,"没有正经的商品信息~");
            }
        } else if (i == R.id.tv_buy_now) {
            //立即购买
            ToastUtils.showToast(mContext, "立即购买");
        }
    }

    /**
     * 设置购物车数量
     */
    private void setCartNumber() {
        int count = ShoppingCartUtils.getCartCount();
        if (count < 1) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(String.valueOf(count));
        }
    }

    /**
     * 切换Fragment
     *
     * @param position position
     */
    public void setCurrentFragment(int position) {
        vpContent.setCurrentItem(position);
    }

}
