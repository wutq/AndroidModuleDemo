package com.wss.common.base;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.wss.common.bean.User;
import com.wss.common.constants.Constants;
import com.wss.common.constants.Dic;
import com.wss.common.manage.ActivityManage;
import com.wss.common.utils.CacheUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.common.utils.toast.ToastInterceptor;
import com.wss.common.utils.toast.style.ToastBlackStyle;

import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import androidx.multidex.MultiDex;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import rxhttp.HttpSender;
import rxhttp.wrapper.ssl.SSLSocketFactoryImpl;
import rxhttp.wrapper.ssl.X509TrustManagerImpl;

/**
 * Describe：基础Application所有需要模块化开发的module都需要继承自BaseApplication
 * Created by 吴天强 on 2018/10/12.
 */
public class BaseApplication extends Application {
    /**
     * 全局上下文
     */
    private static BaseApplication application;
    /**
     * Activity管理器
     */
    private ActivityManage activityManage;

    /**
     * 保存所有网络请求
     */
    private Map<String, Disposable> netDisposable;

    /**
     * 登录用户
     */
    private User user;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        //MultiDex分包方法 初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activityManage = new ActivityManage();
        //初始化日志框架
        initLogger();
        //初始化ARouter
        initARouter();
        //初始化Toast
        initToast();
        //初始化网络框架
        initRXHttp();
        //刷新框架
        initRefreshLayout();
    }

    /**
     * 初始化刷新框架
     */
    private void initRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //指定为经典Footer，默认是 BezierRadarHeader
            return new ClassicsHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    /**
     * B
     * 初始化Toast
     */
    private void initToast() {
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Logger.e("空 Toast");
                } else {
                    Logger.d(text.toString());
                }
                return intercept;
            }
        });
        // 初始化吐司工具类
        ToastUtils.init(this, new ToastBlackStyle(this));
    }

    /**
     * 初始化网络请求
     */
    private void initRXHttp() {
        X509TrustManager trustManager = new X509TrustManagerImpl();
        SSLSocketFactory sslSocketFactory = new SSLSocketFactoryImpl(trustManager);
        HttpSender.setDebug(BuildConfig.DEBUG);
        HttpSender.init(new OkHttpClient.Builder()
                .connectTimeout(Constants.Net.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.Net.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.Net.TIME_OUT, TimeUnit.SECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
                //添加信任证书
                .sslSocketFactory(sslSocketFactory, trustManager)
                //忽略Host验证
                .hostnameVerifier(((hostname, session) -> true))
                .build());
        RxJavaPlugins.setErrorHandler(throwable -> {
            //Rx全局异常处理
        });
    }

    /**
     * 初始化日志打印框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //（可选）是否显示线程信息。 默认值为true
                .showThreadInfo(false)
                //（可选）要显示的方法行数。 默认2
                .methodCount(0)
                //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .methodOffset(7)
                //（可选）更改要打印的日志策略。 默认LogCat
                .logStrategy(new LogcatLogStrategy())
                //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .tag("APP_LOG")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //非DEBUG模式下不打印LOG
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(application);
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
    @Contract(pure = true)
    public static BaseApplication i() {
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
     * 获取Activity管理器
     *
     * @return ActivityManage
     */
    public ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }

    /**
     * 获取存放的请求对象
     *
     * @return Map
     */
    public Map<String, Disposable> getNetDisposables() {
        if (netDisposable == null) {
            return new HashMap<>(16);
        }
        return netDisposable;
    }

    /**
     * 退出登录清除换群
     */
    public void loginOutClean() {

    }

    /**
     * 添加请求对象
     *
     * @param requestId  请求ID
     * @param disposable 对象
     */
    public void addNetDisposable(String requestId, Disposable disposable) {
        if (netDisposable == null) {
            netDisposable = new HashMap<>(16);
        }
        netDisposable.put(requestId, disposable);
    }

    /**
     * 根据请求ID移除该对象
     *
     * @param requestId 请求ID
     */
    public void removeNetDisposable(String requestId) {
        netDisposable.remove(requestId);
    }

    /**
     * 保存用户信息
     *
     * @param user 登录用户
     */
    public void setUser(User user) {
        CacheUtils.get(this).put(Dic.LOGIN_USER_INFO, user);
        this.user = user;
    }

    /**
     * 获取用户信息
     *
     * @return User
     */
    public User getUser() {
        if (!ValidUtils.isValid(user) || !ValidUtils.isValid(user.getId())) {
            //如果保存的user为空，则去本地缓存中取
            user = (User) CacheUtils.get(this).getAsObject(Dic.LOGIN_USER_INFO);
        }
        if (!ValidUtils.isValid(user)) {
            //防止异常情况下getUser对象为null问题
            user = new User();
        }
        return user;
    }

    /**
     * 是否登录
     *
     * @return boolean
     */
    public boolean isLogged() {
        User user = getUser();
        return ValidUtils.isValid(user) && ValidUtils.isValid(user.getId());
    }


    public String getDeviceId() {
        return "";
    }

    public String getLoginToken() {
        return "";
    }
}