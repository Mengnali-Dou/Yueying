package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 影院查询请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchCinemaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6291463142668812748L;

    /**
     * 影院名
     */
    private String cinemaName;
}
