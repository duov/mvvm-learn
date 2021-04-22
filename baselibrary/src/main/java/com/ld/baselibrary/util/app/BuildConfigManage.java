package com.ld.baselibrary.util.app;


import com.ld.baselibrary.BuildConfig;

public class BuildConfigManage {
    // 判断当前环境是否是prod
    public static boolean isProd(){
        if(BuildConfig.FLAVOR.equals("prod")
//                || BuildConfig.FLAVOR.equals("zqh360")
//                || BuildConfig.FLAVOR.equals("zbaidu")
//                || BuildConfig.FLAVOR.equals("zhuawei")
//                || BuildConfig.FLAVOR.equals("zxiaomi")
//                || BuildConfig.FLAVOR.equals("zoppo")
//                || BuildConfig.FLAVOR.equals("zvivo")
//                || BuildConfig.FLAVOR.equals("zyingyongbao")
//                || BuildConfig.FLAVOR.equals("zwandoujia")
//                || BuildConfig.FLAVOR.equals("zanzhi")
//                || BuildConfig.FLAVOR.equals("zlianxiang")
//                || BuildConfig.FLAVOR.equals("zsanxing")
//                || BuildConfig.FLAVOR.equals("zmeizu")
        ){
            return true;
        }else{
            return false;
        }
    }
}
