package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MovieHallTypeDto implements Serializable{

    @Serial
    private static final long serialVersionUID = 721079378202368752L;

    /**
     * 影厅类型id
     */
    private Long hallTypeId;

    /**
     * 影厅类型名
     */
    private String hallTypeName;
}
