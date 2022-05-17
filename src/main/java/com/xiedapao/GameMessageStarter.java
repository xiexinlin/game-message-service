package com.xiedapao;

import com.xiedapao.config.Config;
import com.xiedapao.netty.NettyChatServer;

/**
 * @author xxl 2022/5/17
 */
public class GameMessageStarter {

    public static void main(String[] args) {
        Config.loadConfig("prod");
        new NettyChatServer().start();
    }

}
