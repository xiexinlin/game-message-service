package com.xiedapao.enumeration;

/**
 * @author xxl 2021/9/14
 */
public enum ServerEventEnum {
    LOGIN("login", "loginNettyServiceHandler"),
    CHAT("chat", "sendMessageNettyServiceHandler"),
    LOGOUT("logout"),
    FRIEND_ONLINE("friendOnline")
    ;

    private String service;

    private String handlerName;

    ServerEventEnum(String service) {
        this(service, null);
    }

    ServerEventEnum(String service, String handlerName) {
        this.service = service;
        this.handlerName = handlerName;
    }

    public String getService() {
        return service;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public static String getHandlerNameById(String service) {
        for (ServerEventEnum serverEventEnum : values()) {
            if (serverEventEnum.getService().equals(service)) {
                return serverEventEnum.getHandlerName();
            }
        }
        return null;
    }

}
