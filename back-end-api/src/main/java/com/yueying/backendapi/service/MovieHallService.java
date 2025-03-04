package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieHall;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchMovieHallRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall(影厅)】的数据库操作Service
* @createDate 2025-03-04 16:20:09
*/
public interface MovieHallService extends IService<MovieHall> {

    /**
     * 搜索影厅
     * @param searchMovieHallRequest 搜索影厅请求体
     * @return 影厅信息
     */
    ResponseEntity<Object> searchMovieHall(SearchMovieHallRequest searchMovieHallRequest);
}
