package com.xiedapao.api;

import com.xiedapao.pojo.dto.FriendDTO;
import com.xiedapao.utils.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xxl 2022/5/17
 */
public class FriendApi {

    public static List<FriendDTO> queryFriends(Integer userId) {
        Map<String, Object> prm = new HashMap<>();
        prm.put("userId", userId);
        return HttpClientUtil.postForArray("/friend/queryFriends", prm, FriendDTO.class);
    }

    public static boolean sendMessage(Map<String, Object> prm) {
        return HttpClientUtil.checkReturnStatus(HttpClientUtil.post("/friend/sendMessage", prm));
    }

}
