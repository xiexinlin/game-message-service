package com.xiedapao.netty;

import com.xiedapao.netty.base.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyChatClientHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message s) throws Exception {
        System.out.println(s.getContent());
    }

}
