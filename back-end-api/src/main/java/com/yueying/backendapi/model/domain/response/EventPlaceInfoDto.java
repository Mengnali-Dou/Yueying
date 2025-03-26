package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 活动场地信息DTO
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class EventPlaceInfoDto {

    /**
     * 场地Id
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
     * 场地地址
     */
    private String placeAddress;

    /**
     * 最大容纳人数
     */
    private Long maxSeats;
}
