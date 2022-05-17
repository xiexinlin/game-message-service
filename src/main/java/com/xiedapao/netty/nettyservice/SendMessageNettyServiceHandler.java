package com.xiedapao.netty.nettyservice;


import com.xiedapao.api.FriendApi;
import com.xiedapao.enumeration.ServerEventEnum;
import com.xiedapao.netty.base.Message;
import com.xiedapao.netty.base.NettyData;
import com.xiedapao.netty.handler.NettyServiceHandler;
import com.xiedapao.utils.NumberUtil;
import com.xiedapao.utils.StringUtil;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xxl 2021/12/10
 */
public class SendMessageNettyServiceHandler implements NettyServiceHandler {

    public SendMessageNettyServiceHandler() {

    }

    @Override
    public void doHandle(Map<Integer, Channel> channelMap, Channel clientChannel, Map<String, Object> data) {
        int userId = NumberUtil.parseInt(data.get("userId"));
        int friendId = NumberUtil.parseInt(data.get("friendId"));

        HashMap<String, Object> map = new HashMap<>();
        map.put("friendId", friendId);
        map.put("userId", userId);
        map.put("message", data.get("message"));
        map.put("messageType", data.get("messageType"));
        map.put("timestamp", data.get("timestamp"));
        map.put("factionId", data.get("factionId"));
        long timestamp = Long.parseLong(StringUtil.toString(data.get("timestamp")));
        Date date = new Date();
        date.setTime(timestamp);

        Channel friendChannel = channelMap.get(friendId);
        if (friendChannel != null) {
            NettyData send = new NettyData(ServerEventEnum.CHAT.getService(), map);
            Message message = new Message(send.toJsonString());
            friendChannel.writeAndFlush(message);
        }
        map.put("createTime", date);
        FriendApi.sendMessage(map);
    }
}
