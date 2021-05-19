package org.echoiot.dao;

import org.apache.ibatis.annotations.*;
import org.echoiot.entity.vo.InventoryVO;
import org.echoiot.entity.pojo.Goods;
import org.echoiot.entity.vo.SalesVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/10 16:06
 */
@Repository
public interface GoodsDao {

    /**
     * 添加货物
     * @param goods
     * @return
     */
    @Insert("INSERT INTO goods(id,goods_name,goods_price,goods_repertory,store_id,goods_discount,add_time) " +
            "VALUES(#{goods.id},#{goods.goods_name},#{goods.goods_price},#{goods.goods_repertory},#{goods.store_id},#{goods.goods_discount},#{goods.add_time})")
    int add (@Param("goods") Goods goods);

    /**
     * 查询所有商品
     * @return
     */
    @Select("SELECT * FROM goods WHERE store_id=#{store_id} ")
    List<Goods> findAllGoods(@Param("store_id") Long store_id);

    /**
     * 删除商品
     */
    @Delete("DELETE FROM goods WHERE id = #{id}")
    int delete(@Param("id") Long id);

    /**
     * 通过id查询商品信息
     * @param id 传进来的商品id
     * @return 返回查询到的goods
     */
    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods findgoods(@Param("id") Long id);

    /**
     * 通过名称查询商品信息
     * @param name 传进来的商品名称
     * @return 返回查询到的goods
     */
    @Select("SELECT * FROM goods WHERE goods_name = #{name}")
    Goods findgoodsbyname(@Param("name")String name);
    /**
     * 修改商品信息
     *
     */
    @Update("UPDATE goods SET  goods_name = #{goods.goods_name},goods_price = #{goods.goods_price},goods_repertory = #{goods.goods_repertory},goods_discount = #{goods.goods_discount}" +
            " WHERE id = #{goods.id}")
    int update(@Param("goods") Goods goods);

    /**
     * 修改商品库存
     *
     */
    @Update("UPDATE goods SET  goods_repertory = #{goods.goods_repertory}" +
            " WHERE id = #{goods.id}")
    int updateRepertory(@Param("goods") Goods goods);

    /**
     * 查询商品库存
     * @return
     */
    @Select("SELECT id,goods_name,goods_repertory FROM goods WHERE store_id=#{store_id} ")
    List<InventoryVO> Inventory(@Param("store_id") Long store_id);

    /**
     * 查询商品销量
     * @return
     */
    @Select("SELECT goods_name,sum(number) as SalesNum FROM indent_goods WHERE store_id=#{store_id} group by goods_name ")
    List<SalesVO> Sales (@Param("store_id") Long store_id);
}
