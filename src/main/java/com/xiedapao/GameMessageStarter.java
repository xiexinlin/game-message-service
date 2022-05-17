package com.xiedapao;

import com.xiedapao.config.Config;
import com.xiedapao.netty.NettyChatServer;

/**
 * @author xxl 2022/5/17
 */
public class GameMessageStarter {

    public static void main(String[] args) {
        Config.loadConfig(getActiveFromArgs(args));
        new NettyChatServer().start();
    }

    private static String getActiveFromArgs(String[] args) {
        String active = "prod";
        if (args != null && args.length > 0) {
            for (String arg : args) {
                if (arg.contains("--active=")) {
                    active = arg.replace("--active=", "");
                    return active;
                }
            }
        }
        return active;
    }

}
