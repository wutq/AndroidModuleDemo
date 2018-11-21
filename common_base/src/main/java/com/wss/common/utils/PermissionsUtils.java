package com.wss.common.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：6.0动态权限管理帮助类
 * Created by 吴天强 on 2018/10/25.
 */

public class PermissionsUtils {


    /**
     * 判断权限
     *
     * @param context     context
     * @param permissions 权限列表
     */
    public static boolean checkPermissions(Activity context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionsList = new ArrayList<>();
            if (permissions != null && permissions.length != 0) {
                for (String permission : permissions) {
                    if (!isHavePermissions(context, permission)) {
                        permissionsList.add(permission);
                    }
                }
                if (permissionsList.size() > 0) {
                    // 遍历完后申请
                    applyPermissions(context, permissionsList);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查是否授权某权限
     */
    private static boolean isHavePermissions(Activity context, String permissions) {
        return ContextCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 申请权限
     */
    private static void applyPermissions(Activity context, List<String> permissions) {
        if (!permissions.isEmpty()) {
            ActivityCompat.requestPermissions(context, permissions.toArray(new String[permissions.size()]), 1);
        }
    }


}
