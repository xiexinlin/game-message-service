package com.xiedapao.netty;

import com.xiedapao.netty.base.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readInt = byteBuf.readInt();
        if (readInt > 0) {
            byte[] bytes = new byte[readInt];
            byteBuf.readBytes(bytes);
            String s = new String(bytes, StandardCharsets.UTF_8);
            Message message = new Message(s);
            list.add(message);
        }

    }
}
