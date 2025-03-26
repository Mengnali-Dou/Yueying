package com.yueying.backendapi.controller;

import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.service.EventOrderService;
import com.yueying.backendapi.service.MovieOrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Resource
    private MovieOrderService movieOrderService;

    @Resource
    private EventOrderService eventOrderService;

    @GetMapping("/movie-search")
    public ResponseEntity<Object> searchMovieOrder(@RequestParam Long userId, @RequestParam String beginDate, @RequestParam Integer orderStatus, HttpServletRequest httpServletRequest) {
        SearchMovieOrderRequest searchMovieOrderRequest = new SearchMovieOrderRequest();
        searchMovieOrderRequest.setUserId(userId);
        searchMovieOrderRequest.setBeginDate(beginDate);
        searchMovieOrderRequest.setOrderStatus(orderStatus);
        return movieOrderService.searchMovieOrderService(searchMovieOrderRequest, httpServletRequest);
    }

    @PostMapping("/movie-book")
    public ResponseEntity<Object> bookMovie(@RequestBody BookMovieRequest bookMovieRequest, HttpServletRequest httpServletRequest) {
        return movieOrderService.bookMovie(bookMovieRequest, httpServletRequest);
    }

    @PutMapping("/movie-refund")
    public ResponseEntity<Object> movieRefund(@RequestBody MovieRefundRequest movieRefundRequest, HttpServletRequest httpServletRequest) {
        return movieOrderService.movieRefund(movieRefundRequest, httpServletRequest);
    }

    @PutMapping("/movie-refund-manage")
    public ResponseEntity<Object> movieRefundManage(@RequestBody MovieRefundManageRequest movieRefundManageRequest, HttpServletRequest httpServletRequest) {
        return movieOrderService.movieRefundManage(movieRefundManageRequest, httpServletRequest);
    }

    @DeleteMapping("/movie-delete")
    public ResponseEntity<Object> deleteMovieOrder(@RequestParam Long movieOrderId, HttpServletRequest httpServletRequest) {
        DeleteMovieOrderRequest deleteMovieOrderRequest = new DeleteMovieOrderRequest();
        deleteMovieOrderRequest.setMovieOrderId(movieOrderId);
        return movieOrderService.deleteMovieOrder(deleteMovieOrderRequest, httpServletRequest);
    }

    @GetMapping("/event-search")
    public ResponseEntity<Object> searchEventOrder(@RequestParam Long userId, @RequestParam Long eventId, @RequestParam String beginTime, @RequestParam Integer orderStatus, HttpServletRequest httpServletRequest) {
        SearchEventOrderRequest searchEventOrderRequest = new SearchEventOrderRequest();
        searchEventOrderRequest.setUserId(userId);
        searchEventOrderRequest.setEventId(eventId);
        searchEventOrderRequest.setBeginTime(beginTime);
        searchEventOrderRequest.setOrderStatus(orderStatus);
        return eventOrderService.searchEventOrder(searchEventOrderRequest, httpServletRequest);
    }

    @PostMapping("/event-book")
    public ResponseEntity<Object> bookEvent(@RequestBody BookEventRequest bookEventRequest, HttpServletRequest httpServletRequest) {
        return eventOrderService.bookEvent(bookEventRequest, httpServletRequest);
    }
}
