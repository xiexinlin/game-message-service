package com.xiedapao.schedule;

import com.xiedapao.netty.NettyChatServerHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        log.info("循环同步用户登录状态！");
        Map<Integer, Channel> clientChannelMap = nettyChatServerHandler.getClientChannelMap();
        for (Integer userId : clientChannelMap.keySet()) {

        }
    }

}
