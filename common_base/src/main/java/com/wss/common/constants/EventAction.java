package com.wss.common.constants;

/**
 * Describe：EventBus事件
 * Created by 吴天强 on 2018/10/19.
 */

public interface EventAction {


    //************************************Market模块*****************************************/
    /**
     * 购物车有变化
     */
    String EVENT_SHOPPING_CART_CHANGED = "event_shopping_cart_changed";
    /**
     * 刷新购物车 重新获取数据
     */
    String EVENT_SHOPPING_CART_REFRESH = "event_shopping_cart_refresh";

    //************************************User模块*****************************************/
    /**
     * 登录成功
     */
    String EVENT_LOGIN_SUCCESS = "EVENT_LOGIN_SUCCESS";

    /**
     * 注册成功
     */
    String EVENT_REGISTER_SUCCESS = "EVENT_REGISTER_SUCCESS";
}
