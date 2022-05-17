package com.xiedapao.schedule;

import com.xiedapao.api.UserApi;
import com.xiedapao.netty.NettyChatServerHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author xxl 2022/5/17
 */
@Slf4j
public class UserLoginStatusRunnable implements Runnable {

    private NettyChatServerHandler nettyChatServerHandler;

    public UserLoginStatusRunnable(NettyChatServerHandler nettyChatServerHandler) {
        this.nettyChatServerHandler = nettyChatServerHandler;
    }

    @Override
    public void run() {
        log.info("循环同步用户登录状态-开始！");
        Map<Integer, Channel> clientChannelMap = nettyChatServerHandler.getClientChannelMap();
        if (clientChannelMap.isEmpty()) {
            log.info("没有需要同步的用户！");
            return;
        }
        StringBuilder userIds = new StringBuilder();
        for (Integer userId : clientChannelMap.keySet()) {
            userIds.append(userId).append(",");
        }
        UserApi.syncUserOnlineStatus(userIds.substring(0, userIds.length() - 1));
        log.info("循环同步用户登录状态-结束！");
    }

}
