create database db_yueying;

create table tb_user
(
    user_id       bigint auto_increment comment 'id' primary key,
    user_account  varchar(256) unique                                                                                                    null comment '账号',
    user_name     varchar(256)                                                                                                           not null comment '用户名',
    user_password varchar(256)                                                                                                           not null comment '密码',
    user_role     tinyint       default 0                                                                                                not null comment '用户角色 0 - 普通用户 1 - 客服 2 - 影院管理员 3 - 活动管理员 4 - 系统管理员 5 - 超级管理员',
    avatar_url    varchar(1024) default 'https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202305292055355.png' null comment '头像',
    gender        tinyint       default 0                                                                                                null comment '性别 0 - 男 1 - 女',
    phone         varchar(256)                                                                                                           null comment '电话',
    email         varchar(256)                                                                                                           null comment '邮箱',
    user_status   int           default 0                                                                                                not null comment '状态 0 - 正常',
    create_time   datetime      default CURRENT_TIMESTAMP                                                                                null comment '创建时间',
    update_time   datetime      default CURRENT_TIMESTAMP                                                                                null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       tinyint       default 0                                                                                                not null comment '是否删除 0 - 未删除 1 - 已删除'
) comment '用户表';


create table tb_cinema
(
    cinema_id      bigint auto_increment comment 'id' primary key,
    cinema_name    varchar(256)                       not null comment '影院名',
    cinema_address varchar(256)                       not null comment '影院地址',
    cinema_profile text                               null comment '影院简介',
    cinema_service text                               null comment '影院服务',
    cinema_phone   varchar(256) comment '影院电话',
    cinema_traffic varchar(256) comment '影院交通',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除'
) comment '影院';


create table tb_movie_hall
(
    movie_hall_id      bigint auto_increment comment 'id' primary key,
    cinema_id          bigint                                                                                                                 not null comment '影院ID',
    movie_hall_name    varchar(256)                                                                                                           not null comment '影厅名',
    movie_hall_type_id bigint                                                                                                                 not null comment '影厅类型ID',
    movie_hall_photo   varchar(1024) default 'https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202403061441515.png' not null comment '影厅照片',
    movie_hall_profile text                                                                                                                   null comment '影厅简介',
    seating            bigint                                                                                                                 not null comment '座位数',
    create_time        datetime      default CURRENT_TIMESTAMP                                                                                null comment '创建时间',
    update_time        datetime      default CURRENT_TIMESTAMP                                                                                null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted            tinyint       default 0                                                                                                not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_movie_hall_cinema foreign key (cinema_id) references tb_cinema (cinema_id)
) comment '影厅';


create table tb_movie_hall_type
(
    type_id     bigint auto_increment comment 'id' primary key,
    type_name   varchar(256)                       not null comment '类型名',
    row_numbers int                                not null comment '座位排数',
    col_numbers int                                not null comment '座位列数',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除'
) comment '影厅类型';


create table tb_movie_seat
(
    movie_seat_id bigint auto_increment comment 'id' primary key,
    movie_hall_id bigint comment 'movie_hall_id',
    row_numbers   int      default 0                 not null comment '排',
    col_numbers   int      default 0                 not null comment '列',
    seat_type     tinyint  default 0                 not null comment '座位类型 0 - 普通座位 1 - 未安装座位 2 - 故障',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_movie_seat_movie_session foreign key (movie_hall_id) references tb_movie_hall (movie_hall_id)
) comment '影厅座位（每个座位一条数据）';


create table tb_movie_type
(
    movie_type_id int auto_increment comment 'id' primary key,
    movie_type    varchar(256)                       not null,
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除'
) comment '影片类型';


create table tb_movie
(
    movie_id          bigint auto_increment comment 'id' primary key,
    movie_name        varchar(256)                                                                                                           not null comment '影片名',
    movie_type_id     int                                                                                                                    not null comment '影片类型id',
    movie_cover_small varchar(1024) default 'https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202403061441515.png' not null comment '影片封面（小）',
    movie_cover_large varchar(1024) default 'https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202403061441515.png' not null comment '影片封面（大）',
    release_date      datetime                                                                                                               not null comment '上映时间',
    movie_duration    time                                                                                                                   not null comment '影片时长',
    main_actor        varchar(1024)                                                                                                          null comment '主要演员',
    movie_profile     text                                                                                                                   null comment '影片简介',
    create_time       datetime      default CURRENT_TIMESTAMP                                                                                null comment '创建时间',
    update_time       datetime      default CURRENT_TIMESTAMP                                                                                null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted           tinyint       default 0                                                                                                not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_movie_movie_type foreign key (movie_type_id) references tb_movie_type (movie_type_id)
) comment '影片';


