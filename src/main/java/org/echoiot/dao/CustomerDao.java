package org.echoiot.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.echoiot.entity.pojo.Customer;
import org.springframework.stereotype.Repository;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/13 18:02
 */
@Repository
public interface CustomerDao {
    /**
     * 添加顾客
     * @param customer
     * @return
     */
    @Insert("INSERT INTO customer(id,store_id,indent_id,phone,add_time) " +
            "VALUES(#{customer.id},#{customer.store_id},#{customer.indent_id},#{customer.phone},#{customer.add_time})")
    int add (@Param("customer")Customer customer);
}
