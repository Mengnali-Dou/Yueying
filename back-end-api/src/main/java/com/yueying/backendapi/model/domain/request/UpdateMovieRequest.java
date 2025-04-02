package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改影片信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateMovieRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7791233283097707850L;

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
