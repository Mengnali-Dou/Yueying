package com.yueying.backendapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.Cinema;
import com.yueying.backendapi.model.domain.request.AddCinemaRequest;
import com.yueying.backendapi.model.domain.request.SearchCinemaRequest;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * 添加影院
     * @param addCinemaRequest 添加影院请求体
     * @param httpServletRequest http请求信息
     * @return 是否添加成功
     */
    ResponseEntity<Object> addCinema(AddCinemaRequest addCinemaRequest, HttpServletRequest httpServletRequest);
}
