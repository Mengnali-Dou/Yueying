package com.yueying.backendapi.service;

import com.yueying.backendapi.model.domain.CinemaAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yueying.backendapi.model.domain.request.SearchCinemaAdminRequest;
import com.yueying.backendapi.model.domain.request.UpdateCinemaAdminRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_cinema_admin(影院管理员)】的数据库操作Service
* @createDate 2025-03-05 16:31:28
*/
public interface CinemaAdminService extends IService<CinemaAdmin> {

    /**
     * 搜索影院管理员
     * @param searchCinemaAdminRequest 搜索影院管理员请求体
     * @param httpServletRequest http请求信息
     * @return 影院管理员列表
     */
    ResponseEntity<Object> searchCinemaAdmin(SearchCinemaAdminRequest searchCinemaAdminRequest, HttpServletRequest httpServletRequest);

    /**
     * 修改用户管理员
     * @param updateCinemaAdminRequest 修改用户管理员请求体
     * @param httpServletRequest http请求信息
     * @return 是否修改成功
     */
    ResponseEntity<Object> updateCinemaAdmin(UpdateCinemaAdminRequest updateCinemaAdminRequest, HttpServletRequest httpServletRequest);
}
