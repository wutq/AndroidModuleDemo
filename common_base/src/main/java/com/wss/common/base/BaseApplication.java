package com.wss.common.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.wss.common.manage.ActivityManage;
import com.wss.common.manage.CrashHandlerManage;

/**
 * Describe：基础Application所有需要模块化开发的module都需要继承自BaseApplication
 * Created by 吴天强 on 2018/10/12.
 */
public class BaseApplication extends Application {

    //全局唯一的context
    private static BaseApplication application;

    //Activity管理器
    private ActivityManage activityManage;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activityManage = new ActivityManage();
        initARouter();
        initLogger();
        initCrashManage();
    }

    /**
     * 初始化崩溃管理器
     */
    private void initCrashManage() {
        if (!BuildConfig.DEBUG) {
            CrashHandlerManage.getInstance()
                    .init(getApplicationContext());
        }
    }


    /**
     * 初始化日志打印框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)                   //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)                          //（可选）要显示的方法行数。 默认2
                .methodOffset(7)                         //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .logStrategy(new LogcatLogStrategy())    //（可选）更改要打印的日志策略。 默认LogCat
                .tag("AMD")                              //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //DEBUG模式下不打印LOG
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);// 尽可能早，推荐在Application中初始化
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    /**
     * 获取全局唯一上下文
     *
     * @return BaseApplication
     */
    public static BaseApplication getApplication() {
        return application;
    }


    /**
     * 退出应用
     */
    public void exitApp() {
        activityManage.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 返回Activity管理器
     */
    public ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }


}