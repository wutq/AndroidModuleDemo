package com.wss.module.wan.bean;

import com.wss.common.base.bean.BaseBean;

/**
 * Describe：Banner实体类
 * Created by 吴天强 on 2018/10/17.
 */

public class BannerInfo extends BaseBean {


    /**
     * desc :
     * id : 18
     * imagePath : http://www.wanandroid.com/blogimgs/00f83f1d-3c50-439f-b705-54a49fc3d90d.jpg
     * isVisible : 1
     * order : 0
     * title : 公众号文章列表强势上线
     * type : 0
     * url : http://www.wanandroid.com/wxarticle/list/409/1
     */

    private int id;
    private String title;
    private String desc;
    private String imagePath;
    private int isVisible;
    private int order;
    private int type;
    private String url;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
