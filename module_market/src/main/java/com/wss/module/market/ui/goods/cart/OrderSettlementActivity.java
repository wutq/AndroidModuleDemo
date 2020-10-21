package com.wss.module.market.ui.goods.cart;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Dic;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.MoneyUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.MultipleItemView;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.Vendor;
import com.wss.module.market.ui.goods.cart.adapter.OrderSettlementGoodsAdapter;
import com.wss.module.market.utils.ShoppingCartUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：结算订单
 * Created by 吴天强 on 2020/10/20.
 */
public class OrderSettlementActivity extends BaseActionBarActivity {

    @BindView(R2.id.tv_no_address)
    TextView tvNoAddress;

    @BindView(R2.id.tv_name)
    TextView tvName;

    @BindView(R2.id.tv_address)
    TextView tvAddress;

    @BindView(R2.id.rv_goods_list)
    RecyclerView rvGoodsList;

    @BindView(R2.id.miv_coupon)
    MultipleItemView mivCoupon;

    @BindView(R2.id.miv_shopping_bi)
    MultipleItemView mivShoppingBi;

    @BindView(R2.id.tv_goods_price)
    TextView tvGoodsPrice;

    @BindView(R2.id.tv_coupon_price)
    TextView tvCouponPrice;

    @BindView(R2.id.rl_coupon_money)
    RelativeLayout rlCouponMoney;

    @BindView(R2.id.rl_shopping_bi_money)
    RelativeLayout rlShoppingBiMoney;

    @BindView(R2.id.tv_shopping_bi)
    TextView tvShoppingBi;

    @BindView(R2.id.tv_total)
    TextView tvTotal;

    @BindView(R2.id.btn_buy)
    TextView btnBuy;

    @BindView(R2.id.rl_bottom)
    RelativeLayout rlBottom;

    private List<Vendor> vendorList = new ArrayList<>();

    private double cartCountPrice;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.market_order_settlement;
    }

    @Override
    protected void initView() {
        setCenterText("结算订单");
        //以JSON的形式传入商品信息
        String goodsJson = getIntent().getStringExtra(Dic.VENDOR_GOODS_INFO);
        vendorList.addAll(JsonUtils.getList(goodsJson, Vendor.class));
        cartCountPrice = ShoppingCartUtils.getCartCountPrice(vendorList);

        rvGoodsList.setLayoutManager(new LinearLayoutManager(context));
        rvGoodsList.setAdapter(new OrderSettlementGoodsAdapter(context, vendorList, (data, position) -> {

            ToastUtils.show("商品清单");
        }));
        mivShoppingBi.setRightIconChangedListener(checked -> {
            if (checked) {
                rlShoppingBiMoney.setVisibility(View.VISIBLE);
                cartCountPrice -= 10.00;
            } else {
                cartCountPrice += 10.00;
                rlShoppingBiMoney.setVisibility(View.GONE);
            }
            setTotal();
        });
        setTotal();
    }

    private void setTotal() {
        //减掉优惠券
        String subtract = MoneyUtils.Algorithm.subtract(String.valueOf(cartCountPrice), "100");
        tvTotal.setText(String.format("合计：%s", subtract));
    }


    @OnClick({R2.id.tv_no_address, R2.id.miv_coupon, R2.id.btn_buy})
    public void onViewClicked(@NotNull View view) {
        int id = view.getId();
        if (id == R.id.tv_no_address) {
            //去添加地址
            ToastUtils.show("去添加地址");
        } else if (id == R.id.miv_coupon) {
            //优惠券
            ToastUtils.show("优惠券页面");
        } else if (id == R.id.btn_buy) {
            //去支付
            ToastUtils.show("去支付");
        }
    }
}
