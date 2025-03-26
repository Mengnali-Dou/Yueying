package com.yueying.backendapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueying.backendapi.mapper.*;
import com.yueying.backendapi.model.domain.MovieOrder;
import com.yueying.backendapi.model.domain.MovieSession;
import com.yueying.backendapi.model.domain.MovieSessionSeat;
import com.yueying.backendapi.model.domain.User;
import com.yueying.backendapi.model.domain.request.BookMovieRequest;
import com.yueying.backendapi.model.domain.request.MovieRefundManageRequest;
import com.yueying.backendapi.model.domain.request.MovieRefundRequest;
import com.yueying.backendapi.model.domain.request.SearchMovieOrderRequest;
import com.yueying.backendapi.model.domain.response.ErrorResponseDto;
import com.yueying.backendapi.model.domain.response.MovieOrderInfoDto;
import com.yueying.backendapi.model.domain.response.MovieSessionInfoDto;
import com.yueying.backendapi.model.domain.response.SuccessResponseDto;
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

import static com.yueying.backendapi.constant.MovieMessage.*;
import static com.yueying.backendapi.constant.OrderConstant.*;
import static com.yueying.backendapi.constant.ResponseStatus.*;
import static com.yueying.backendapi.constant.UniversalConstant.*;
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
    private MovieSessionSeatMapper movieSessionSeatMapper;

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

    @Override
    public ResponseEntity<Object> bookMovie(BookMovieRequest bookMovieRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 是否登陆
        if (!UserPublicClass.isLogin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, USER_NOT_LOGGED_IN, errorResponseDto));
        }

        // 必要参数是否为空
        if (!StringUtils.isNoneBlank(bookMovieRequest.getSeat(), bookMovieRequest.getContact(), bookMovieRequest.getSpectator()) || bookMovieRequest.getSessionId() <= 0) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseData.responseData(BAD_REQUEST, PARAMETER_CANNOT_BE_NULL, errorResponseDto));
        }

        // 场次是否存在
        QueryWrapper<MovieSession> movieSessionQueryWrapper = new QueryWrapper<>();
        movieSessionQueryWrapper.eq("session_id", bookMovieRequest.getSessionId());
        if (movieSessionMapper.selectCount(movieSessionQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, MOVIE_SESSION_NONENTITY, errorResponseDto));
        }

        // 是否有余票
        if (movieSessionMapper.selectById(bookMovieRequest.getSessionId()).getTicketsLeft() <= 0) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, INSUFFICIENT_BALANCE, errorResponseDto));
        }

        // 座位是否存在
        String[] seatArea = bookMovieRequest.getSeat().split(",");
        Integer row = Integer.parseInt(seatArea[0]);
        Integer col = Integer.parseInt(seatArea[1]);
        QueryWrapper<MovieSessionSeat> movieSessionSeatQueryWrapper = new QueryWrapper<>();
        movieSessionSeatQueryWrapper.eq("session_id", bookMovieRequest.getSessionId());
        movieSessionSeatQueryWrapper.eq("row_numbers", row);
        movieSessionSeatQueryWrapper.eq("col_numbers", col);
        if (movieSessionSeatMapper.selectCount(movieSessionSeatQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, SEAT_NOT_FOUND, errorResponseDto));
        }

        // 座位是否售出
        if (movieSessionSeatMapper.selectOne(movieSessionSeatQueryWrapper).getSold() == 1) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, SEAT_HAS_SOLD, errorResponseDto));
        }

        // 获取用户信息
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User userInfo = (User) userObj;

        // 获取场次信息
        MovieSession movieSession = movieSessionMapper.selectById(bookMovieRequest.getSessionId());

        // 下单
        MovieOrder movieOrder = new MovieOrder();
        movieOrder.setUserId(userInfo.getUserId());
        movieOrder.setSessionId(bookMovieRequest.getSessionId());
        movieOrder.setSeat(bookMovieRequest.getSeat());
        movieOrder.setBeginTime(movieSession.getMovieRuntime());
        movieOrder.setContact(bookMovieRequest.getContact());
        movieOrder.setSpectator(bookMovieRequest.getSpectator());
        movieOrder.setOrderPrice(movieSession.getPrice());
        boolean bookMovie = this.save(movieOrder);
        if (!bookMovie) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseData.responseData(INTERNAL_SERVER_ERROR, BOOK_FAILED, errorResponseDto));
        }

        // 场次座位-1
        UpdateWrapper<MovieSession> movieSessionUpdateWrapper = new UpdateWrapper<>();
        movieSessionUpdateWrapper.eq("session_id", bookMovieRequest.getSessionId());
        movieSessionUpdateWrapper.setSql("tickets_left = tickets_left - 1");
        movieSessionMapper.update(movieSessionUpdateWrapper);

        // 座位状态修改
        UpdateWrapper<MovieSessionSeat> movieSessionSeatUpdateWrapper = new UpdateWrapper<>();
        movieSessionSeatUpdateWrapper.eq("session_id", bookMovieRequest.getSessionId());
        movieSessionSeatUpdateWrapper.eq("row_numbers", row);
        movieSessionSeatUpdateWrapper.eq("col_numbers", col);
        movieSessionSeatUpdateWrapper.setSql("sold = 1");
        movieSessionSeatMapper.update(movieSessionSeatUpdateWrapper);

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, BOOK_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> movieRefund(MovieRefundRequest movieRefundRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();

        // 是否登陆
        if (!UserPublicClass.isLogin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, USER_NOT_LOGGED_IN, errorResponseDto));
        }

        // 订单是否存在
        QueryWrapper<MovieOrder> movieOrderQueryWrapper = new QueryWrapper<>();
        movieOrderQueryWrapper.eq("order_id", movieRefundRequest.getMovieOrderId());
        if (movieOrderMapper.selectCount(movieOrderQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, ORDER_NOT_FOUND, errorResponseDto));
        }

        // 订单是不是当前用户的
        MovieOrder movieOrder = movieOrderMapper.selectById(movieRefundRequest.getMovieOrderId());
        if (UserPublicClass.isCurrentUser(movieOrder.getUserId(), httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 退票申请
        MovieOrder refundMovieOrder = new MovieOrder();
        refundMovieOrder.setOrderId(movieRefundRequest.getMovieOrderId());
        refundMovieOrder.setOrderStatus(ORDER_STATUS_REFUND_REQUEST);
        boolean refund = this.updateById(refundMovieOrder);
        if (!refund) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, REFUND_FAILED, errorResponseDto));
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, REFUND_SUCCESSFULLY, successResponseDto));
    }

    @Override
    public ResponseEntity<Object> movieRefundManage(MovieRefundManageRequest movieRefundManageRequest, HttpServletRequest httpServletRequest) {

        ErrorResponseDto errorResponseDto =  new ErrorResponseDto();

        // 验证权限
        if (UserPublicClass.isAdmin(httpServletRequest)) {
            return ResponseEntity.status(UNAUTHORIZED).body(ResponseData.responseData(UNAUTHORIZED, INSUFFICIENT_AUTHORITY, errorResponseDto));
        }

        // 订单是否存在
        QueryWrapper<MovieOrder> movieOrderQueryWrapper = new QueryWrapper<>();
        movieOrderQueryWrapper.eq("order_id", movieRefundManageRequest.getMovieOrderId());
        if (movieOrderMapper.selectCount(movieOrderQueryWrapper) <= 0) {
            return ResponseEntity.status(NOT_FOUND).body(ResponseData.responseData(NOT_FOUND, ORDER_NOT_FOUND, errorResponseDto));
        }

        // 处理
        MovieOrder movieOrder = new MovieOrder();
        movieOrder.setOrderId(movieRefundManageRequest.getMovieOrderId());
        movieOrder.setOrderStatus(movieRefundManageRequest.getAgree() ? ORDER_STATUS_REFUND_SUCCESSFUL : ORDER_STATUS_REFUND_REQUEST_FAILED);
        boolean manage = this.updateById(movieOrder);
        if (!manage) {
            return ResponseEntity.status(CONFLICT).body(ResponseData.responseData(CONFLICT, MANAGE_FAILED, errorResponseDto));
        }

        if (movieRefundManageRequest.getAgree()) {
            // 场次座位+1
            MovieOrder movieOrderInfo = movieOrderMapper.selectById(movieRefundManageRequest.getMovieOrderId());
            UpdateWrapper<MovieSession> movieSessionUpdateWrapper = new UpdateWrapper<>();
            movieSessionUpdateWrapper.eq("session_id", movieOrderInfo.getSessionId());
            movieSessionUpdateWrapper.setSql("tickets_left = tickets_left + 1");
            movieSessionMapper.update(movieSessionUpdateWrapper);

            // 座位状态修改
            String[] seatArea = movieOrderInfo.getSeat().split(",");
            Integer row = Integer.parseInt(seatArea[0]);
            Integer col = Integer.parseInt(seatArea[1]);
            UpdateWrapper<MovieSessionSeat> movieSessionSeatUpdateWrapper = new UpdateWrapper<>();
            movieSessionSeatUpdateWrapper.eq("session_id", movieOrderInfo.getSessionId());
            movieSessionSeatUpdateWrapper.eq("row_numbers", row);
            movieSessionSeatUpdateWrapper.eq("col_numbers", col);
            movieSessionSeatUpdateWrapper.setSql("sold = 0");
            movieSessionSeatMapper.update(movieSessionSeatUpdateWrapper);
        }

        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        return ResponseEntity.ok(ResponseData.responseData(OK, MANAGE_SUCCESSFULLY, successResponseDto));
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




