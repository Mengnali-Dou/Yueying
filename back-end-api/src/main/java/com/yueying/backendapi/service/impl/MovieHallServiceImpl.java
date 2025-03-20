package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaAdminMapper;
import com.yueying.backendapi.mapper.CinemaMapper;
import com.yueying.backendapi.mapper.MovieHallTypeMapper;
import com.yueying.backendapi.model.domain.*;
import com.yueying.backendapi.model.domain.request.*;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieHallIdDto;
import com.yueying.backendapi.model.domain.response.MovieHallInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.MovieHallService;
import com.yueying.backendapi.mapper.MovieHallMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.CinemaMessage.*;
import static com.yueying.backendapi.constant.MovieHallMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall(影厅)】的数据库操作Service实现
* @createDate 2025-03-04 16:20:09
*/
@Service
public class MovieHallServiceImpl extends ServiceImpl<MovieHallMapper, MovieHall>
    implements MovieHallService{

    @Resource
    private MovieHallMapper movieHallMapper;

    @Resource
    private MovieHallTypeMapper movieHallTypeMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private CinemaAdminMapper cinemaAdminMapper;

    private static CinemaMapper staticCinemaMapper;
    private static MovieHallTypeMapper staticMovieHallTypeMapper;

    @PostConstruct
    public void init() {
        staticCinemaMapper = cinemaMapper;
        staticMovieHallTypeMapper = movieHallTypeMapper;
    }

    @Override
    public ResponseEntity<Object> searchMovieHall(SearchMovieHallRequest searchMovieHallRequest) {

        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        if (searchMovieHallRequest.getCinemaId() > 0) {
            movieHallQueryWrapper.eq("cinema_id", searchMovieHallRequest);
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieHallMapper.selectList(movieHallQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addMovieHall(AddMovieHallRequest addMovieHallRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (addMovieHallRequest.getCinemaId() <= 0 || addMovieHallRequest.getMovieHallTypeId() <= 0 || addMovieHallRequest.getSeating() <= 0 || StringUtils.isBlank(addMovieHallRequest.getMovieHallName())) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 影院是否存在
        QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
        cinemaQueryWrapper.eq("cinema_id", addMovieHallRequest.getCinemaId());
        if (cinemaMapper.selectCount(cinemaQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_NONENTITY, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_admin_id", UserPublicClass.getUserId(httpServletRequest));
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影厅是否存在
        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        movieHallQueryWrapper.eq("cinema_id", addMovieHallRequest.getCinemaId());
        movieHallQueryWrapper.like("movie_hall_name", addMovieHallRequest.getMovieHallName());
        if (movieHallMapper.selectCount(movieHallQueryWrapper) > 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, MOVIE_HALL_ALREADY_EXISTS, errorResponseDto));
        }

        // 影厅类型是否存在
        QueryWrapper<MovieHallType> movieHallTypeQueryWrapper = new QueryWrapper<>();
        movieHallTypeQueryWrapper.eq("type_id", addMovieHallRequest.getMovieHallTypeId());
        if (movieHallTypeMapper.selectCount(movieHallTypeQueryWrapper) <= 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, MOVIE_HALL_TYPE_DOES_NOT_EXISTS, errorResponseDto));
        }

        // 添加影厅
        MovieHall movieHall = new MovieHall();
        movieHall.setCinemaId(addMovieHallRequest.getCinemaId());
        movieHall.setMovieHallName(addMovieHallRequest.getMovieHallName());
        movieHall.setMovieHallTypeId(addMovieHallRequest.getMovieHallTypeId());
        if (StringUtils.isBlank(addMovieHallRequest.getMovieHallPhoto())) {
            movieHall.setMovieHallPhoto(addMovieHallRequest.getMovieHallPhoto());
        }
        if (StringUtils.isBlank(addMovieHallRequest.getMovieHallProfile())) {
            movieHall.setMovieHallProfile(addMovieHallRequest.getMovieHallProfile());
        }
        movieHall.setSeating(addMovieHallRequest.getSeating());

        boolean addMovieHall = this.save(movieHall);
        if (!addMovieHall) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        // 查询新添加影厅id
        QueryWrapper<MovieHall> searchMovieHallIdQueryWrapper = new QueryWrapper<>();
        searchMovieHallIdQueryWrapper.eq("cinema_id", addMovieHallRequest.getCinemaId());
        searchMovieHallIdQueryWrapper.eq("movie_hall_name", addMovieHallRequest.getMovieHallName());
        Long movieHallId = movieHallMapper.selectOne(searchMovieHallIdQueryWrapper).getMovieHallId();

        MovieHallIdDto movieHallIdDto = new MovieHallIdDto();
        movieHallIdDto.setMovieHallId(movieHallId);
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, movieHallIdDto));
    }

    @Override
    public ResponseEntity<Object> updateMovieHall(UpdateMovieHallRequest updateMovieHallRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        MovieHall movieHall = new MovieHall();

        // 必要参数是否为空
        if (updateMovieHallRequest.getMovieHallId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_admin_id", UserPublicClass.getUserId(httpServletRequest));
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影厅是否存在
        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        movieHallQueryWrapper.eq("movie_hall_id", updateMovieHallRequest.getMovieHallId());
        if (movieHallMapper.selectCount(movieHallQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, MOVIE_HALL_DOES_NOT_EXISTS, errorResponseDto));
        }


        // 影厅类型是否存在
        if (updateMovieHallRequest.getMovieHallTypeId() > 0) {
            QueryWrapper<MovieHallType> movieHallTypeQueryWrapper = new QueryWrapper<>();
            movieHallTypeQueryWrapper.eq("type_id", updateMovieHallRequest.getMovieHallTypeId());
            if (movieHallTypeMapper.selectCount(movieHallTypeQueryWrapper) <= 0) {
                return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, MOVIE_HALL_TYPE_DOES_NOT_EXISTS, errorResponseDto));
            }
            movieHall.setMovieHallTypeId(updateMovieHallRequest.getMovieHallTypeId());
        }

        // 影厅名是否重复
        if (StringUtils.isNotBlank(updateMovieHallRequest.getMovieHallName())) {
            movieHallQueryWrapper.like("movie_hall_name", updateMovieHallRequest.getMovieHallName());
            if (movieHallMapper.selectCount(movieHallQueryWrapper) > 0) {
                return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, MOVIE_HALL_ALREADY_EXISTS, errorResponseDto));
            }
            movieHall.setMovieHallName(updateMovieHallRequest.getMovieHallName());
        }

        // 修改
        movieHall.setMovieHallId(updateMovieHallRequest.getMovieHallId());
        if (StringUtils.isNotBlank(updateMovieHallRequest.getMovieHallPhoto())) {
            movieHall.setMovieHallPhoto(updateMovieHallRequest.getMovieHallPhoto());
        }
        if (StringUtils.isNotBlank(updateMovieHallRequest.getMovieHallProfile())) {
            movieHall.setMovieHallProfile(updateMovieHallRequest.getMovieHallProfile());
        }

        boolean updateMovieHall = this.updateById(movieHall);
        if (!updateMovieHall) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_UPDATE, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> deleteMovieHall(DeleteMovieHallRequest deleteMovieHallRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (deleteMovieHallRequest.getMovieHallId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_admin_id", UserPublicClass.getUserId(httpServletRequest));
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影厅是否存在
        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        movieHallQueryWrapper.eq("movie_hall_id", deleteMovieHallRequest.getMovieHallId());
        if (movieHallMapper.selectCount(movieHallQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, MOVIE_HALL_DOES_NOT_EXISTS, errorResponseDto));
        }

        // 删除
        boolean deleted = this.removeById(deleteMovieHallRequest.getMovieHallId());
        if (!deleted) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, DELETE_FAILED, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, DELETE_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param movieHalls 影厅数据库表字段列表
     * @return 影厅信息列表
     */
    private List<MovieHallInfoDto> convertToDtoList(List<MovieHall> movieHalls) {
        return movieHalls.stream().map(MovieHallServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieHall 影厅数据库表字段
     * @return 影厅信息
     */
    private static MovieHallInfoDto convertToDto(MovieHall movieHall) {
        MovieHallInfoDto movieHallInfoDto = new MovieHallInfoDto();
        movieHallInfoDto.setMovieHallId(movieHall.getMovieHallId());
        movieHallInfoDto.setCinemaId(movieHall.getCinemaId());
        movieHallInfoDto.setCinemaName(staticCinemaMapper.selectById(movieHall.getCinemaId()).getCinemaName());
        movieHallInfoDto.setMovieHallName(movieHall.getMovieHallName());
        movieHallInfoDto.setMovieHallTypeId(movieHall.getMovieHallTypeId());
        movieHallInfoDto.setMovieHallTypeName(staticMovieHallTypeMapper.selectById(movieHall.getMovieHallTypeId()).getTypeName());
        movieHallInfoDto.setMovieHallPhoto(movieHall.getMovieHallPhoto());
        movieHallInfoDto.setMovieHallProfile(movieHall.getMovieHallProfile());
        movieHallInfoDto.setSeating(movieHall.getSeating());
        return movieHallInfoDto;
    }
}




