package com.yueying.backendapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.Movie;
import com.yueying.backendapi.model.domain.request.AddMovieRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie(影片)】的数据库操作Service
* @createDate 2025-02-27 14:27:42
*/
public interface MovieService extends IService<Movie> {

    /**
     * 影片查询
     * @param searchMovieRequest 影片查询请求体
     * @return 影片信息列表
     */
    ResponseEntity<Object> searchMovieInfo(SearchMovieRequest searchMovieRequest);

    /**
     * 添加影片
     * @param addMovieRequest 添加影片请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addMovie(AddMovieRequest addMovieRequest, HttpServletRequest httpServletRequest);
}
