package com.xiedapao.netty.nettyservice;


import com.xiedapao.enumeration.ServerEventEnum;
import com.xiedapao.netty.base.Message;
import com.xiedapao.netty.base.NettyData;
import com.xiedapao.netty.handler.NettyServiceHandler;
import com.xiedapao.utils.NumberUtil;
import com.xiedapao.utils.StringUtil;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxl 2021/12/10
 */
public class LoginNettyServiceHandler implements NettyServiceHandler {

    public LoginNettyServiceHandler() {

    }

    @Override
    public void doHandle(Map<Integer, Channel> channelMap, Channel clientChannel, Map<String, Object> data) {
        Integer userId = NumberUtil.parseInt(StringUtil.toString(data.get("userId")));
        // 挤下线
        if (channelMap.containsKey(userId) && !channelMap.get(userId).remoteAddress().equals(clientChannel.remoteAddress())) {
            Channel channel = channelMap.get(userId);
            if (channel != null) {
                Map<String, Object> params = new HashMap<>();
                params.put("ip", clientChannel.remoteAddress());
                NettyData send = new NettyData(ServerEventEnum.LOGOUT.getService(), params);
                Message message = new Message(send.toJsonString());
                channel.writeAndFlush(message);
                channelMap.remove(userId);
            }
        }

        channelMap.put(userId, clientChannel);
//        log.info("userId：" + userId + " ip：" + clientChannel.remoteAddress() + "上线了");
//
//        // 通知好友上线
//        QueryFriendsRequest queryFriendsRequest = new QueryFriendsRequest();
//        queryFriendsRequest.setUserId(userId);
//        List<FriendDTO> friendDTOS = this.friendService.queryFriends(queryFriendsRequest);
//        String username = null;
//        if (!CollectionUtils.isEmpty(friendDTOS)) {
//            for (FriendDTO friendDTO : friendDTOS) {
//                if (channelMap.containsKey(friendDTO.getFriendId())) {
//                    if (StringUtil.isEmpty(username)) {
//                        username = userService.getUserInfo(userId).getUsername();
//                    }
//                    Channel channel = channelMap.get(friendDTO.getFriendId());
//                    Map<String, Object> params = new HashMap<>();
//                    params.put("username", username);
//                    NettyData send = new NettyData(ServerEventEnum.FRIEND_ONLINE.getService(), params);
//                    Message message = new Message(send.toJsonString());
//                    channel.writeAndFlush(message);
//                }
//            }
//        }
    }

}
