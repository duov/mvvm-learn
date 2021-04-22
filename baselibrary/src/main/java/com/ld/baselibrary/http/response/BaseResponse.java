package com.ld.baselibrary.http.response;

import com.google.gson.annotations.SerializedName;

/**
 * 返回数据base
 */
public class BaseResponse<T> {
    private boolean success;
    private String code;
    // msg/message
    @SerializedName(value = "message", alternate = {"msg"})
    private String message;
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
