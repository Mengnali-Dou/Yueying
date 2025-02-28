package com.yueying.backendapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.Cinema;
import com.yueying.backendapi.model.domain.request.SearchCinemaRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_cinema(影院)】的数据库操作Service
* @createDate 2025-02-28 09:39:28
*/
public interface CinemaService extends IService<Cinema> {

    /**
     * 影院查询
     * @param searchCinemaRequest 影院查询请求体
     * @return 影院信息列表
     */
    ResponseEntity<Object> searchCinema(SearchCinemaRequest searchCinemaRequest);
}