create table tb_movie_session
(
    session_id    bigint auto_increment comment 'id' primary key,
    movie_id      bigint                             not null comment '影片ID',
    cinema_id     bigint                             not null comment '影院ID',
    hall_id       bigint                             not null comment '影厅ID',
    movie_runtime datetime                           not null comment '放映时间',
    price         decimal  default 0                 not null comment '票价',
    tickets_left  bigint   default 0                 not null comment '余票',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_movie_session_movie foreign key (movie_id) references tb_movie (movie_id),
    constraint fk_movie_session_cinema foreign key (cinema_id) references tb_cinema (cinema_id),
    constraint fk_movie_session_hall foreign key (hall_id) references tb_movie_hall (movie_hall_id)
) comment '影片场次';


create table tb_session_seat
(
    session_id  bigint                             not null comment '场次id',
    row_numbers int      default 0                 not null comment '排',
    col_numbers int      default 0                 not null comment '列',
    sold        tinyint  default 0                 not null comment '是否已售 0 - 否 1 - 是',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除'
);


create table tb_event_place
(
    place_id      bigint auto_increment comment 'id' primary key,
    place_name    varchar(256)                       not null comment '场地名',
    place_type    varchar(256)                       not null comment '场地类型',
    place_address varchar(256)                       not null comment '场地地址',
    max_seats     bigint   default 0                 not null comment '最大容纳人数',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除'
) comment '活动场地';


create table tb_event
(
    event_id          bigint auto_increment comment 'id' primary key,
    event_name        varchar(256)                                                                                                           not null comment '活动名',
    event_place_id    bigint                                                                                                                 not null comment '活动场地ID',
    event_type        varchar(256)                                                                                                           not null comment '活动类型',
    main_actor        text                                                                                                                   null comment '主要演员',
    event_cover_small varchar(1024) default 'https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202403061441515.png' not null comment '活动封面（小）',
    event_cover_large varchar(1024) default 'https://mengnali-dou-1307976958.cos.ap-beijing.myqcloud.com/ForTyporaImage/202403061441515.png' not null comment '活动封面（大）',
    begin_time        datetime                                                                                                               not null comment '开始时间',
    finish_time       datetime                                                                                                               not null comment '结束时间',
    finished          tinyint       default 0 comment '是否结束 0 - 未结束 1 - 已结束',
    event_profile     text                                                                                                                   null comment '活动简介',
    create_time       datetime      default CURRENT_TIMESTAMP                                                                                null comment '创建时间',
    update_time       datetime      default CURRENT_TIMESTAMP                                                                                null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted           tinyint       default 0                                                                                                not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_event_place foreign key (event_place_id) references tb_event_place (place_id),
    constraint tb_event_chk_1 check ( tb_event.finish_time >= tb_event.begin_time )
) comment '活动';


create table tb_event_price
(
    price_id     bigint auto_increment comment 'id' primary key,
    event_id     bigint                             not null comment '活动ID',
    seat_type    varchar(256)                       null comment '座位类型',
    seat_rows    int      default 0                 not null comment '排数',
    seat_cols    int      default 0                 not null comment '列数',
    price        decimal  default 0                 not null comment '票价',
    tickets_left bigint   default 0                 not null comment '余票',
    total_num    bigint   default 0                 not null comment '总票数',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_event_price_event foreign key (event_id) references tb_event (event_id)
) comment '活动票价';


create table tb_event_order
(
    order_id     bigint auto_increment comment 'id' primary key,
    user_id      bigint                             not null comment '用户ID',
    event_id     bigint                             not null comment '活动ID',
    seat         varchar(256)                       not null comment '座位',
    begin_time   datetime                           not null comment '开始时间',
    contact      varchar(256)                       not null comment '联系人',
    spectator    varchar(256)                       not null comment '观演人',
    order_price  decimal  default 0                 not null comment '订单价格',
    order_status tinyint  default 0                 not null comment '订单状态 0 - 未支付 1 - 支付成功 2 - 待检票 3 - 检票完成 4 - 活动结束 5 - 订单结束 6 - 退票申请 7 - 退票成功',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_order_user foreign key (user_id) references tb_user (user_id),
    constraint fk_order_event foreign key (event_id) references tb_event (event_id)
) comment '活动订单';


create table tb_movie_order
(
    order_id     bigint auto_increment comment 'id' primary key,
    user_id      bigint                             not null comment '用户ID',
    session_id   bigint                             not null comment '场次',
    seat         varchar(256)                       not null comment '座位',
    begin_time   datetime                           not null comment '开始时间',
    contact      varchar(256)                       not null comment '联系人',
    spectator    varchar(256)                       not null comment '观演人',
    order_price  decimal  default 0                 not null comment '订单价格',
    order_status tinyint  default 0                 not null comment '订单状态 0 - 未支付 1 - 支付成功 2 - 待取票 3 - 取票完成 4 - 待检票 5 - 检票完成 6 - 订单结束 7 - 退票申请 8 - 退票成功',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    constraint fk_movie_order_user foreign key (user_id) references tb_user (user_id),
    constraint fk_movie_order_session foreign key (session_id) references tb_movie_session (session_id)
) comment '订单';
