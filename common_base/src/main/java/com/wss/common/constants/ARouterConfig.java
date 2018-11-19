package com.wss.common.constants;

/**
 * Describe：路由页面常量配置 注意：路径至少需要两级 {/xx/xx}
 * Created by 吴天强 on 2018/10/16.
 */

public interface ARouterConfig {

    //************************************Main模块*****************************************/
    /**
     * 订单列表
     */
    String MAIN_ORDER_LIST = "/main/OrderListActivity";

    //*************************************玩安卓模块*****************************************/
    /**
     * 玩安卓首页
     */
    String WAN_MAIN_FRAGMENT = "/wan/HomeFragment";
    /**
     * 我的收藏
     */
    String WAN_COLLECTION = "/wan/CollectionActivity";


    //*************************************商城模块******************************************/
    /**
     * 商场首页
     */
    String MARKET_MAIN_ACTIVITY = "/market/MarketMainActivity";


    //*************************************User模块*****************************************/
    /**
     * 用户模块
     */
    String USER_MAIN_FRAGMENT = "/user/UserFragment";
    /**
     * 登录
     */
    String USER_LOGIN = "/user/LoginActivity";


}
