package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改影厅信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateMovieHallRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8775114147000007647L;

    /**
     * 影厅id
     */
    private Long movieHallId;

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
}
