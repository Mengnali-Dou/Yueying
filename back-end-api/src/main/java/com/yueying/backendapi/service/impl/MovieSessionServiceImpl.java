package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.CinemaMapper;
import com.yueying.backendapi.mapper.MovieHallMapper;
import com.yueying.backendapi.mapper.MovieMapper;
import com.yueying.backendapi.model.domain.Cinema;
import com.yueying.backendapi.model.domain.Movie;
import com.yueying.backendapi.model.domain.MovieSession;
import com.yueying.backendapi.model.domain.request.SearchMovieSessionRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieSessionInfoDto;
import com.yueying.backendapi.service.MovieSessionService;
import com.yueying.backendapi.mapper.MovieSessionMapper;
import com.yueying.backendapi.utils.PublicMethods;
import com.yueying.backendapi.utils.ResponseData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yueying.backendapi.constant.CinemaMessage.*;
import static com.yueying.backendapi.constant.MovieMessage.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_session(影片场次)】的数据库操作Service实现
* @createDate 2025-03-21 21:13:25
*/
@Service
public class MovieSessionServiceImpl extends ServiceImpl<MovieSessionMapper, MovieSession>
    implements MovieSessionService{

    @Resource
    private MovieSessionMapper movieSessionMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MovieHallMapper movieHallMapper;

    private static CinemaMapper staticCinemaMapper;
    private static MovieMapper staticMovieMapper;
    private static MovieHallMapper staticMovieHallMapper;

    @PostConstruct
    private void init() {
        staticCinemaMapper = cinemaMapper;
        staticMovieMapper = movieMapper;
        staticMovieHallMapper = movieHallMapper;
    }

    @Override
    public ResponseEntity<Object> searchMovieSession(SearchMovieSessionRequest searchMovieSessionRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        QueryWrapper<MovieSession> movieSessionQueryWrapper = new QueryWrapper<>();

        if (searchMovieSessionRequest.getSessionId() > 0) {
            movieSessionQueryWrapper.eq("session_id", searchMovieSessionRequest.getSessionId());
        }

        if (searchMovieSessionRequest.getMovieId() > 0) {
            // 影片是否存在
            QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<>();
            movieQueryWrapper.eq("movie_id", searchMovieSessionRequest.getMovieId());
            if (movieMapper.selectCount(movieQueryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_NONENTITY, errorResponseDto));
            }
            movieSessionQueryWrapper.eq("movie_id", searchMovieSessionRequest.getMovieId());
        }

        if (searchMovieSessionRequest.getCinemaId() > 0) {
            // 影院是否存在
            QueryWrapper<Cinema> cinemaQueryWrapper = new QueryWrapper<>();
            cinemaQueryWrapper.eq("cinema_id", searchMovieSessionRequest.getCinemaId());
            if (cinemaMapper.selectCount(cinemaQueryWrapper) <= 0) {
                return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, CINEMA_NONENTITY, errorResponseDto));
            }
            movieSessionQueryWrapper.eq("cinema_id", searchMovieSessionRequest.getCinemaId());
        }

        if (StringUtils.isNotBlank(searchMovieSessionRequest.getMovieRunDate())) {
            movieSessionQueryWrapper.like("movie_runtime", "%" + searchMovieSessionRequest.getMovieRunDate() + "%");
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieSessionMapper.selectList(movieSessionQueryWrapper))));
    }

    /**
     * 数据格式转换
     * @param movieSessionList 影片场次数据库表字段列表
     * @return 影片场次信息列表
     */
    private List<MovieSessionInfoDto> convertToDtoList(List<MovieSession> movieSessionList) {
        return movieSessionList.stream().map(MovieSessionServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieSession 影片场次数据库表字段
     * @return 影片场次信息
     */
    private static MovieSessionInfoDto convertToDto(MovieSession movieSession) {
        MovieSessionInfoDto movieSessionInfoDto = new MovieSessionInfoDto();
        movieSessionInfoDto.setSessionId(movieSession.getSessionId());
        movieSessionInfoDto.setMovieId(movieSession.getMovieId());
        movieSessionInfoDto.setMovieName(staticMovieMapper.selectById(movieSession.getMovieId()).getMovieName());
        movieSessionInfoDto.setCinemaId(movieSession.getCinemaId());
        movieSessionInfoDto.setCinemaName(staticCinemaMapper.selectById(movieSession.getCinemaId()).getCinemaName());
        movieSessionInfoDto.setHallId(movieSession.getHallId());
        movieSessionInfoDto.setHallName(staticMovieHallMapper.selectById(movieSession.getHallId()).getMovieHallName());
        movieSessionInfoDto.setMovieRuntime(PublicMethods.dateTimeConvertToString(movieSession.getMovieRuntime()));
        movieSessionInfoDto.setPrice(movieSession.getPrice());
        movieSessionInfoDto.setTicketsLeft(movieSession.getTicketsLeft());
        return movieSessionInfoDto;
    }
}




