package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchMovieOrderRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_order(订单)】的数据库操作Service
* @createDate 2025-03-26 11:08:33
*/
public interface MovieOrderService extends IService<MovieOrder> {

    /**
     * 搜索影片订单
     * @param searchMovieOrderRequest 搜索影片订单请求体
     * @param httpServletRequest http请求信息
     * @return 影片订单列表
     */
    ResponseEntity<Object> searchMovieOrderService(SearchMovieOrderRequest searchMovieOrderRequest, HttpServletRequest httpServletRequest);
}
