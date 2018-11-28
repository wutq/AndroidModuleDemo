package com.wss.module.market.bean;

import com.wss.common.base.bean.BaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Describe：商品信息 购物车做了本地化存储
 * Created by 吴天强 on 2018/11/1.
 */

@Entity
public class GoodsInfo extends BaseBean {

    @Id
    private String goodsId;
    private String goodsName;
    private String goodsPrice;//商品当前价格
    private String goodsOldPrice;//老价格
    private String goodsMasterImg;//商品主图
    private String praiseRate;//好评率
    private String commentCount;//用户点评数
    private int num;//数量
    @Transient//该字段不入库
    private boolean checked;//是否选择
    @Transient//该字段不入库
    private List<String> goodsHeadImg;//商品头图
    private String vendorId;//供应商ID


    public GoodsInfo(String goodsId, String goodsName, String goodsMasterImg, String goodsPrice, String goodsOldPrice, String vendorId) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsOldPrice = goodsOldPrice;
        this.goodsMasterImg = goodsMasterImg;
        this.vendorId = vendorId;
    }


    @Generated(hash = 931718353)
    public GoodsInfo(String goodsId, String goodsName, String goodsPrice, String goodsOldPrice, String goodsMasterImg, String praiseRate,
                     String commentCount, int num, String vendorId) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsOldPrice = goodsOldPrice;
        this.goodsMasterImg = goodsMasterImg;
        this.praiseRate = praiseRate;
        this.commentCount = commentCount;
        this.num = num;
        this.vendorId = vendorId;
    }


    @Generated(hash = 1227172248)
    public GoodsInfo() {
    }


    public String getGoodsId() {
        return this.goodsId;
    }


    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }


    public String getGoodsName() {
        return this.goodsName;
    }


    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    public String getGoodsPrice() {
        return this.goodsPrice;
    }


    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }


    public String getGoodsOldPrice() {
        return this.goodsOldPrice;
    }


    public void setGoodsOldPrice(String goodsOldPrice) {
        this.goodsOldPrice = goodsOldPrice;
    }


    public String getGoodsMasterImg() {
        return this.goodsMasterImg;
    }


    public void setGoodsMasterImg(String goodsMasterImg) {
        this.goodsMasterImg = goodsMasterImg;
    }


    public String getPraiseRate() {
        return this.praiseRate;
    }


    public void setPraiseRate(String praiseRate) {
        this.praiseRate = praiseRate;
    }


    public String getCommentCount() {
        return this.commentCount;
    }


    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }


    public int getNum() {
        return this.num;
    }


    public void setNum(int num) {
        this.num = num;
    }


    public String getVendorId() {
        return this.vendorId;
    }


    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<String> getGoodsHeadImg() {
        return goodsHeadImg;
    }

    public void setGoodsHeadImg(List<String> goodsHeadImg) {
        this.goodsHeadImg = goodsHeadImg;
    }
}
