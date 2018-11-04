package com.wss.module.market.bean;


import lombok.Getter;
import lombok.Setter;

/**
 * Describe：商品信息中的规格参数bean
 * Created by 吴天强 on 2018/11/1.
 */

@Getter
@Setter
public class GoodsConfigBean {
    private int keyPropId;
    /**
     * 参数key
     */
    private String keyProp;
    private int valueId;
    /**
     * 参数value
     */
    private String value;

    public GoodsConfigBean(String keyProp, String value) {
        this.keyProp = keyProp;
        this.value = value;
    }
}
