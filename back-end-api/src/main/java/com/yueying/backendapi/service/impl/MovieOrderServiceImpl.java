package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.*;
import com.yueying.backendapi.model.domain.MovieOrder;
import com.yueying.backendapi.model.domain.MovieSession;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.SearchMovieOrderRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieOrderInfoDto;
import com.yueying.backendapi.model.domain.response.MovieSessionInfoDto;
import com.yueying.backendapi.service.MovieOrderService;
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

import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.SEARCH_SUCCESSFULLY;
import static com.yueying.backendapi.constant.UserConstant.*;

/**
* @author <a href="mengnalidou.icu">mengnali_dou</a>
* @description 针对表【tb_movie_order(订单)】的数据库操作Service实现
* @createDate 2025-03-26 11:08:33
*/
@Service
public class MovieOrderServiceImpl extends ServiceImpl<MovieOrderMapper, MovieOrder>
    implements MovieOrderService{

    @Resource
    private MovieOrderMapper movieOrderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MovieSessionMapper movieSessionMapper;

    @Resource
    private MovieHallMapper movieHallMapper;

    private static UserMapper staticUserMapper;
    private static CinemaMapper staticCinemaMapper;
    private static MovieMapper staticMovieMapper;
    private static MovieSessionMapper staticMovieSessionMapper;
    private static MovieHallMapper staticMovieHallMapper;

    @PostConstruct
    private void init() {
        staticUserMapper = userMapper;
        staticCinemaMapper = cinemaMapper;
        staticMovieMapper = movieMapper;
        staticMovieSessionMapper = movieSessionMapper;
        staticMovieHallMapper = movieHallMapper;
    }

    @Override
    public ResponseEntity<Object> searchMovieOrderService(SearchMovieOrderRequest searchMovieOrderRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 是否登陆
        if (!UserPublicClass.isLogin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, USER_NOT_LOGGED_IN, errorResponseDto));
        }

        QueryWrapper<MovieOrder> movieOrderQueryWrapper = new QueryWrapper<>();
        Object userInfo = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userInfo;

        // 是管理员权限可以使用用户id查询
        if (UserPublicClass.isAdmin(httpServletRequest) && searchMovieOrderRequest.getUserId() > 0) {
            movieOrderQueryWrapper.eq("user_id", searchMovieOrderRequest.getUserId());
        }

        // 普通用户权限，不使用用户id查询
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            movieOrderQueryWrapper.eq("user_id", user.getUserId());
        }

        // 搜索
        if (StringUtils.isNotBlank(searchMovieOrderRequest.getBeginDate())) {
            movieOrderQueryWrapper.like("begin_time", "%" + searchMovieOrderRequest.getBeginDate() + "%");
        }
        if (searchMovieOrderRequest.getOrderStatus() > -1) {
            movieOrderQueryWrapper.eq("order_status", searchMovieOrderRequest.getOrderStatus());
        }

        return ResponseEntity.ok(ResponseData.responseData(OK, SEARCH_SUCCESSFULLY, convertToDtoList(movieOrderMapper.selectList(movieOrderQueryWrapper))));
    }

    /**
     * 数据格式转换
     * @param movieOrders 影片订单数据库表字段列表
     * @return 影片订单信息列表
     */
    private List<MovieOrderInfoDto> convertToDtoList(List<MovieOrder> movieOrders) {
        return movieOrders.stream().map(MovieOrderServiceImpl::convertToDto).collect(Collectors.toList());
    }

    /**
     * 数据格式转换
     * @param movieOrder 影片订单数据库表字段
     * @return 影片订单信息
     */
    private static MovieOrderInfoDto convertToDto(MovieOrder movieOrder) {
        MovieOrderInfoDto movieOrderInfoDto = new MovieOrderInfoDto();
        movieOrderInfoDto.setOrderId(movieOrder.getOrderId());
        movieOrderInfoDto.setUserId(movieOrder.getUserId());
        movieOrderInfoDto.setUserAccount(staticUserMapper.selectById(movieOrder.getUserId()).getUserAccount());
        movieOrderInfoDto.setMovieSessionInfo(convertMovieSessionToMovieSessionInfoDto(staticMovieSessionMapper.selectById(movieOrder.getSessionId())));
        movieOrderInfoDto.setSeat(movieOrder.getSeat());
        movieOrderInfoDto.setBeginTime(PublicMethods.dateTimeConvertToString(movieOrder.getBeginTime()));
        movieOrderInfoDto.setContact(movieOrder.getContact());
        movieOrderInfoDto.setSpectator(movieOrder.getSpectator());
        movieOrderInfoDto.setOrderPrice(movieOrder.getOrderPrice());
        movieOrderInfoDto.setOrderStatus(movieOrder.getOrderStatus());
        movieOrderInfoDto.setOrderTime(PublicMethods.dateTimeConvertToString(movieOrder.getCreateTime()));
        return movieOrderInfoDto;
    }

    /**
     * 数据格式转换
     * @param movieSession 影片场次数据库表字段
     * @return 影片场次信息
     */
    private static MovieSessionInfoDto convertMovieSessionToMovieSessionInfoDto(MovieSession movieSession) {
        MovieSessionInfoDto movieSessionInfoDto = new MovieSessionInfoDto();
        movieSessionInfoDto.setSessionId(movieSession.getSessionId());
        movieSessionInfoDto.setMovieId(movieSession.getMovieId());
        movieSessionInfoDto.setMovieName(staticMovieMapper.selectById(movieSession.getMovieId()).getMovieName());
        movieSessionInfoDto.setCinemaId(movieSession.getCinemaId());
        movieSessionInfoDto.setCinemaName(staticCinemaMapper.selectById(movieSession.getCinemaId()).getCinemaName());
        movieSessionInfoDto.setHallId(movieSession.getHallId());
        movieSessionInfoDto.setHallName(staticMovieHallMapper.selectById(movieSession.getCinemaId()).getMovieHallName());
        movieSessionInfoDto.setMovieRuntime(PublicMethods.dateTimeConvertToString(movieSession.getMovieRuntime()));
        movieSessionInfoDto.setPrice(movieSession.getPrice());
        movieSessionInfoDto.setTicketsLeft(movieSession.getTicketsLeft());
        return movieSessionInfoDto;
    }
}




