package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 添加活动场地请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class AddEventPlaceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 993093277182748397L;

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
