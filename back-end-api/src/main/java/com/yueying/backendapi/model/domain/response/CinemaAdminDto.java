package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影院管理员信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class CinemaAdminDto {

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 影院名
     */
    private String cinemaName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userAccount;
}
