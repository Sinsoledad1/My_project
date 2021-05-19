package org.echoiot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.echoiot.entity.bean.InviteCode;
import org.echoiot.entity.pojo.Store;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/1 15:35
 */
@Repository
public interface StoreDao {

    /**
     * 创建店铺
     * @param store
     * @return
     */
    @Insert("INSERT INTO store(id,store_name,creator_id,boss_id,store_phone,store_location,add_time) " +
            "VALUES(#{store.id},#{store.store_name},#{store.creator_id},#{store.boss_id},#{store.store_phone},#{store.store_location},#{store.add_time})")
    int create (@Param("store") Store store);


    /**
     * 通过邀请码，查找所有信息
     * @param code 用户登录时获得的用户名
     * @return 返回查询到的user
     */
    @Select("SELECT * FROM invitation_code WHERE code = #{code}")
    InviteCode findcode(@Param("code") String code);

    /**
     * 通过id查找店铺
     *
     * @param id
     *
     * @return ApplicationForm
     **/
    @Select("SELECT * FROM store WHERE id = #{id}")
    Store FindStorebyID(@Param("id") Long id);


    /**
     * 通过id查找店铺
     *
     * @param uid
     *
     * @return ApplicationForm
     **/
    @Select("SELECT * FROM store WHERE creator_id = #{uid}")
    List<Store> FindStorebyUID(@Param("uid") Long uid);

    /**
     * 删除店铺
     */
    @Delete("DELETE FROM store WHERE id = #{id}")
    int delete(@Param("id") Long id);
}