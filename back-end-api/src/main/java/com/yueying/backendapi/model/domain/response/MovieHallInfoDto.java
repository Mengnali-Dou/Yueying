package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影厅信息
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieHallInfoDto {

    /**
     * 影厅id
     */
    private Long movieHallId;

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 影院名
     */
    private String cinemaName;

    /**
     * 影厅名
     */
    private String movieHallName;

    /**
     * 影厅类型id
     */
    private Long movieHallTypeId;

    /**
     * 影厅类型名
     */
    private String movieHallTypeName;

    /**
     * 影厅照片
     */
    private String movieHallPhoto;

    /**
     * 影厅简介
     */
    private String movieHallProfile;

    /**
     * 影厅座位数
     */
    private Long seating;
}
