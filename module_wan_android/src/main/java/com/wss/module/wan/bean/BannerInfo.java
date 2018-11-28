package com.wss.module.wan.bean;

import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：Banner实体类
 * Created by 吴天强 on 2018/10/17.
 */
@Getter
@Setter
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

}
