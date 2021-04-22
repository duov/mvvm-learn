package com.ld.baselibrary.http.exception;

/**
 * Describe: 服务器返回异常
 */
public class ServerException extends Throwable{

    private static final long serialVersionUID = 6955502544596478739L;

    private String code;
    private String msg;

    public ServerException(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
