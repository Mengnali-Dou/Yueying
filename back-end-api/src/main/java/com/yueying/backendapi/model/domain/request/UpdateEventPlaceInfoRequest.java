package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改活动场地信息请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class UpdateEventPlaceInfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7955125485003791438L;

    /**
     * 场地ID
     */
    private Long placeId;

    /**
     * 场地名
     */
    private String placeName;

    /**
     * 场地类型
     */
    private String placeType;

    /**
     * 地址
     */
    private String placeAddress;

    /**
     * 最大容纳人数
     */
    private Long maxSeats;
}
