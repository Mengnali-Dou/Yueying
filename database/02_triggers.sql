create trigger movie_hall_seating_trigger
    before insert
    on tb_movie_hall
    for each row
begin
    declare seats bigint;
    select row_numbers * col_numbers
    into seats
    from tb_movie_hall_type
    where NEW.movie_hall_type_id = tb_movie_hall_type.type_id;
    begin
        set NEW.seating = seats;
    end;
end;


create trigger tr_movie_session
    before insert
    on tb_movie_session
    for each row
begin
    declare seats bigint;
    select tb_movie_hall.seating into seats from tb_movie_hall where tb_movie_hall.movie_hall_id = NEW.hall_id;
    begin
        set NEW.tickets_left = seats;
    end;
end;


create trigger tr_movie_seat_row_col
    before insert
    on tb_movie_seat
    for each row
begin
    declare row_num, col_num int;
    select row_numbers, col_numbers
    into row_num, col_num
    from tb_movie_hall_type
    where type_id = (select movie_hall_type_id
                     from tb_movie_hall
                     where movie_hall_id = (select hall_id from tb_movie_session where session_id = NEW.session_id));
    if NEW.row_numbers > row_num && NEW.col_numbers > col_num then
        signal sqlstate '45000'
            set message_text = '行列超过最大限制';
    end if;
end;


create trigger tr_event_order
    before insert
    on tb_event_order
    for each row
begin
    declare begin_time datetime;
    select tb_event.begin_time into begin_time from tb_event where NEW.event_id = tb_event.event_id;
    begin
        set NEW.begin_time = begin_time;
    end;
end;


create trigger tr_movie_order
    before insert
    on tb_movie_order
    for each row
begin
    declare begin_time datetime;
    select tb_movie_session.movie_runtime
    into begin_time
    from tb_movie_session
    where tb_movie_session.session_id = NEW.session_id;
    begin
        set NEW.begin_time = begin_time;
    end;
end;