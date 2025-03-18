package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieHall;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddMovieHallRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieHallRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieHallRequest;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * 添加影厅
     * @param addMovieHallRequest 添加影厅请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addMovieHall(AddMovieHallRequest addMovieHallRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改影厅信息
     * @param updateMovieHallRequest 修改影厅信息请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateMovieHall(UpdateMovieHallRequest updateMovieHallRequest, HttpServletRequest httpServletRequest);
}
