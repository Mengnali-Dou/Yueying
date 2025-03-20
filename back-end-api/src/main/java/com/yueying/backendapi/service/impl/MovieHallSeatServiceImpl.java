package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaAdminMapper;
import com.yueying.backendapi.mapper.MovieHallMapper;
import com.yueying.backendapi.model.domain.CinemaAdmin;
import com.yueying.backendapi.model.domain.MovieHall;
import com.yueying.backendapi.model.domain.MovieHallSeat;
import com.yueying.backendapi.model.domain.request.AddMovieHallSeatRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieHallSeatRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieHallSeatDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.MovieHallSeatService;
import com.yueying.backendapi.mapper.MovieHallSeatMapper;
import com.yueying.backendapi.utils.ResponseData;
import com.yueying.backendapi.utils.UserPublicClass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.MovieHallMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_hall_seat(影厅座位（每个座位一条数据）)】的数据库操作Service实现
* @createDate 2025-03-17 16:43:11
*/
@Service
public class MovieHallSeatServiceImpl extends ServiceImpl<MovieHallSeatMapper, MovieHallSeat>
    implements MovieHallSeatService{

    @Resource
    private MovieHallSeatMapper movieHallSeatMapper;

    @Resource
    private MovieHallMapper movieHallMapper;

    @Resource
    private CinemaAdminMapper cinemaAdminMapper;

    @Override
    public ResponseEntity<Object> searchMovieHallSeat(SearchMovieHallSeatRequest searchMovieHallSeatRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (searchMovieHallSeatRequest.getMovieHallId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 影厅是否存在
        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        movieHallQueryWrapper.eq("movie_hall_id", searchMovieHallSeatRequest.getMovieHallId());
        if (movieHallMapper.selectCount(movieHallQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, MOVIE_HALL_DOES_NOT_EXISTS, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_id", movieHallMapper.selectById(searchMovieHallSeatRequest.getMovieHallId()).getCinemaId());
        cinemaAdminQueryWrapper.eq("user_id", UserPublicClass.getUserId(httpServletRequest));
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        QueryWrapper<MovieHallSeat> movieHallSeatQueryWrapper = new QueryWrapper<>();
        movieHallSeatQueryWrapper.eq("movie_hall_id", searchMovieHallSeatRequest.getMovieHallId());
        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieHallSeatMapper.selectList(movieHallSeatQueryWrapper))));
    }

    @Override
    public ResponseEntity<Object> addMovieHallSeat(List<AddMovieHallSeatRequest> addMovieHallSeatRequestList, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 添加
        boolean addMovieHallSeats = this.saveBatch(convertAddMovieHallSeatListToMovieHallSeatList(addMovieHallSeatRequestList));

        if (!addMovieHallSeats) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    /**
     * 数据格式转换
     * @param movieHallSeats 影厅座位信息数据库表字段列表
     * @return 影厅座位信息列表
     */
    private List<MovieHallSeatDto> convertToDtoList(List<MovieHallSeat> movieHallSeats) {
        return movieHallSeats.stream().map(MovieHallSeatServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieHallSeat 影厅座位数据库表字段
     * @return 影厅座位信息
     */
    private static MovieHallSeatDto convertToDto(MovieHallSeat movieHallSeat) {
        MovieHallSeatDto movieHallSeatDto = new MovieHallSeatDto();
        movieHallSeatDto.setMovieSeatId(movieHallSeat.getMovieSeatId());
        movieHallSeatDto.setMovieHallId(movieHallSeat.getMovieHallId());
        movieHallSeatDto.setRowNumbers(movieHallSeat.getRowNumbers());
        movieHallSeatDto.setColNumbers(movieHallSeat.getColNumbers());
        movieHallSeatDto.setSeatType(movieHallSeat.getSeatType());
        return movieHallSeatDto;
    }

    /**
     * 数据格式转换
     * @param addMovieHallSeatRequestList 添加影院座位请求体列表
     * @return 影厅座位数据库表字段列表
     */
    private List<MovieHallSeat> convertAddMovieHallSeatListToMovieHallSeatList(List<AddMovieHallSeatRequest> addMovieHallSeatRequestList) {
        return addMovieHallSeatRequestList.stream().map(MovieHallSeatServiceImpl::convertAddMovieHallSeatToMovieHallSeat).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param addMovieHallSeatRequest 添加影院座位请求体
     * @return 影厅座位数据库表字段
     */
    private static MovieHallSeat convertAddMovieHallSeatToMovieHallSeat(AddMovieHallSeatRequest addMovieHallSeatRequest) {
        MovieHallSeat movieHallSeat = new MovieHallSeat();
        movieHallSeat.setMovieHallId(addMovieHallSeatRequest.getMovieHallId());
        movieHallSeat.setRowNumbers(addMovieHallSeatRequest.getRowNumbers());
        movieHallSeat.setColNumbers(addMovieHallSeatRequest.getColNumbers());
        movieHallSeat.setSeatType(addMovieHallSeatRequest.getSeatType());
        return movieHallSeat;
    }
}




