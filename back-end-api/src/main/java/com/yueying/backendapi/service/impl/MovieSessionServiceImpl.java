package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.*;
import com.yueying.backendapi.model.domain.*;
import com.yueying.backendapi.model.domain.request.AddMovieSessionRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieSessionRequest;
import com.yueying.backendapi.model.domain.request.UpdateMovieSessionRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieSessionInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
import com.yueying.backendapi.service.MovieSessionService;
import com.yueying.backendapi.utils.PublicMethods;
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
    private MovieSessionSeatServiceImpl movieSessionSeatService;

    @Resource
    private MovieSessionMapper movieSessionMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MovieHallMapper movieHallMapper;

    @Resource
    private CinemaAdminMapper cinemaAdminMapper;

    @Resource
    private MovieHallSeatMapper movieHallSeatMapper;

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

    @Override
    public ResponseEntity<Object> addMovieSession(AddMovieSessionRequest addMovieSessionRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (StringUtils.isBlank(addMovieSessionRequest.getMovieRuntime()) || addMovieSessionRequest.getMovieId() <= 0 || addMovieSessionRequest.getHallId() <= 0 || addMovieSessionRequest.getPrice() < 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 影厅是否存在
        QueryWrapper<MovieHall> movieHallQueryWrapper = new QueryWrapper<>();
        movieHallQueryWrapper.eq("movie_hall_id", addMovieSessionRequest.getHallId());
        if (movieHallMapper.selectCount(movieHallQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_HALL_DOES_NOT_EXISTS, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        Long cinemaId = movieHallMapper.selectById(addMovieSessionRequest.getHallId()).getCinemaId();
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_admin_id", UserPublicClass.getUserId(httpServletRequest));
        cinemaAdminQueryWrapper.eq("cinema_id", cinemaId);
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 影片是否存在
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<>();
        movieQueryWrapper.eq("movie_id", addMovieSessionRequest.getMovieId());
        if (movieMapper.selectCount(movieQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_NONENTITY, errorResponseDto));
        }

        // 添加场次
        MovieSession movieSession = new MovieSession();
        movieSession.setMovieId(addMovieSessionRequest.getMovieId());
        movieSession.setCinemaId(cinemaId);
        movieSession.setHallId(addMovieSessionRequest.getHallId());
        movieSession.setMovieRuntime(PublicMethods.stringConvertToDateTime(addMovieSessionRequest.getMovieRuntime()));
        movieSession.setPrice(addMovieSessionRequest.getPrice());

        QueryWrapper<MovieHallSeat> movieHallSeatQueryWrapper = new QueryWrapper<>();
        movieHallSeatQueryWrapper.eq("movie_hall_id", addMovieSessionRequest.getHallId());
        movieSession.setTicketsLeft(movieHallSeatMapper.selectCount(movieHallSeatQueryWrapper));

        boolean addMovieSession = this.save(movieSession);
        if (!addMovieSession) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        // 获取新添加场次id
        QueryWrapper<MovieSession> movieSessionQueryWrapper = new QueryWrapper<>();
        movieSessionQueryWrapper.eq("movie_id", addMovieSessionRequest.getMovieId());
        movieSessionQueryWrapper.eq("cinema_id", cinemaId);
        movieSessionQueryWrapper.eq("hall_id", addMovieSessionRequest.getHallId());
        movieSessionQueryWrapper.eq("movie_runtime", PublicMethods.stringConvertToDateTime(addMovieSessionRequest.getMovieRuntime()));
        Long sessionId = movieSessionMapper.selectOne(movieSessionQueryWrapper).getSessionId();

        // 添加场次座位
        List<MovieHallSeat> movieHallSeatList = movieHallSeatMapper.selectList(movieHallSeatQueryWrapper);
        boolean addMovieSessionSeat = movieSessionSeatService.saveBatch(convertMovieHallSeatListToMovieSessionSeatList(movieHallSeatList, sessionId));
        if (!addMovieSessionSeat) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_INSERT, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> updateMovieSession(UpdateMovieSessionRequest updateMovieSessionRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 必要参数是否为空
        if (updateMovieSessionRequest.getSessionId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 场次是否存在
        QueryWrapper<MovieSession> movieSessionQueryWrapper = new QueryWrapper<>();
        movieSessionQueryWrapper.eq("session_id", updateMovieSessionRequest.getSessionId());
        if (movieSessionMapper.selectCount(movieSessionQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_SESSION_NONENTITY, errorResponseDto));
        }

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest) || UserPublicClass.isCinemaAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }
        Long cinemaId = movieSessionMapper.selectById(updateMovieSessionRequest.getSessionId()).getCinemaId();
        QueryWrapper<CinemaAdmin> cinemaAdminQueryWrapper = new QueryWrapper<>();
        cinemaAdminQueryWrapper.eq("cinema_admin_id", UserPublicClass.getUserId(httpServletRequest));
        cinemaAdminQueryWrapper.eq("cinema_id", cinemaId);
        if (cinemaAdminMapper.selectCount(cinemaAdminQueryWrapper) <= 0) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 修改
        MovieSession movieSession = new MovieSession();
        movieSession.setSessionId(updateMovieSessionRequest.getSessionId());
        movieSession.setMovieRuntime(PublicMethods.stringConvertToDateTime(updateMovieSessionRequest.getMovieRuntime()));
        movieSession.setPrice(updateMovieSessionRequest.getPrice());

        boolean updated = this.updateById(movieSession);
        if (!updated) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, FAILED_TO_UPDATE, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, INSERT_SUCCESSFULLY, successResponseDto));
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

    /**
     * 数据格式转换
     * @param movieHallSeatList 影厅座位数据库表字段列表
     * @param sessionId 场次id
     * @return 影片场次座位数据库表字段列表
     */
    private List<MovieSessionSeat> convertMovieHallSeatListToMovieSessionSeatList(List<MovieHallSeat> movieHallSeatList, Long sessionId) {
        List<MovieSessionSeat> movieSessionSeatList = movieHallSeatList.stream().map(MovieSessionServiceImpl::convertMovieHallSeatToMovieSessionSeat).collect(Collectors.toList());
        movieSessionSeatList.forEach(data -> data.setSessionId(sessionId));
        return movieSessionSeatList;
    }

    /**
     * 数据格式转换
     * @param movieHallSeat 影厅座位数据库表字段
     * @return 影片场次座位数据库表字段
     */
    private static MovieSessionSeat convertMovieHallSeatToMovieSessionSeat(MovieHallSeat movieHallSeat) {
        MovieSessionSeat movieSessionSeat = new MovieSessionSeat();
        movieSessionSeat.setRowNumbers(movieHallSeat.getRowNumbers());
        movieSessionSeat.setColNumbers(movieHallSeat.getColNumbers());
        movieSessionSeat.setSold(0);
        return movieSessionSeat;
    }
}




