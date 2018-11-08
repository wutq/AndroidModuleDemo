package com.wss.module.market.db;

import android.content.Context;

import com.wss.common.constants.DBConfig;
import com.wss.module.market.bean.DaoMaster;
import com.wss.module.market.bean.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Describe：商场
 * Created by 吴天强 on 2018/11/5.
 */

public class MarketDBManage {


    private static MarketDBManage dbManage;

    private Context context;

    public static MarketDBManage getInstance(Context context) {
        if (dbManage == null) {
            synchronized (MarketDBManage.class) {
                if (dbManage == null) {
                    dbManage = new MarketDBManage(context);
                }
            }
        }
        return dbManage;
    }

    private MarketDBManage(Context context) {
        this.context = context;
    }

    /**
     * 设置debug模式开启或关闭，默认关闭 * * @param flag
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }


    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    /**
     * 获取DaoSession * * @return
     */
    public synchronized DaoSession getDaoSession() {
        if (null == mDaoSession) {
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    /**
     * 关闭数据库
     */
    public synchronized void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    /**
     * 判断数据库是否存在，如果不存在则创建 * * @return
     */
    private DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            mHelper = new DaoMaster.DevOpenHelper(context, DBConfig.DB_NAME, null);
            mDaoMaster = new DaoMaster(mHelper.getWritableDb());
        }
        return mDaoMaster;
    }

    private void closeDaoSession() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private void closeHelper() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }
}
