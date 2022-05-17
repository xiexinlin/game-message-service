package com.xiedapao.netty.handler;

import io.netty.channel.Channel;

import java.util.Map;

/**
 * @author xxl 2021/12/10
 */
public interface NettyServiceHandler {

    void doHandle(Map<Integer, Channel> channelMap, Channel clientChannel, Map<String, Object> data);

}
