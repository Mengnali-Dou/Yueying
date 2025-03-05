package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaMapper;
import com.yueying.backendapi.mapper.UserMapper;
import com.yueying.backendapi.model.domain.Cinema;
import com.yueying.backendapi.model.domain.CinemaAdmin;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.SearchCinemaAdminRequest;
import com.yueying.backendapi.model.domain.response.CinemaAdminDto;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.service.CinemaAdminService;
import com.yueying.backendapi.mapper.CinemaAdminMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;
import static com.yueying.backendapi.constant.UserConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_cinema_admin(影院管理员)】的数据库操作Service实现
* @createDate 2025-03-05 16:31:28
*/
@Service
public class CinemaAdminServiceImpl extends ServiceImpl<CinemaAdminMapper, CinemaAdmin>
    implements CinemaAdminService{

    @Resource
    private CinemaAdminMapper cinemaAdminMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    private static UserMapper staticUserMapper;

    private static CinemaMapper staticCinemaMapper;

    @PostConstruct
    public void init() {
        staticUserMapper = userMapper;
        staticCinemaMapper = cinemaMapper;
    }

    @Override
    public ResponseEntity<Object> searchCinemaAdmin(SearchCinemaAdminRequest searchCinemaAdminRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 用户是否存在
        if (searchCinemaAdminRequest.getUserId() > 0) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("user_id", searchCinemaAdminRequest.getUserId());
            long userNum = userMapper.selectCount(userQueryWrapper);
            if (userNum <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, USER_DOES_NOT_EXISTS, errorResponseDto));
            }
            cinemaAdminQueryWrapper.eq("user_id", searchCinemaAdminRequest.getUserId());
        }

        // 影院是否存在
        if (searchCinemaAdminRequest.getCinemaId() > 0) {
            QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
            cinemaQueryWrapper.eq("cinema_id", searchCinemaAdminRequest.getCinemaId());
            long cinemaNum = cinemaMapper.selectCount(cinemaQueryWrapper);
            if (cinemaNum <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, USER_DOES_NOT_EXISTS, errorResponseDto));
            }
            cinemaAdminQueryWrapper.eq("cinema_id", searchCinemaAdminRequest.getCinemaId());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(cinemaAdminMapper.selectList(cinemaAdminQueryWrapper))));
    }

    /**
     * 数据格式转换
     * @param cinemaAdminList 影院管理员数据库表字段列表
     * @return 影院管理员信息列表
     */
    private List<CinemaAdminDto> convertToDtoList(List<CinemaAdmin> cinemaAdminList) {
        return cinemaAdminList.stream().map(CinemaAdminServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param cinemaAdmin 影院管理员数据库表字段
     * @return 影院管理员信息
     */
    private static CinemaAdminDto convertToDto(CinemaAdmin cinemaAdmin) {
        CinemaAdminDto cinemaAdminDto = new CinemaAdminDto();
        cinemaAdminDto.setCinemaId(cinemaAdmin.getCinemaId());
        cinemaAdminDto.setCinemaName(staticCinemaMapper.selectById(cinemaAdmin.getCinemaId()).getCinemaName());
        cinemaAdminDto.setUserId(cinemaAdmin.getUserId());
        cinemaAdminDto.setUserAccount(staticUserMapper.selectById(cinemaAdmin.getUserId()).getUserAccount());
        return cinemaAdminDto;
    }
}




