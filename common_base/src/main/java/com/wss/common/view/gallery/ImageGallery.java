package com.wss.common.view.gallery;


import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：缩放图片Bean
 * Created by 吴天强 on 2019/7/31.
 */
@Getter
@Setter
public class ImageGallery extends BaseBean {
    /**
     * 图片描述
     */
    private String describe;
    /**
     * 图片地址 如果是本地图片 则是图片文件的绝对路径
     */
    private String url;

    public ImageGallery() {
    }

    public ImageGallery(String url) {
        this.url = url;
    }

    public ImageGallery(String describe, String url) {
        this.describe = describe;
        this.url = url;
    }
}
