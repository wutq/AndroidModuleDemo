package com.wss.module.main.ui.hortab.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.bean.Goods;
import com.wss.module.main.bean.Order;
import com.wss.module.main.ui.hortab.mvp.contract.OrderContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Describe：订单Presenter
 * Created by 吴天强 on 2018/10/23.
 */

public class OrderPresenter extends BasePresenter<OrderContract.Module, OrderContract.View> implements OrderContract.Presenter {


    @Override
    public void getOrderList() {
        if (isViewAttached()) {

            if (getView().getType() == 1) {
                //Error
                getView().onError("", "");
            } else if (getView().getType() == 2) {
                getView().onEmpty("");
            } else {
                if (getView().getPage() == 3) {
                    //到第三页告诉他没有数据了
                    getView().onEmpty("");
                } else {
                    getView().orderList(getData());
                    getView().dismissLoading();
                }
            }

        }

    }

    private List<Order> getData() {
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setOrderDate("2018年10月23日18:39:41");
            order.setOrderTotal(String.valueOf(6.28 * (i + 1)));
            order.setGoodsList(getGoods());
            order.setOrderState(getView().getType() != 0 ? getView().getType() : new Random().nextInt(4) + 1);
            list.add(order);
        }
        return list;
    }

    private List<Goods> getGoods() {
        List<Goods> list = new ArrayList<>();
        int max = new Random().nextInt(5) + 1;
        for (int i = 1; i <= max; i++) {
            Goods goods = new Goods();
            goods.setGoodsName(String.format("这是第%s页的第%s个商品", getView().getPage() + 1, i));
            goods.setGoodsNum(i * max + 1);
            goods.setGoodsPrice(String.valueOf(3 * max + 0.28));
            goods.setGoodsImg("https://img14.360buyimg.com/n7/jfs/t20509/311/508682869/290069/4d2c41e/5b0fcab1N217bcf3d.jpg");
            list.add(goods);
        }

        return list;
    }

    @Override
    protected OrderContract.Module createModule() {
        return null;
    }

    @Override
    public void start() {
        getOrderList();
    }

}
