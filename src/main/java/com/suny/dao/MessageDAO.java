package com.suny.dao;

import com.suny.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static com.suny.dao.MessageDAO.INSERT_FIELDS;
import static com.suny.dao.MessageDAO.SELECT_FIELDS;
import static com.suny.dao.MessageDAO.TABLE_NAME;

/**
 * Created by 孙建荣 on 17-9-4.下午5:21
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, create_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String HAS_READER_TRUE = "1";

    @Update({"update ", TABLE_NAME, " set has_read=", HAS_READER_TRUE, " where conversation_id=#{conversationId}"})
    void updateMessagesReadStatus(String conversationId);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);


    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId} "})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);


    // group by conversation_id

    @Select({"select ", INSERT_FIELDS, " , count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} or to_id=#{userId} order by create_date desc) tt  GROUP BY conversation_id  order by create_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

}
