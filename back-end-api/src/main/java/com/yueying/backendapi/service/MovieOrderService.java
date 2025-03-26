package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.BookMovieRequest;
import com.yueying.backendapi.model.domain.request.MovieRefundManageRequest;
import com.yueying.backendapi.model.domain.request.MovieRefundRequest;
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

    /**
     * 影片下单
     * @param bookMovieRequest 影片下单请求体
     * @param httpServletRequest http请求信息
     * @return 是否下单成功
     */
    ResponseEntity<Object> bookMovie(BookMovieRequest bookMovieRequest, HttpServletRequest httpServletRequest);

    /**
     * 影片退票申请
     * @param movieRefundRequest 影片退票申请请求体
     * @param httpServletRequest http请求信息
     * @return 是否申请成功
     */
    ResponseEntity<Object> movieRefund(MovieRefundRequest movieRefundRequest, HttpServletRequest httpServletRequest);

    /**
     * 退票申请处理
     * @param movieRefundManageRequest 退票申请处理请求体
     * @param httpServletRequest http请求信息
     * @return 是否处理成功
     */
    ResponseEntity<Object> movieRefundManage(MovieRefundManageRequest movieRefundManageRequest, HttpServletRequest httpServletRequest);
}
