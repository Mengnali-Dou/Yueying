package com.yueying.backendapi.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 搜索电影请求体
 * @author <a href="mengnalidou.icu">mengnali_dou</a>
 */
@Data
public class SearchMovieRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -6680578494088777865L;

    /**
     * 电影名
     */
    private String movieName;

    /**
     * 电影类别
     */
    private String movieType;
}
