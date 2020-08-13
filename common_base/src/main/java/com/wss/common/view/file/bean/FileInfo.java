package com.wss.common.view.file.bean;


import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Describe：选择文件
 * Created by 吴天强 on 2019/11/12.
 */
@Getter
@Setter
@ToString
public class FileInfo extends BaseBean {
    private String fileName;
    private String filePath;
    private long fileSize;
    private String time;
    private boolean checked;
    /**
     * 文件类型  Constants.FileType
     */
    private int type;
}
