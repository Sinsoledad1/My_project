package org.echoiot.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 9:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryChangeLog {
    /**
     * id
     * 日志类型(入库:1;出库:2)
     * 店铺id
     * 订单id
     * 商品id
     * 发生数量
     * 货物价值
     * 经手人id
     * 添加时间
     * ip
     */

    private Long id;
    private  Integer log_type;
    private Long store_id;
    private Long indent_id;
    private Long goods_id;
    private Integer number;
    private BigDecimal total_price;
    private Long agent_id;
    private Long add_time;
    private String ip;


}
