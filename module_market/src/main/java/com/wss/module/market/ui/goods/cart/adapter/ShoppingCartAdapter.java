package com.wss.module.market.ui.goods.cart.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.ImageUtils;
import com.wss.common.widget.CountClickView;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.bean.Vendor;
import com.wss.module.market.utils.ShoppingCartUtils;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe：购物车适配器
 * Created by 吴天强 on 2018/11/5.
 */

public class ShoppingCartAdapter extends BaseListAdapter<Vendor> implements OnClickListener {

    private boolean isEdit;

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    public ShoppingCartAdapter(Context context, List<Vendor> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Vendor data) {
        holder.setText(R.id.tv_vendor_name, data.getVendorName());
        LinearLayout goodsLayout = holder.findViewById(R.id.goods_list);
        ImageView ivVendor = holder.findViewById(R.id.iv_check_vendor);
        ivVendor.setSelected(data.isChecked());
        ivVendor.setTag(layoutPosition);
        ivVendor.setOnClickListener(this);
        goodsLayout.removeAllViews();

        for (int i = 0; i < data.getGoodsInfos().size(); i++) {
            View view = View.inflate(getContext(), R.layout.market_item_of_shopping_cart_list_goods_list, null);
            new ViewHolder(view).onBindData(layoutPosition, i);
            goodsLayout.addView(view);
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_check_vendor) {
            //供应商被点击了
            ShoppingCartUtils.checkVendor(getData(), (Integer) v.getTag());
            updateCart();
        } else if (v.getId() == R.id.iv_goods_check) {
            //商品被点击了
            String[] tag = ((String) v.getTag()).split(",");
            ShoppingCartUtils.checkGoods(getData(), Integer.parseInt(tag[0]), Integer.parseInt(tag[1]));
            updateCart();
        }
    }


    /**
     * 商品View
     */
    public class ViewHolder {
        @BindView(R2.id.iv_goods)
        ImageView ivGoods;

        @BindView(R2.id.iv_goods_check)
        ImageView ivGoodsCheck;

        @BindView(R2.id.tv_goods_name)
        TextView tvGoodsName;

        @BindView(R2.id.tv_price)
        TextView tvPrice;

        @BindView(R2.id.ccv_click)
        CountClickView ccvClick;


        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        void onBindData(final int parent, final int child) {
            final GoodsInfo goodsInfo = getData().get(parent).getGoodsInfos().get(child);

            ImageUtils.loadImage(ivGoods, goodsInfo.getGoodsMasterImg());
            ivGoodsCheck.setSelected(goodsInfo.isChecked());
            ivGoodsCheck.setTag(parent + "," + child);
            ivGoodsCheck.setOnClickListener(ShoppingCartAdapter.this);
            tvGoodsName.setText(goodsInfo.getGoodsName());
            tvPrice.setText(String.format("¥%s", goodsInfo.getGoodsPrice()));
            ccvClick.setMinCount(1);
            ccvClick.setMaxCount(99);
            ccvClick.setInput(true);
            ccvClick.setCurrCount(goodsInfo.getNum() < 1 ? 1 : goodsInfo.getNum());
            ccvClick.setAfterClickListener(new CountClickView.OnClickAfterListener() {
                @Override
                public void onAfter(int value) {
                    //修改源数据
                    getData().get(parent).getGoodsInfos().get(child).setNum(value);
                    //修改本地数据库数据
                    goodsInfo.setNum(value);
                    ShoppingCartUtils.updateCartGoodsNum(goodsInfo);
                    updateCart();
                }

                @Override
                public void onMin() {

                }
            });
            tvPrice.setVisibility(isEdit ? View.GONE : View.VISIBLE);
            ccvClick.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        }
    }

    private void updateCart() {
        //刷新购物车
        EventBusUtils.sendEvent(new Event(EventAction.EVENT_SHOPPING_CART_CHANGED));
    }
}
