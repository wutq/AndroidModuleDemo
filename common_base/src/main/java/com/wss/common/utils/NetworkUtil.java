package com.wss.common.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Describe：网络状态工具类
 * Created by 吴天强 on 2018/10/25.
 */
public class NetworkUtil {

    /**
     * 是否有可用网络
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkEnabled(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * gps能用
     *
     * @param context ctx
     * @return boolean
     */
    public static boolean isGpsEnabled(@NonNull Context context) {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        if (locationManager != null) {
            List<String> accessibleProviders = locationManager.getProviders(true);
            return accessibleProviders.size() > 0;
        }
        return false;
    }

    /**
     * Wifi是否可用
     *
     * @param context context
     * @return boolean
     */
    public static boolean isWifiEnabled(@NonNull Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgrConn != null && mgrConn.getActiveNetworkInfo() != null &&
                mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return mgrTel != null && mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS;
    }

    /**
     * 当前网络是否为Wifi
     *
     * @param context context
     * @return boolean
     */
    public static boolean isWifi(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }


    /**
     * 当前网络是否为移动数据
     *
     * @param context context
     * @return boolean
     */
    public static boolean isMobile(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return false;
    }

    /**
     * 获取当前网络IP
     *
     * @return 网络IP
     */
    public static String getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI
                    .hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检查是否为连接
     *
     * @param url url
     * @return boolean
     */
    public static boolean isLink(String url) {
        return ValidUtils.isValid(url) && (url.startsWith("http") || url.startsWith("https"));
    }
}
