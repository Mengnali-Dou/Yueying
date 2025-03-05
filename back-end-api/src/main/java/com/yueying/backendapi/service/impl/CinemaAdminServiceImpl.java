package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaMapper;
import com.yueying.backendapi.mapper.UserMapper;
import com.yueying.backendapi.model.domain.Cinema;
import com.yueying.backendapi.model.domain.CinemaAdmin;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.AddCinemaAdminRequest;
import com.yueying.backendapi.model.domain.request.SearchCinemaAdminRequest;
import com.yueying.backendapi.model.domain.request.UpdateCinemaAdminRequest;
import com.yueying.backendapi.model.domain.response.CinemaAdminDto;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
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

import static com.yueying.backendapi.constant.AdminMessage.*;
import static com.yueying.backendapi.constant.CinemaMessage.*;
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
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_NONENTITY, errorResponseDto));
            }
            cinemaAdminQueryWrapper.eq("cinema_id", searchCinemaAdminRequest.getCinemaId());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(cinemaAdminMapper.selectList(cinemaAdminQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addCinemaAdmin(AddCinemaAdminRequest addCinemaAdminRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (addCinemaAdminRequest.getCinemaId() + addCinemaAdminRequest.getUserId() < 2) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影院是否存在
        QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
        cinemaQueryWrapper.eq("cinema_id", addCinemaAdminRequest.getCinemaId());
        long cinemaNum = cinemaMapper.selectCount(cinemaQueryWrapper);
        if (cinemaNum <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_NONENTITY, errorResponseDto));
        }

        // 用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_id", addCinemaAdminRequest.getUserId());
        long userNum = userMapper.selectCount(userQueryWrapper);
        if (userNum <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, USER_DOES_NOT_EXISTS, errorResponseDto));
        }

        // 影院管理员是否存在
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_id", addCinemaAdminRequest.getCinemaId());
        cinemaAdminQueryWrapper.eq("user_id", addCinemaAdminRequest.getUserId());
        long cinemaAdminNum = cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper);
        if (cinemaAdminNum > 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, CINEMA_ADMIN_ALREADY_EXISTS, errorResponseDto));
        }

        // 添加
        CinemaAdmin cinemaAdmin = new CinemaAdmin();
        cinemaAdmin.setCinemaId(addCinemaAdminRequest.getCinemaId());
        cinemaAdmin.setUserId(addCinemaAdminRequest.getUserId());
        boolean addCinemaAdmin = this.save(cinemaAdmin);
        if (!addCinemaAdmin) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> updateCinemaAdmin(UpdateCinemaAdminRequest updateCinemaAdminRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (updateCinemaAdminRequest.getCinemaAdminId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影院管理员是否存在
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_admin_id", updateCinemaAdminRequest.getCinemaAdminId());
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_ADMIN_NONENTITY, errorResponseDto));
        }

        // 修改
        CinemaAdmin cinemaAdmin = new CinemaAdmin();
        cinemaAdmin.setCinemaAdminId(updateCinemaAdminRequest.getCinemaAdminId());
        if (updateCinemaAdminRequest.getCinemaId() > 0) {
            cinemaAdmin.setCinemaId(updateCinemaAdminRequest.getCinemaId());
        }
        if (updateCinemaAdminRequest.getUserId() > 0) {
            cinemaAdmin.setUserId(updateCinemaAdminRequest.getUserId());
        }

        boolean updateCinemaAdmin = this.updateById(cinemaAdmin);
        if (!updateCinemaAdmin) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_UPDATE, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
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




