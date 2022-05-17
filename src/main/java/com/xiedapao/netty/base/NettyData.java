package com.xiedapao.netty.base;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class NettyData {

    private String service;

    private Map<String, Object> data;

    public NettyData(String service, Map<String, Object> data) {
        this.service = service;
        this.data = data;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    @Override
    public String toString() {
        return "NettyData{" +
                "service='" + service + '\'' +
                ", data=" + data +
                '}';
    }
}
