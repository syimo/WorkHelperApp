package com.king.applib.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络相关工具类.
 * Created by HuoGuangxu on 2016/10/20.
 */

public class NetworkUtil {
    private NetworkUtil() {
        throw new UnsupportedOperationException("No instances!");
    }

    /**
     * 判断网络是否可用.
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo[] allNets = connectivity.getAllNetworkInfo();
        if (allNets != null) {
            for (NetworkInfo info : allNets) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi
     */
    public static boolean isWifi(Context context) {
        return ConnectivityManager.TYPE_WIFI == getNetWorkType(context);
    }

    /**
     * 获得当前网络类型.获取不到返回-1.
     */
    public static int getNetWorkType(Context context) {
        if (context == null) {
            return -1;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                switch (networkInfo.getType()) {
                    case ConnectivityManager.TYPE_WIFI:
                        return ConnectivityManager.TYPE_WIFI;
                    case ConnectivityManager.TYPE_MOBILE:
                        return ConnectivityManager.TYPE_MOBILE;
                    default:
                        return -1;
                }
            }
        }
        return -1;
    }

    // MOBILE网络是几G网2G？3G？4G？
    // 返回几就是几G网
    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;
            default:
                return 0;
        }
    }

    // wifi网络是否可用
    public static boolean isWifiConnected(@NonNull Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        }
        return false;
    }

    //mobile网络是否可用
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 使用MOBILE网络时用来获取IP
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                        return inetAddress.getHostAddress().toString();
                }
            }
        } catch (SocketException ex) {
        }
        return "0.0.0.0";
    }

    // 使用Wifi时用来获取IP
    public static String getWifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }

    // 获取wifi的ssid（即wifi名称）
    public static String getWifiSsid(Context context) {
        String ssidApn = "";
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wi = wm.getConnectionInfo();
            ssidApn = wi.getSSID();
        } catch (Exception e) {
        }
        return ssidApn;
    }

    // 手机GPS是否开启
    public static boolean isGpsEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // 返回运营商名字
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    private static String intToIp(int i) {
        return String.format("%d.%d.%d.%d", (i & 0xff), (i >> 8 & 0xff), (i >> 16 & 0xff), (i >> 24 & 0xff));
    }

    /**
     *当手机使用WIFI时：
     * 网络是否可用 isNetworkAvailable:true
     得到当前网络类型 getNetWorkType:wifi
     当前网络是不是wifi isConnectedByWifi:true
     MOBILE网络是几G网 getNetWorkClass:0
     wifi网络是否可用 isWifiConnected:true
     mobile网络是否可用 isMobileConnected:true
     使用MOBILE网络时用来获取IP getLocalIpAddress:fe80::fa:70ff:fe07:89d1%dummy0
     使用Wifi时用来获取IP getWifiIpAddress:192.168.30.6
     获取wifi的ssid（即wifi名称） getWifiSsid:"***aifu"
     手机GPS是否开启 isGpsEnable:true
     返回运营商名字 getNetworkOperatorName:中国联通

     当使用MOBILE网络时：
     网络是否可用 isNetworkAvailable:true
     得到当前网络类型 getNetWorkType:mobile
     当前网络是不是wifi isConnectedByWifi:false
     MOBILE网络是几G网 getNetWorkClass:3
     wifi网络是否可用 isWifiConnected:false
     mobile网络是否可用 isMobileConnected:true
     使用MOBILE网络时用来获取IP getLocalIpAddress:10.34.23.150
     使用Wifi时用来获取IP getWifiIpAddress:0.0.0.0
     获取wifi的ssid（即wifi名称） getWifiSsid:<unknown ssid>
     手机GPS是否开启 isGpsEnable:true
     返回运营商名字 getNetworkOperatorName:中国联通
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
