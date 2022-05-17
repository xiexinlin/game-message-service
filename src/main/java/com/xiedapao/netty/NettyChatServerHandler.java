package com.xiedapao.netty;

import com.alibaba.fastjson.JSONObject;
import com.xiedapao.api.UserApi;
import com.xiedapao.enumeration.ServerEventEnum;
import com.xiedapao.netty.base.Message;
import com.xiedapao.netty.base.NettyData;
import com.xiedapao.netty.handler.NettyServiceHandler;
import com.xiedapao.netty.nettyservice.LoginNettyServiceHandler;
import com.xiedapao.netty.nettyservice.SendMessageNettyServiceHandler;
import com.xiedapao.schedule.UserLoginStatusRunnable;
import com.xiedapao.utils.StringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@ChannelHandler.Sharable
public class NettyChatServerHandler extends SimpleChannelInboundHandler<Message> {

    private Map<Integer, Channel> clientChannelMap = new HashMap<>();

    private Map<String, NettyServiceHandler> nettyServiceHandlerMap = new HashMap<>();

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public NettyChatServerHandler() {
        super();
        initHandler();
        initSchedule();
    }

    private void initHandler() {
        nettyServiceHandlerMap.put(ServerEventEnum.LOGIN.getHandlerName(), new LoginNettyServiceHandler());
        nettyServiceHandlerMap.put(ServerEventEnum.CHAT.getHandlerName(), new SendMessageNettyServiceHandler());
    }

    private void initSchedule() {
        scheduledExecutorService.scheduleAtFixedRate(new UserLoginStatusRunnable(this), 60 * 60, 60 * 60, TimeUnit.SECONDS);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        for (Integer key : clientChannelMap.keySet()) {
            Channel channel = clientChannelMap.get(key);
            if (ctx.channel().equals(channel)) {
                clientChannelMap.remove(key);
                log.info(String.format("userId：%s,ip：%s离线了", key, ctx.channel().remoteAddress()));
                // 更新离线状态
                UserApi.logout(key);
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message msg) throws Exception {
        try {
            Channel client = channelHandlerContext.channel();

            String msgStr = new String(msg.getContent(), StandardCharsets.UTF_8);
            NettyData nettyData = JSONObject.parseObject(msgStr, NettyData.class);

            String service = nettyData.getService();
            Map<String, Object> data = nettyData.getData();

            String handlerName = ServerEventEnum.getHandlerNameById(service);
            if (StringUtil.isNotEmpty(handlerName) && nettyServiceHandlerMap.containsKey(handlerName)) {
                NettyServiceHandler nettyServiceHandler = nettyServiceHandlerMap.get(handlerName);
                nettyServiceHandler.doHandle(clientChannelMap, client, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public Map<Integer, Channel> getClientChannelMap() {
        return clientChannelMap;
    }

}
