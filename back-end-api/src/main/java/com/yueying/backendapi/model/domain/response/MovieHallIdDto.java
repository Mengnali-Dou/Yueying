package com.yueying.backendapi.model.domain.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影厅id Dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieHallIdDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 9109688249053523739L;

    /**
     * 影厅id
     */
    private Long movieHallId;
}
