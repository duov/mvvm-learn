package com.ld.mvvm.base;

/**
 * @author: liangduo
 * @date: 2020/9/1 4:15 PM
 */
public class BaseResponseBean {


    /**
     * query : car
     * errorCode : 50
     */

    private String query;
    private int errorCode;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
