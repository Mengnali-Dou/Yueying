package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddMovieTypeRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieTypeRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieTypeRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_type(影片类型)】的数据库操作Service
* @createDate 2025-04-02 21:43:58
*/
public interface MovieTypeService extends IService<MovieType> {

    /**
     * 搜索影片类型
     * @param searchMovieTypeRequest 搜索影片类型请求体
     * @return 影片类型列表
     */
    ResponseEntity<Object> searchMovieType(SearchMovieTypeRequest searchMovieTypeRequest);

    /**
     * 添加影片类型
     * @param addMovieTypeRequest 添加影片类型请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addMovieType(AddMovieTypeRequest addMovieTypeRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改影片类型
     * @param updateMovieTypeRequest 修改影片类型请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateMovieType(UpdateMovieTypeRequest updateMovieTypeRequest, HttpServletRequest httpServletRequest);
}
