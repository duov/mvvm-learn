package com.ld.baselibrary.util.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.ld.baselibrary.base.BaseApplication;

public class AppVersionUtil {

    private static Object androidId;

    //版本名
    public static String getVersionName(Context context) {
        if (getPackageInfo(context) == null) {
            return "";
        } else {
            return getPackageInfo(context).versionName;
        }
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    //手机厂家
    public static String getManufactor() {
        return Build.MANUFACTURER;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static String getAndroidId() {
        //android设备唯一标识
        String AndroidId = "";
        String androidID = Settings.Secure.getString(BaseApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        AndroidId = androidID + Build.SERIAL;
        return AndroidId;
    }
}
