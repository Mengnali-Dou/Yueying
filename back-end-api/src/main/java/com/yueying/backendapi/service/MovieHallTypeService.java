package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieHallType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddMovieHallTypeRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieHallTypeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall_type(影厅类型)】的数据库操作Service
* @createDate 2025-03-01 19:22:45
*/
public interface MovieHallTypeService extends IService<MovieHallType> {

    /**
     * 搜索影厅类型
     * @param searchMovieHallTypeRequest 搜索影厅类型请求体
     * @return 影厅类型信息列表
     */
    ResponseEntity<Object> searchMovieHallType(SearchMovieHallTypeRequest searchMovieHallTypeRequest);

    /**
     * 添加影厅类型
     * @param addMovieHallTypeRequest 添加影厅类型请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addMovieHallType(AddMovieHallTypeRequest addMovieHallTypeRequest, HttpServletRequest httpServletRequest);
}
