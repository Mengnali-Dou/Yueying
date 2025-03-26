package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.SearchMovieOrderRequest;
import com.yueying.backendapi.service.MovieOrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private MovieOrderService movieOrderService;

    @GetMapping("/movie-search")
    public ResponseEntity<Object> searchMovieOrder(@RequestParam Long userId, @RequestParam String beginDate, @RequestParam Integer orderStatus, HttpServletRequest httpServletRequest) {
        SearchMovieOrderRequest searchMovieOrderRequest = new SearchMovieOrderRequest();
        searchMovieOrderRequest.setUserId(userId);
        searchMovieOrderRequest.setBeginDate(beginDate);
        searchMovieOrderRequest.setOrderStatus(orderStatus);
        return movieOrderService.searchMovieOrderService(searchMovieOrderRequest, httpServletRequest);
    }
}
