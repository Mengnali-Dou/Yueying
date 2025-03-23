package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.model.domain.MovieSessionSeat;
import com.yueying.backendapi.model.domain.request.SearchMovieSessionSeatRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieSessionSeatDto;
import com.yueying.backendapi.service.MovieSessionSeatService;
import com.yueying.backendapi.mapper.MovieSessionSeatMapper;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.MovieMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_session_seat(电影场次座位)】的数据库操作Service实现
* @createDate 2025-03-23 10:29:46
*/
@Service
public class MovieSessionSeatServiceImpl extends ServiceImpl<MovieSessionSeatMapper, MovieSessionSeat>
    implements MovieSessionSeatService{

    @Resource
    private MovieSessionSeatMapper movieSessionSeatMapper;

    @Override
    public ResponseEntity<Object> searchMovieSessionSeat(SearchMovieSessionSeatRequest searchMovieSessionSeatRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (searchMovieSessionSeatRequest.getMovieSessionId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 是否存在
        QueryWrapper<MovieSessionSeat> movieSessionSeatQueryWrapper = new QueryWrapper<>();
        movieSessionSeatQueryWrapper.eq("session_id", searchMovieSessionSeatRequest.getMovieSessionId());
        if (movieSessionSeatMapper.selectCount(movieSessionSeatQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_SESSION_NONENTITY, errorResponseDto));
        }

        return ResponseEntity.status(OK).body(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieSessionSeatMapper.selectList(movieSessionSeatQueryWrapper))));
    }

    /**
     * 数据格式转换
     * @param movieSessionSeatList 影片场次座位数据库表字段列表
     * @return 影片场次座位信息列表
     */
    private List<MovieSessionSeatDto> convertToDtoList(List<MovieSessionSeat> movieSessionSeatList) {
        return movieSessionSeatList.stream().map(MovieSessionSeatServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieSessionSeat 影片场次座位数据库表字段
     * @return 影片场次座位信息
     */
    private static MovieSessionSeatDto convertToDto(MovieSessionSeat movieSessionSeat) {
        MovieSessionSeatDto movieSessionSeatDto = new MovieSessionSeatDto();
        movieSessionSeatDto.setMovieSessionSeatId(movieSessionSeat.getMovieSessionSeatId());
        movieSessionSeatDto.setSessionId(movieSessionSeat.getSessionId());
        movieSessionSeatDto.setRowNumbers(movieSessionSeat.getRowNumbers());
        movieSessionSeatDto.setColNumbers(movieSessionSeat.getColNumbers());
        movieSessionSeatDto.setSold(0);
        return movieSessionSeatDto;
    }
}




