package org.echoiot.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.echoiot.entity.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 9:55
 */
@Repository
public interface UserDao {
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Insert("INSERT INTO users(id,username,password,role,store_id,real_name,last_login,add_time) " +
            "VALUES(#{user.id},#{user.username},#{user.password},#{user.role},#{user.store_id},#{user.real_name},#{user.last_login},#{user.add_time})")
    int register (@Param("user") User user);

    /**
     * 登录时通过用户名获取用户全部信息
     * @param loginName 用户登录时获得的用户名
     * @return 返回查询到的user
     */
    @Select("SELECT * FROM users WHERE username = #{loginName}")
    User userLogin(@Param("loginName") String loginName);

    /**
     * 更新最后登录时间
     * @param user User的实例化对象
     */
    @Update("UPDATE users SET last_login = #{user.last_login} WHERE id = #{user.id}")
    void updateLastLogin(@Param("user")User user);

    /**
     * 用户自己更改密码
     * @param user user
     * @return 返回影响的行数
     */
    @Update("UPDATE users SET password = #{user.password} WHERE username = #{user.username}")
    int updatePassword(@Param("user")User user);


    /**
     * 更新店铺id
     * @param user User的实例化对象
     */
    @Update("UPDATE users SET store_id = #{user.store_id} WHERE id = #{user.id}")
    int updateStoreId(@Param("user")User user);

    /**
     * 更新角色
     * @param user User的实例化对象
     */
    @Update("UPDATE users SET role = #{user.role} WHERE id = #{user.id}")
    int updateRole(@Param("user")User user);

    /**
     * 更新角色和所在店铺
     * @param user User的实例化对象
     */
    @Update("UPDATE users SET role = #{user.role},store_id = #{user.store_id} WHERE id = #{user.id}")
    int updateRS(@Param("user")User user);

    /**
     * 通过id查询用户信息
     * @param id 传进来的用户id
     * @return 返回查询到的user
     */
    @Select("SELECT * FROM users WHERE id = #{id}")
    User finduser(@Param("id") Long id);

}
