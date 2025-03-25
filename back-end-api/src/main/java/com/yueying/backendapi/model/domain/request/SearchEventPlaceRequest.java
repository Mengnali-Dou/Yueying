package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 活动场地查询请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchEventPlaceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 4647416605830084573L;

    /**
     * 场地名
     */
    private String placeName;

    /**
     * 场地类型
     */
    private String placeType;
}
