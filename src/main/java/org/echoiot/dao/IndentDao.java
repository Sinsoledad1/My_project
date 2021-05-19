package org.echoiot.dao;

import org.apache.ibatis.annotations.*;
import org.echoiot.entity.pojo.Indent;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/13 23:47
 */
@Repository
public interface IndentDao {

    /**
     * 添加订单
     * @param indent
     * @return
     */
    @Insert("INSERT INTO indent(id,store_id,customer_id,salesman_id,pay_status,return_status,total_price,discount_price,remark,add_time,pay_time,return_time,delivery_time,delivery_status,delivery_man_id,delivery_address,delivery_phone,return_salesman_id,store_name) " +
            "VALUES(#{indent.id},#{indent.store_id},#{indent.customer_id},#{indent.salesman_id},#{indent.pay_status},#{indent.return_status},#{indent.total_price},#{indent.discount_price},#{indent.remark},#{indent.add_time},#{indent.pay_time},#{indent.return_time},#{indent.delivery_time},#{indent.delivery_status},#{indent.delivery_man_id},#{indent.delivery_address},#{indent.delivery_phone},#{indent.return_salesman_id},#{indent.store_name})")
    int add (@Param("indent")Indent indent);

    /**
     * 通过id查找订单
     * @param id
     * @return
     */
    @Select("SELECT * FROM indent WHERE id = #{id}")
    Indent find(@Param("id") Long id);


    /**
     * 支付订单
     * @param indent
     * @return
     */
    @Update("UPDATE indent SET pay_status = #{indent.pay_status},delivery_time = #{indent.delivery_time},pay_time = #{indent.pay_time}, delivery_status = #{indent.delivery_status} WHERE id = #{indent.id}")
    int pay(@Param("indent")Indent indent);

    /**
     * 修改订单退款状态
     * @param indent
     * @return
     */
    @Update("UPDATE indent SET return_status = #{indent.return_status},return_time = #{indent.return_time},return_salesman_id = #{indent.return_salesman_id} WHERE id = #{indent.id}")
    int returnAll(@Param("indent")Indent indent);

    /**
     * 删除订单
     */
    @Delete("DELETE FROM indent WHERE id = #{id}")
    int delete(@Param("id") Long id);

    /**
     * 查询所有订单
     * @return
     */
    @Select("SELECT * FROM indent WHERE store_id=#{store_id} ")
    List<Indent> findAllIndent(@Param("store_id") Long store_id);

    /**
     * 查询所有未支付订单
     * @return
     */
    @Select("SELECT * FROM indent WHERE store_id=#{store_id} AND pay_status = '0'")
    List<Indent> notPay(@Param("store_id") Long store_id);

    /**
     * 查询所有已支付订单
     * @return
     */
    @Select("SELECT * FROM indent WHERE store_id=#{store_id} AND pay_status = '1'")
    List<Indent> Payed(@Param("store_id") Long store_id);

    /**
     * 查询所有已支付订单
     * @return
     */
    @Select("SELECT * FROM indent WHERE store_id=#{store_id} AND salesman_id = #{salesman_id}")
    List<Indent> MyIndent(@Param("salesman_id") Long salesman_id,@Param("store_id") Long store_id);

}
