package org.echoiot.dao;

import org.apache.ibatis.annotations.*;
import org.echoiot.entity.pojo.Indent;
import org.echoiot.entity.pojo.IndentGoods;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/13 18:18
 */
@Repository
public interface IndentGoodsDao {

    /**
     * 添加订单货物
     * @param goods
     * @return
     */
    @Insert("INSERT INTO indent_goods(id,indent_id,goods_name,return_status,goods_price,goods_discount,add_time,number,total_price,store_id,goods_id) " +
            "VALUES(#{goods.id},#{goods.indent_id},#{goods.goods_name},#{goods.return_status},#{goods.goods_price},#{goods.goods_discount},#{goods.add_time},#{goods.number},#{goods.total_price},#{goods.store_id},#{goods.goods_id})")
    int add (@Param("goods") IndentGoods goods);

    /**
     * 修改该订单货物为全部退货
     * @param id
     * @return
     */
    @Update("UPDATE indent_goods SET return_status = '1' WHERE indent_id = #{id} ")
    int returnAll(@Param("id") Long id);

    /**
     * 修改该订单货物为部分退货
     * @param id
     * @return
     */
    @Update("UPDATE indent_goods SET return_status = '1' WHERE indent_id = #{id} AND goods_name=#{name}")
    int returnSome(@Param("id") Long id,@Param("name") String name);


    /**
     * 通过商品名称查找商品数量
     * @param id
     * @param name
     * @return
     */
    @Select("SELECT number FROM indent_goods WHERE indent_id = #{id} AND goods_name = #{name}")
    Integer findNumber(@Param("id") Long id,@Param("name") String name);

    /**
     * 通过订单id查找所有商品名称
     * @param id
     * @return
     */
    @Select("SELECT goods_name FROM indent_goods WHERE indent_id = #{id}")
    LinkedList<String> findName(@Param("id") Long id);

    /**
     * 删除订单货物
     */
    @Delete("DELETE FROM indent_goods WHERE indent_id = #{id}")
    int delete(@Param("id") Long id);

    /**
     * 查询当前订单下的所有货物
     * @return
     */
    @Select("SELECT * FROM indent_goods WHERE indent_id=#{indent_id} ")
    List<IndentGoods> findAll(@Param("indent_id") Long indent_id);
}
