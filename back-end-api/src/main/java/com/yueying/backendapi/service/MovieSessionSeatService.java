package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieSessionSeat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchMovieSessionSeatRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_session_seat(电影场次座位)】的数据库操作Service
* @createDate 2025-03-23 10:29:46
*/
public interface MovieSessionSeatService extends IService<MovieSessionSeat> {

    /**
     * 搜索影片场次座位信息
     * @param searchMovieSessionSeatRequest 搜索影片场次座位信息请求体
     * @return 影片场次座位信息列表
     */
    ResponseEntity<Object> searchMovieSessionSeat(SearchMovieSessionSeatRequest searchMovieSessionSeatRequest);
}
