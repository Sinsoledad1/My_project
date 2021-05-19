package org.echoiot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.echoiot.entity.bean.InviteCode;
import org.echoiot.entity.vo.UserShowVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/7 16:22
 */
@Repository
public interface AdminDao {

    /**
     * 添加邀请码
     * @param code
     * @return
     */
    @Insert("INSERT INTO invitation_code(id,store_id,postman_id,code,add_time) " +
            "VALUES(#{code.id},#{code.store_id},#{code.postman_id},#{code.code},#{code.add_time})")
    int invite (@Param("code") InviteCode code);

    /**
     * 将要返回的user信息封装到UserAllDTO,写入链表并返回
     * @return 返回UserAllDTO的链表
     */
    @Select("SELECT id,username,role,store_id,real_name,add_time FROM users WHERE store_id=#{store_id} ")
    List<UserShowVO> findAllUser(@Param("store_id") Long store_id);
    /**
     * 移除店员
     */
    @Delete("DELETE FROM users WHERE id = #{id}")
    Integer delete(@Param("id") Long id);
}
