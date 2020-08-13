package com.wss.common.bean;


import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * description: 房源首页Banner实体
 *
 * @author 杨伟-tony
 * create by 2020/5/21 20:40
 */
@Getter
@Setter
public class Banner extends BaseBean {
    /**
     * 图片链接
     */
    private String imageUrl;
    /**
     * 跳转链接
     */
    private String redirectUrl;

    public Banner(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Banner(String imageUrl, String redirectUrl) {
        this.imageUrl = imageUrl;
        this.redirectUrl = redirectUrl;
    }
}
