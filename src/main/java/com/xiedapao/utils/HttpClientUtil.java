package com.xiedapao.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiedapao.config.Config;
import com.xiedapao.entity.Result;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HttpClientUtil {

    private static final int SUCCESS_STATUS = 200;

    private static CloseableHttpClient httpClient;

    private static CloseableHttpClient getClientInstance() {
        if (httpClient == null) {
            synchronized (HttpClientUtil.class) {
                if (httpClient == null) {
                    httpClient = HttpClients.createDefault();
                }
            }
        }
        return httpClient;
    }

    private static String getSign(Map<String, Object> prm, long timestamp) {
        TreeMap<String, String> keys = new TreeMap<>();
        prm.forEach((key, value) -> {
            keys.put(key, StringUtil.toString(value));
        });
        return MD5.encrypt(keys, timestamp);
    }

    public static Result get(String uri) {
        return get(uri, new HashMap<>());
    }

    public static Result get(String uri, Map<String, Object> prm) {
        CloseableHttpClient httpClient = getClientInstance();
        StringBuilder params = new StringBuilder();

        for (String key : prm.keySet()) {
            if (StringUtil.isNotEmpty(params.toString())) {
                params.append("&");
            }
            params.append(key).append("=").append(prm.get(key));
        }

        StringBuilder requestUrl = new StringBuilder();
        long timestamp = System.currentTimeMillis();
        String sign = getSign(prm, timestamp);
        requestUrl.append(Config.gameServerUrl).append(uri).append("?").append(params).append("&tid=").append(timestamp).append("&sid=").append(sign);

        HttpGet httpGet = new HttpGet(requestUrl.toString());
        // 设置超时时间
        RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(Config.readOutTime).setConnectTimeout(Config.readOutTime).build();
        httpGet.setConfig(requestConfig);

        return execute(httpClient, httpGet);
    }

    public static Result post(String uri) {
        return post(uri, new HashMap<>());
    }

    public static Result post(String uri, Map<String, Object> prm) {
        CloseableHttpClient httpClient = getClientInstance();

        Set<String> strings = prm.keySet();
        List<BasicNameValuePair> list = new ArrayList<>();
        for (String key : strings) {
            list.add(new BasicNameValuePair(key, StringUtil.toString(prm.get(key))));
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, StandardCharsets.UTF_8);

        StringBuilder requestUrl = new StringBuilder();
        long timestamp = System.currentTimeMillis();
        String sign = getSign(prm, timestamp);
        requestUrl.append(Config.gameServerUrl).append(uri).append("?").append("tid=").append(timestamp).append("&sid=").append(sign);

        HttpPost httpPost = new HttpPost(requestUrl.toString());
        // 设置超时时间
        RequestConfig requestConfig =  RequestConfig.custom().setSocketTimeout(Config.readOutTime).setConnectTimeout(Config.readOutTime).build();
        httpPost.setConfig(requestConfig);
        // 设置参数
        httpPost.setEntity(formEntity);
        // 设置请求头
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8;multipart/form-data");
        httpPost.setHeader("service-name", "game-message-service");

        return execute(httpClient, httpPost);
    }

    private static Result execute(CloseableHttpClient httpClient, HttpUriRequest httpUriRequest) {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpUriRequest);
            HttpEntity entity = response.getEntity();
            String resultStr = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            Result result = JSONObject.parseObject(resultStr, Result.class);
            System.out.println("uri:" + httpUriRequest.getURI() + " " + result);
            return result;
        } catch (Exception e) {
            showErrorMessage(e);
            Result result = new Result();
            result.setReturnStatus(500);
            result.setMessage("服务器异常！");
            return result;
        } finally {
            close(httpClient, response);
        }
    }

    public static boolean checkReturnStatus(Result result) {
        if (result == null) return false;
        return NumberUtil.parseInt(result.getReturnStatus()) == SUCCESS_STATUS;
    }

    public static<T> T getForObj(String uri, Map<String, Object> prm, Class<T> objClass) {
        return postForJsonObj(uri, prm).toJavaObject(objClass);
    }

    public static JSONObject postForJsonObj(String uri, Map<String, Object> prm) {
        Result result = post(uri, prm);
        if (HttpClientUtil.checkReturnStatus(result)) {
            return (JSONObject) result.getData();
        }
        return new JSONObject();
    }

    public static<T> T postForObj(String uri, Map<String, Object> prm, Class<T> objClass) {
        return postForJsonObj(uri, prm).toJavaObject(objClass);
    }

    public static JSONArray postForJsonArray(String uri, Map<String, Object> prm) {
        Result result = post(uri, prm);
        if (HttpClientUtil.checkReturnStatus(result)) {
            return (JSONArray) result.getData();
        }
        return new JSONArray();
    }

    public static<T> List<T> postForArray(String uri, Map<String, Object> prm, Class<T> objClass) {
        return postForJsonArray(uri, prm).toJavaList(objClass);
    }

    public static void close(CloseableHttpClient httpclient, CloseableHttpResponse response) {
        try {
//            if (httpclient != null)
//                httpclient.close();
            if (response != null)
                response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorMessage(Exception e) {
        e.printStackTrace();
    }

}
