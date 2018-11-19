package com.wss.module.wan.bean;

import com.wss.common.base.bean.BaseBean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：文章
 * Created by 吴天强 on 2018/10/18.
 */

@Getter
@Setter
public class Article extends BaseBean {

    /**
     * apkLink :
     * author : 请叫我大苏
     * chapterId : 228
     * chapterName : 辅助 or 工具类
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 5852
     * link : https://www.jianshu.com/p/6064a14d86a3
     * niceDate : 2018-10-14
     * origin :
     * projectLink :
     * publishTime : 1539513452000
     * superChapterId : 135
     * superChapterName : 项目必备
     * title : 封装个 Android 的高斯模糊组件
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */
    private String apkLink;
    private String author;
    private int chapterId;
    private String chapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String envelopePic;
    private boolean fresh;
    private int id;
    private String link;
    private String niceDate;
    private String origin;
    private String projectLink;
    private long publishTime;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;
    private List<ArticleTag> tags;

    @Getter
    @Setter
    public class ArticleTag extends BaseBean {

        private String name;
        private String url;
    }
}
