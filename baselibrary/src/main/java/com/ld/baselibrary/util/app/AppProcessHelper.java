package com.ld.baselibrary.util.app;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;

import com.ld.baselibrary.base.BaseApplication;
import com.ld.baselibrary.util.ContextUtils;

/**
 * Created by Lsy on 2020/3/24.
 */
public class AppProcessHelper {

    private final static String PROCESS_NAME = "com.ld";

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public static boolean isAppMainProcess(BaseApplication application) {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(application.getInstance(), pid);
            if (TextUtils.isEmpty(process)) {
                return true;
            } else if (PROCESS_NAME.equalsIgnoreCase(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    public static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(ContextUtils.getContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
