package com.xiedapao.netty;

import com.xiedapao.config.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyChatServer {

    private NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private NioEventLoopGroup workGroup = new NioEventLoopGroup();

    public void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NettyChatServerHandler nettyChatServerHandler = new NettyChatServerHandler();
        try {
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new MessageDecoder());
                            pipeline.addLast("encoder", new MessageEncoder());
                            pipeline.addLast(nettyChatServerHandler);
                        }
                    });

            log.info("启动成功");
            ChannelFuture channelFuture = serverBootstrap.bind(Config.port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
