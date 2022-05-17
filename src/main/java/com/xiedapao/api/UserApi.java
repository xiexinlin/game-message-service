package com.xiedapao.api;

import com.xiedapao.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxl 2022/5/17
 */
public class UserApi {

    public static boolean logout(Integer userId) {
        Map<String, Object> prm = new HashMap<>();
        prm.put("userId", userId);
        return HttpClientUtil.checkReturnStatus(HttpClientUtil.post("/user/logout", prm));
    }

    public static boolean syncUserOnlineStatus(String userIds) {
        Map<String, Object> prm = new HashMap<>();
        prm.put("userIds", userIds);
        return HttpClientUtil.checkReturnStatus(HttpClientUtil.post("/user/syncUserOnlineStatus", prm));
    }

}
