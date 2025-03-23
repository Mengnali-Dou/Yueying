package com.yueying.backendapi.model.domain.response;

import lombok.Data;

/**
 * 影片场次信息dto
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class MovieSessionInfoDto {

    /**
     * id
     */
    private Long sessionId;

    /**
     * 影片ID
     */
    private Long movieId;

    /**
     * 影片名
     */
    private String movieName;

    /**
     * 影院ID
     */
    private Long cinemaId;

    /**
     * 影院名
     */
    private String cinemaName;

    /**
     * 影厅ID
     */
    private Long hallId;

    /**
     * 影厅名
     */
    private String hallName;

    /**
     * 放映时间
     */
    private String movieRuntime;

    /**
     * 票价
     */
    private Integer price;

    /**
     * 余票
     */
    private Long ticketsLeft;
}
