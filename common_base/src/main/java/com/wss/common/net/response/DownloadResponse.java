package com.wss.common.net.response;


import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;
import rxhttp.wrapper.entity.Progress;

/**
 * Describe：下载文件响应
 * Created by 吴天强 on 2021/5/10.
 */
@Getter
@Setter
public class DownloadResponse extends BaseBean {
    /**
     * 下载文件进度对象
     */
    private Progress progress;
    /**
     * 是否下载完成
     */
    private boolean success;
}
