package com.ld.baselibrary.http.response;

import java.util.List;

/**
 * 返回List数据base
 */
public class BaseListResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private Object messageArgs;

    private List<T> result;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object messageArgs) {
        this.messageArgs = messageArgs;
    }

    public List<T> getResult() {
        return result;
    }


    public void setResult(List<T> result) {
        this.result = result;

    }
}
