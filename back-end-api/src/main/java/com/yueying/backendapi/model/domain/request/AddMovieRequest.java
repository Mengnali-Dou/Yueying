package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影片请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddMovieRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 5961043682004360764L;

    /**
     * 影片名
     */
    private String movieName;

    /**
     * 影片类型
     */
    private String movieType;

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
