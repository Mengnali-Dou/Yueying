package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.MovieSession;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchMovieSessionRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_session(影片场次)】的数据库操作Service
* @createDate 2025-03-21 21:13:25
*/
public interface MovieSessionService extends IService<MovieSession> {

    /**
     * 搜索影片场次
     * @param searchMovieSessionRequest 搜索影片场次请求体
     * @return 影片场次列表
     */
    ResponseEntity<Object> searchMovieSession(SearchMovieSessionRequest searchMovieSessionRequest);
}
