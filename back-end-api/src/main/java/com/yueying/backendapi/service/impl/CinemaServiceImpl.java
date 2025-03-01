package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaMapper;
import com.yueying.backendapi.model.domain.Cinema;
import com.yueying.backendapi.model.domain.request.AddCinemaRequest;
import com.yueying.backendapi.model.domain.request.DeleteCinemaRequest;
import com.yueying.backendapi.model.domain.request.SearchCinemaRequest;
import com.yueying.backendapi.model.domain.request.UpdateCinemaInfoRequest;
import com.yueying.backendapi.model.domain.response.CinemaInfoDto;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.CinemaService;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.CinemaMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_cinema(影院)】的数据库操作Service实现
* @createDate 2025-02-28 09:39:28
*/
@Service
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema>
    implements CinemaService {

    @Resource
    private CinemaMapper cinemaMapper;

    @Override
    public ResponseEntity<Object> searchCinema(SearchCinemaRequest searchCinemaRequest) {

        QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(searchCinemaRequest.getCinemaName())) {
            cinemaQueryWrapper.like("cinema_name", searchCinemaRequest.getCinemaName());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(cinemaMapper.selectList(cinemaQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addCinema(AddCinemaRequest addCinemaRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 参数是否为空
        if (!StringUtils.isNoneBlank(addCinemaRequest.getCinemaName(), addCinemaRequest.getCinemaAddress())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 添加
        Cinema cinema = new Cinema();
        cinema.setCinemaName(addCinemaRequest.getCinemaName());
        cinema.setCinemaAddress(addCinemaRequest.getCinemaAddress());
        if (StringUtils.isNotBlank(addCinemaRequest.getCinemaProfile())) {
            cinema.setCinemaProfile(addCinemaRequest.getCinemaProfile());
        }
        if (StringUtils.isNotBlank(addCinemaRequest.getCinemaService())) {
            cinema.setCinemaService(addCinemaRequest.getCinemaService());
        }
        if (StringUtils.isNotBlank(addCinemaRequest.getCinemaPhone())) {
            cinema.setCinemaPhone(addCinemaRequest.getCinemaPhone());
        }
        if (StringUtils.isNotBlank(addCinemaRequest.getCinemaTraffic())) {
            cinema.setCinemaTraffic(addCinemaRequest.getCinemaTraffic());
        }

        boolean addCinema = this.save(cinema);
        if (!addCinema) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> updateCinemaInfo(UpdateCinemaInfoRequest updateCinemaInfoRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
        cinemaQueryWrapper.eq("cinema_id", updateCinemaInfoRequest.getCinemaId());
        if (cinemaMapper.selectCount(cinemaQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_NONENTITY, errorResponseDto));
        }

        // 修改
        Cinema cinema = new Cinema();
        cinema.setCinemaId(updateCinemaInfoRequest.getCinemaId());
        if (StringUtils.isNotBlank(updateCinemaInfoRequest.getCinemaName())) {
            cinema.setCinemaName(updateCinemaInfoRequest.getCinemaName());
        }
        if (StringUtils.isNotBlank(updateCinemaInfoRequest.getCinemaAddress())) {
            cinema.setCinemaAddress(updateCinemaInfoRequest.getCinemaAddress());
        }
        if (StringUtils.isNotBlank(updateCinemaInfoRequest.getCinemaProfile())) {
            cinema.setCinemaProfile(updateCinemaInfoRequest.getCinemaProfile());
        }
        if (StringUtils.isNotBlank(updateCinemaInfoRequest.getCinemaService())) {
            cinema.setCinemaService(updateCinemaInfoRequest.getCinemaService());
        }
        if (StringUtils.isNotBlank(updateCinemaInfoRequest.getCinemaPhone())) {
            cinema.setCinemaPhone(updateCinemaInfoRequest.getCinemaPhone());
        }
        if (StringUtils.isNotBlank(updateCinemaInfoRequest.getCinemaTraffic())) {
            cinema.setCinemaTraffic(updateCinemaInfoRequest.getCinemaTraffic());
        }
        cinemaMapper.updateById(cinema);

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, UPDATE_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> deleteCinema(DeleteCinemaRequest deleteCinemaRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
        cinemaQueryWrapper.eq("cinema_id", deleteCinemaRequest.getCinemaId());
        if (cinemaMapper.selectCount(cinemaQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_NONENTITY, errorResponseDto));
        }

        // 删除
        boolean deleted = this.removeById(deleteCinemaRequest.getCinemaId());
        if (!deleted) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, DELETE_FAILED, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, DELETE_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param cinemas 影院数据库表字段列表
     * @return 影院信息列表
     */
    private List<CinemaInfoDto> convertToDtoList(List<Cinema> cinemas) {
        return cinemas.stream().map(CinemaServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param cinema 影院数据库表字段
     * @return 影院信息
     */
    private static CinemaInfoDto convertToDto(Cinema cinema) {
        CinemaInfoDto cinemaInfoDto = new CinemaInfoDto();
        cinemaInfoDto.setCinemaId(cinema.getCinemaId());
        cinemaInfoDto.setCinemaName(cinema.getCinemaName());
        cinemaInfoDto.setCinemaAddress(cinema.getCinemaAddress());
        cinemaInfoDto.setCinemaProfile(cinema.getCinemaProfile());
        cinemaInfoDto.setCinemaService(cinema.getCinemaService());
        cinemaInfoDto.setCinemaPhone(cinema.getCinemaPhone());
        cinemaInfoDto.setCinemaTraffic(cinema.getCinemaTraffic());
        return cinemaInfoDto;
    }
}




