package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieHallSeat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.AddMovieHallSeatRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieHallSeatRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieHallSeatRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    /**
     * 添加影厅座位
     * @param addMovieHallSeatRequestList 添加影厅座位请求体列表
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addMovieHallSeat(List<AddMovieHallSeatRequest> addMovieHallSeatRequestList, HttpServletRequest httpServletRequest);

    /**
     * 修改影厅座位信息
     * @param updateMovieHallSeatRequestList 修改影厅座位信息请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateMovieHallSeat(List<UpdateMovieHallSeatRequest> updateMovieHallSeatRequestList, HttpServletRequest httpServletRequest);
}
