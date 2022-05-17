package com.xiedapao.entity;

import com.alibaba.fastjson.JSONObject;

public class Result {

    private int returnStatus;

    private Object data;

    private String message;

    public Result() {

    }

    public int getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(int returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "returnStatus=" + returnStatus +
                ", data=" + JSONObject.toJSONString(data) +
                ", message='" + message + '\'' +
                '}';
    }
}
