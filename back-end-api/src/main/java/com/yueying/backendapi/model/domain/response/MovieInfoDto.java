package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影片信息
 */
@Data
public class MovieInfoDto {

    /**
     * 影片id
     */
    private Long movieId;

    /**
     * 影片名
     */
    private String movieName;

    /**
     * 影片类型id
     */
    private Integer movieTypeId;

    /**
     * 影片类型
     */
    private String movieTypeName;

    /**
     * 影片封面（大）
     */
    private String movieCoverLarge;

    /**
     * 影片封面（小）
     */
    private String movieCoverSmall;

    /**
     * 上映时间
     */
    private String releaseDate;

    /**
     * 影片时长
     */
    private String movieDuration;

    /**
     * 主要演员
     */
    private String mainActor;

    /**
     * 影片简介
     */
    private String movieProfile;
}
