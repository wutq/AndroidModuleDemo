package com.wss.common.constants;

import android.content.Context;

import com.wss.common.base.BaseApplication;

/**
 * Describe：常量存放
 * Created by 吴天强 on 2017/10/9.
 */
public interface Constants {
    /**
     * 无法分类的散装常量
     */
    interface Common {
        /**
         * android
         */
        int PLATFORM_ANDROID = 1;

        /**
         * 渠道名称
         */
        String CHANEL = "android";
        /**
         * API 版本
         */
        String API_VERSION = "1.0";
    }


    /**
     * WebView相关配置
     */
    interface WebView {
        String CACHE_DIR = BaseApplication.i().getDir("cache", Context.MODE_PRIVATE).getPath();
    }


    /**
     * 网络请求相关
     */
    interface Net {
        //response
        String STATE = "errorCode";
        String MESSAGE = "errorMsg";
        String DATA = "data";

        //request
        String HEADER_CHANNEL = "channel";
        String HEADER_API_VERSION = "apiVersion";
        String HEADER_DEVICE_ID = "deviceid";
        String HEADER_TOKEN = "token";
        String HEADER_APP_VERSION = "appVersion";
        String HEADER_IP = "ip";
        String REQUEST_ID = "requestId";
        /**
         * 超时时间 单位：秒
         */
        int TIME_OUT = 30;

        /**
         * 接口响应码
         */
        interface Status {
            /**
             * 处理成功
             */
            String CODE_SUCCESS = "0";
            /**
             * 该用户已经被禁用
             */
            String CODE_ACCOUNT_DISABLE = "1111";
            /**
             * 该用户已经离职
             */
            String CODE_ACCOUNT_QUIT = "1112";
            /**
             * 账号被锁定
             */
            String CODE_ACCOUNT_LOCKED = "1113";
            /**
             * 此账户没有操作权限
             */
            String CODE_ACCOUNT_NO_AUTHORITY = "1114";
            /**
             * 此账户没有归属团队
             */
            String CODE_ACCOUNT_NO_TEAM = "1118";
            /**
             * 此账户同一项目归属多个团队
             */
            String CODE_ACCOUNT_MULTIPLE_TEAM = "1119";
            /**
             * Token过期
             */
            String CODE_TOKEN_EXPIRED = "1120";
            /**
             * 该用户的岗位信息有误
             */
            String CODE_ACCOUNT_POSITION_ERROR = "1128";

            /**
             * 参数异常
             */
            String INVALID_PARAMETER_ERROR = "403";
        }
    }

    /**
     * 选择文件的文件类型
     */
    interface FileType {
        /**
         * 图片
         */
        int IMAGE = 0;
        /**
         * 视频
         */
        int VIDEO = 1;
        /**
         * 音频
         */
        int AUDIO = 2;
        /**
         * 文档
         */
        int DOC = 3;
        /**
         * 压缩包
         */
        int PACKAGE = 4;
        /**
         * 其他
         */
        int OTHER = 5;
    }

    /**
     * 数据库配置
     */
    interface DBConfig {
        String DB_NAME = "AMD.db";
    }

    /**
     * 页面跳转类型
     */
    interface TemplateType {

        int ACTIVITY = 0;
        int AROUTER = 1;
        int WEB_VIEW = 2;
        int SYS_BROWSER = 3;

    }
}
