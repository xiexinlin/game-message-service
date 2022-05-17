package com.xiedapao.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author xxl 2021/10/25
 */
@Data
public class FriendDTO {

    private Integer friendId;

    private String username;

    private Integer lv;

    private String headSculptureUrl;

    private Integer online;

    private Integer unreadChatMessageNum;

    private Date lastLoginDate;

}
