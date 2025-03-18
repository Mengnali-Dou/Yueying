package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieHallSeat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchMovieHallSeatRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall_seat(影厅座位（每个座位一条数据）)】的数据库操作Service
* @createDate 2025-03-17 16:43:11
*/
public interface MovieHallSeatService extends IService<MovieHallSeat> {

    /**
     * 搜索影厅座位
     * @param searchMovieHallSeatRequest 搜索影厅座位请求体
     * @param httpServletRequest http请求信息
     * @return 影厅座位列表
     */
    ResponseEntity<Object> searchMovieHallSeat(SearchMovieHallSeatRequest searchMovieHallSeatRequest, HttpServletRequest httpServletRequest);
}
