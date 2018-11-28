package com.wss.module.market.utils;

import android.text.TextUtils;

import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.BeanCopyUtils;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.GroupUtils;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.bean.Vendor;
import com.wss.module.market.db.MarketDBFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe：购物车帮助类
 * Created by 吴天强 on 2018/11/5.
 */

public class ShoppingCartUtils {

    /**
     * 获取购物车本地数据
     */
    public static List<Vendor> getLocalData() {
        List<GoodsInfo> goodsInfoList = MarketDBFactory.getInstance()
                .getGoodsInfoManage()
                .queryAll();

        //分组后的购物车数据
        List<Vendor> result = new ArrayList<>();

        Map<String, List<GoodsInfo>> map = new LinkedHashMap<>();
        GroupUtils.listGroup2Map(goodsInfoList, map, GoodsInfo.class, "getVendorId");
        for (String key : map.keySet()) {
            Vendor vendor = new Vendor();
            vendor.setVendorId(key);
            //这里伪数据  设置商品的供应商
            vendor.setVendorName(TextUtils.equals(key, "1") ? "小米之家" : "龙门客栈");
            vendor.setGoodsInfos(map.get(key));
            result.add(vendor);
        }
        return result;
    }

    /**
     * 添加商品到购物车
     *
     * @param goods goods
     */
    public static void addCartGoods(GoodsInfo goods) {
        addOrUpdateCartGoods(goods, false);
    }

    /**
     * 更新或添加商品到购物车
     *
     * @param goods     goods
     * @param updateNum 是否是修改商品数量
     */
    private static void addOrUpdateCartGoods(GoodsInfo goods, boolean updateNum) {
        if (goods == null) {
            return;
        }
        GoodsInfo goodsInfo = BeanCopyUtils.copy(goods);

        if (goodsInfo.getNum() < 1) {
            goodsInfo.setNum(1);
        }
        if (!updateNum) {
            GoodsInfo query = MarketDBFactory.getInstance()
                    .getGoodsInfoManage()
                    .query(goodsInfo.getGoodsId());
            if (query != null) {
                goodsInfo.setNum(query.getNum() + goodsInfo.getNum());
            }
        }
        MarketDBFactory.getInstance()
                .getGoodsInfoManage()
                .insertOrUpdate(goodsInfo);
    }

    /**
     * 修改购物车商品数量
     */
    public static void updateCartGoodsNum(GoodsInfo goods) {
        addOrUpdateCartGoods(goods, true);
    }

    /**
     * 获取购物车商品总数
     *
     * @return count
     */
    public static int getCartCount() {
        List<GoodsInfo> goodsInfoList = MarketDBFactory.getInstance()
                .getGoodsInfoManage()
                .queryAll();
        int count = 0;
        for (GoodsInfo info : goodsInfoList) {
            count += info.getNum();
        }
        return count;
    }

    /**
     * 获取购物车商品总价
     *
     * @return count
     */
    public static double getCartCountPrice(List<Vendor> vendors) {

        double price = 0.0;
        for (GoodsInfo info : getAllCheckedGoods(vendors)) {
            price += info.getNum() * Double.valueOf(info.getGoodsPrice());
        }
        return price;
    }

    /**
     * 删除购物车数据
     *
     * @param goodsList goodsList
     */
    public static void delete(List<GoodsInfo> goodsList) {
        MarketDBFactory.getInstance()
                .getGoodsInfoManage()
                .delete(goodsList);
        EventBusUtils.sendEvent(new Event(EventAction.EVENT_SHOPPING_CART_REFRESH));
    }

    //**********************************购物车点击逻辑操作*******************************************

    /**
     * 选择商品
     */
    public static void checkGoods(List<Vendor> vendors, int parent, int child) {
        vendors.get(parent).getGoodsInfos().get(child).setChecked(!vendors.get(parent).getGoodsInfos().get(child).isChecked());
        vendors.get(parent).setChecked(isAllGoodsChecked(vendors.get(parent).getGoodsInfos()));
    }

    /**
     * 选择供应商
     */
    public static void checkVendor(List<Vendor> vendors, int position) {
        vendors.get(position).setChecked(!vendors.get(position).isChecked());
        for (GoodsInfo info : vendors.get(position).getGoodsInfos()) {
            info.setChecked(vendors.get(position).isChecked());
        }
    }

    /**
     * 选择全部
     */
    public static void checkAll(List<Vendor> deliveryInfos, boolean check) {
        for (Vendor deliveryInfo : deliveryInfos) {
            deliveryInfo.setChecked(check);
            for (GoodsInfo info : deliveryInfo.getGoodsInfos()) {
                info.setChecked(check);
            }
        }
    }

    /**
     * 是否所有的商品都已经选择
     */
    private static boolean isAllGoodsChecked(List<GoodsInfo> list) {
        for (GoodsInfo info : list) {
            if (!info.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否所有的供应商已经全选
     */
    public static boolean isAllVendorChecked(List<Vendor> vendors) {
        for (Vendor info : vendors) {
            if (!info.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否至少选择了一个
     */
    public static boolean isCheckedLeastOne(List<Vendor> vendors) {
        for (Vendor deliveryInfo : vendors) {
            if (deliveryInfo.isChecked()) {
                return true;
            }
            for (GoodsInfo info : deliveryInfo.getGoodsInfos()) {
                if (info.isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取购物车中已经选择的商品集合
     */
    public static List<GoodsInfo> getAllCheckedGoods(List<Vendor> vendors) {
        List<GoodsInfo> result = new ArrayList<>();
        for (Vendor deliveryInfo : vendors) {
            if (deliveryInfo.isChecked()) {
                result.addAll(deliveryInfo.getGoodsInfos());
            } else {
                for (GoodsInfo info : deliveryInfo.getGoodsInfos()) {
                    if (info.isChecked()) {
                        result.add(info);
                    }
                }
            }
        }
        return result;
    }

}
