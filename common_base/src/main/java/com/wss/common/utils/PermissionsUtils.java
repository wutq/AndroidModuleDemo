package com.wss.common.utils;

import android.Manifest;
import android.app.Activity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.wss.common.base.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe：6.0 + 动态权限管理帮助类
 * Created by 吴天强 on 2018/10/25.
 */
public class PermissionsUtils {

    /**
     * 授权所有权限
     *
     * @param activity activity
     */
    public static void authorizationAllPermissions(Activity activity) {
        XXPermissions.with(activity)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                    }
                });
    }

    /**
     * 检查权限
     *
     * @param activity    activity
     * @param permissions 权限组
     * @return Observable<Boolean>
     */
    public static Observable<Boolean> checkPermissions(Activity activity, String... permissions) {
        return Observable.<Boolean>create(
                subscriber -> {
                    if (XXPermissions.isHasPermission(activity, permissions)) {
                        subscriber.onNext(true);
                    } else {
                        XXPermissions.with(activity)
                                .permission(permissions)
                                .request(new OnPermission() {
                                    @Override
                                    public void hasPermission(List<String> granted, boolean isAll) {
                                        subscriber.onNext(true);
                                    }

                                    @Override
                                    public void noPermission(List<String> denied, boolean quick) {
                                        subscriber.onNext(false);
                                        ToastUtils.show(activity, activity.getString(R.string.authorise_necessary_authorities));
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 检查相机权限
     *
     * @param activity activity
     * @return Observable<Boolean>
     */
    public static Observable<Boolean> checkCamera(Activity activity) {
        return checkPermissions(activity, Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE);
    }


    /**
     * 检查文件读写权限
     *
     * @param activity activity
     * @return boolean
     */
    public static Observable<Boolean> checkStorage(Activity activity) {
        String[] storage = {
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE};
        return checkPermissions(activity, storage);
    }


    /**
     * 检查录音相关权限
     */
    public static Observable<Boolean> checkRecord(Activity activity) {
        return checkPermissions(activity, Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.RECORD_AUDIO);
    }

    /**
     * 检查拨打电话相关权限
     */
    public static Observable<Boolean> checkCallPhone(Activity activity) {
        return checkPermissions(activity, Permission.CALL_PHONE);
    }

    /**
     * 检查定位权限
     *
     * @param activity activity
     * @return boolean
     */
    public static Observable<Boolean> checkLocation(Activity activity) {
        return checkPermissions(activity, Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION, Permission.RECORD_AUDIO);
    }

    /**
     * 允许锁屏状态下，app在后台运行
     *
     * @param activity activity
     * @return boolean
     */
    public static Observable<Boolean> checkWakeLock(Activity activity) {
        return checkPermissions(activity, Manifest.permission.WAKE_LOCK);
    }

}
