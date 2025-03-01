package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影院信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class CinemaInfoDto {

    /**
     * id
     */
    private Long cinemaId;

    /**
     * 影院名
     */
    private String cinemaName;

    /**
     * 影院地址
     */
    private String cinemaAddress;

    /**
     * 影院简介
     */
    private String cinemaProfile;

    /**
     * 影院服务
     */
    private String cinemaService;

    /**
     * 影院电话
     */
    private String cinemaPhone;

    /**
     * 影院交通
     */
    private String cinemaTraffic;
}
