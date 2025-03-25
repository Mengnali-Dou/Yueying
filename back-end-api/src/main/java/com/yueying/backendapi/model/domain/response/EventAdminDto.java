package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 活动管理员信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventAdminDto {

    /**
     * 活动id
     */
    private Long eventId;

    /**
     * 影院名
     */
    private String eventName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;
}
