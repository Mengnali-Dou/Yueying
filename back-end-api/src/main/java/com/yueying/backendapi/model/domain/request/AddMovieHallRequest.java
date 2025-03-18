package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加影厅请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddMovieHallRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2362351656930083400L;

    /**
     * 影院id
     */
    private Long cinemaId;

    /**
     * 影厅名
     */
    private String movieHallName;

    /**
     * 影厅类型id
     */
    private Long movieHallTypeId;

    /**
     * 影厅照片
     */
    private String movieHallPhoto;

    /**
     * 影厅简介
     */
    private String movieHallProfile;

    /**
     * 座位数
     */
    private Long seating;
}
