package com.suny.dao;

import com.suny.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by 孙建荣 on 17-9-1.上午10:43
 */
@Mapper
public interface LoginTicketDAO {

    String TABLE_NAME = "login_ticket.sql";
    String INSERT_FIELDS = "user_id, expired, status, ticket";
    String SELECT_FIELDS = "id " + INSERT_FIELDS;


    @Insert({"insert into " + TABLE_NAME + "(" + INSERT_FIELDS + ") values(#{userId},#{expired},#{status},#{ticket}"})
    int addTicket(LoginTicket loginTicket);


    @Select({"select ", SELECT_FIELDS + "from " + TABLE_NAME + "where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);


    @Update({"update", TABLE_NAME, "set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}


















