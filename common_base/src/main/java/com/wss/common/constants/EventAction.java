package com.wss.common.constants;

/**
 * Describe：EventBus事件
 * Created by 吴天强 on 2018/10/19.
 */
public interface EventAction {

    //************************************Common模块****************************************/
    /**
     * 删除画廊的图片， Action Data = 删除图片的position
     */
    String IMAGE_GALLERY_DELETE = "image_gallery_delete";
    /**
     * 二维码扫描结果 Action Data = String
     */
    String SCAN_QR_CODE_RESULT = "scan_qr_code_result";



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
    String EVENT_LOGIN_SUCCESS = "event_login_success";

    /**
     * 注册成功
     */
    String EVENT_REGISTER_SUCCESS = "event_register_success";
}
