package com.wss.module.market.bean;

import com.wss.common.base.bean.BaseBean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：供应商
 * Created by 吴天强 on 2018/11/6.
 */

@Getter
@Setter
public class Vendor extends BaseBean {

    private String vendorId;//供应商ID
    private String vendorName;//供应商名称
    private List<GoodsInfo> goodsInfos;
    private boolean checked;


}
