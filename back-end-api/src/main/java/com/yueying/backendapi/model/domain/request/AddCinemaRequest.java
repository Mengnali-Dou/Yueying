package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影院请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddCinemaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2257580122880033014L;

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
