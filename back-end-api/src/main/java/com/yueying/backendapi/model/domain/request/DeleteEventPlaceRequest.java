package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除活动场地请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class DeleteEventPlaceRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7364846520100376534L;

    /**
     * id
     */
    private Long placeId;
}
