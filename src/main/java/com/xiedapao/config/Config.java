package com.xiedapao.config;

import com.xiedapao.GameMessageStarter;
import com.xiedapao.utils.NumberUtil;

import java.util.Properties;

/**
 * 配置类
 * @author xxl 2021/10/11
 */
public class Config {

    public static int port;
    public static int readOutTime;
    public static String gameServerUrl;
    public static String active;

    public static void loadConfig(String activeArg) {
        active = activeArg;
        try {
            Properties properties = new Properties();
            properties.load(GameMessageStarter.class.getResourceAsStream("/game-config.properties"));
            properties.load(GameMessageStarter.class.getResourceAsStream("/game-config-" + active + ".properties"));

            port = NumberUtil.parseInt(properties.getProperty("port"));
            readOutTime = NumberUtil.parseInt(properties.getProperty("read_out_time"));
            gameServerUrl = properties.getProperty("hero_game_service_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
