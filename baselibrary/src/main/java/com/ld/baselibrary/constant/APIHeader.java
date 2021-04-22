package com.ld.baselibrary.constant;


public class APIHeader {

    public final static String API_VER = "21000";

    /**
     * 网络请求header
     */
    //header Content-Type key and value
    public static final String HEADER_KEY_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_VALUE_CONTENT_TYPE = "application/x-www-form-urlencoded";

    //header X-Api-Ver hey and value
    public static final String HEADER_KEY_X_API_VER = "X-Api-Ver";
    public static final String HEADER_VALUE_X_API_VER = API_VER;

    //header X-Client-Ver hey and valuevalue
    public static final String HEADER_KEY_X_CLIENT_VER = "X-Client-Ver";
    //    public static final String HEADER_VALUE_X_CLIENT_VER = "1.0";

    //header X-Client-Platform hey and value
    public static final String HEADER_KEY_X_CLIENT_PLATFORM = "X-Client-Platform";
    public static final String HEADER_VALUE_X_CLIENT_PLATFORM = "android";
    //header X-Device-Id hey and value
    public static final String HEADER_KEY_X_DEVICE_ID = "X-Device-Id";

    //header X-Access-Token
    public static final String TOKEN = "X-Access-Token";
    //header X-Device-Token
    public static final String DEVICE_TOKEN = "X-Device-Token";
    public static final String USER_ID = "X-User-Id";

    //Login Content-Type key and value
    public static final String HEADER_KEY_LOGIN_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_VALUE_LOGIN_CONTENT_TYPE = "application/json";

    //type header 用于区分im和业务请求，切换对应BaseUrl
    public static final String HEADER_REQUEST_URL_TYPE = "Request-Url-Type";
    public static final String HEADER_REQUEST_URL_DXZ = "HEADER_REQUEST_URL_DXZ";
    public static final String HEADER_REQUEST_URL_IM = "HEADER_REQUEST_URL_IM";

    public static final String NORMAL_BASE_URL = HEADER_REQUEST_URL_TYPE + ":" + HEADER_REQUEST_URL_DXZ;
    public static final String IM_BASE_URL = HEADER_REQUEST_URL_TYPE + ":" + HEADER_REQUEST_URL_IM;

    //注册
    public final static String REGISTER = "/api/a/user/register.json";

    //关注达人
    public final static String FOCUS_TEACHER = "/api/a/fans/attention.json";

    // APP查询课程详情
    public final static String GOODS_DETAILS = "/api/a/goods/detail/goods-details.json";

}
