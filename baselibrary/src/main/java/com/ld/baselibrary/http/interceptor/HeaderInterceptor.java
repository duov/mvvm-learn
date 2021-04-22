package com.ld.baselibrary.http.interceptor;

import com.ld.baselibrary.constant.APIHeader;
import com.ld.baselibrary.util.app.AppProcessHelper;
import com.ld.baselibrary.util.app.AppVersionUtil;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 请求拦截器，修改请求header
 */
public class HeaderInterceptor implements Interceptor {

    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request.Builder builder = request.newBuilder();

        HttpUrl httpUrl = null;
        if (request.header(APIHeader.HEADER_REQUEST_URL_TYPE) != null) {
            switch (request.header(APIHeader.HEADER_REQUEST_URL_TYPE)) {
                case APIHeader.HEADER_REQUEST_URL_IM:
                    //更换为IM BaseUrl
//                    HttpUrl newUrl = HttpUrl.parse(FlavorMgr.getInstance().getBaseUrlIm());
//                    HttpUrl.Builder urlBuilder = request.url().newBuilder();
//                    httpUrl = urlBuilder
//                            .scheme(newUrl.scheme())
//                            .host(newUrl.host())
//                            .build();
                    break;
            }
        }
        // 如果不是登录接口添加Token
        if (!request.url().toString().contains("login.json")) {
            builder.addHeader(APIHeader.TOKEN, "");
//            builder.addHeader(APIHeader.TOKEN, BaseSharePreferenceUtil.getValue(Urls.TOKEN));
        }

        builder.removeHeader(APIHeader.HEADER_REQUEST_URL_TYPE)
                .removeHeader("Host")
                .addHeader("User-Agent", AppProcessHelper.getUserAgent())
                .addHeader(APIHeader.HEADER_KEY_X_API_VER, APIHeader.HEADER_VALUE_X_API_VER)
                .addHeader(APIHeader.HEADER_KEY_X_DEVICE_ID, AppVersionUtil.getAndroidId())
                .addHeader(APIHeader.HEADER_KEY_X_CLIENT_VER, "1.0")
                .addHeader(APIHeader.HEADER_KEY_X_CLIENT_PLATFORM, APIHeader.HEADER_VALUE_X_CLIENT_PLATFORM)
                .addHeader(APIHeader.HEADER_KEY_CONTENT_TYPE, APIHeader.HEADER_VALUE_CONTENT_TYPE);

        Request build = null;
        if (httpUrl != null) {
            //更改baseUrl
            builder.url(httpUrl);
        }
        if ("GET".equals(request.method())) {
            build = builder
                    .get()
                    .build();
        } else {
            build = builder
                    .post(RequestBody.create(MEDIA_TYPE_JSON, bodyToString(request.body())))
                    .build();
        }


        return chain.proceed(build);
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
