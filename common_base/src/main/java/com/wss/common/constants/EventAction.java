package com.wss.common.constants;

/**
 * Describe：EventBus事件
 * Created by 吴天强 on 2018/10/19.
 */

public interface EventAction {

    /**
     * 商城商品被点击
     */
    String EVENT_MARKET_CLICK = "event_market_click";

    /**
     * 刷新购物车
     */
    String EVENT_SHOPPING_CART_REFRESH = "event_shopping_cart_refresh";
    /**
     * 清空购物车
     */
    String EVENT_SHOPPING_CART_CLEAN = "event_shopping_cart_clean";

    /**
     * 登录成功
     */
    String EVENT_LOGIN_SUCCESS = "EVENT_LOGIN_SUCCESS";

    /**
     * 注册成功
     */
    String EVENT_REGISTER_SUCCESS = "EVENT_REGISTER_SUCCESS";
}
