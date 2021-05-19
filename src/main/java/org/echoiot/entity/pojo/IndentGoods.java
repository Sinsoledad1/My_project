package org.echoiot.entity.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/28 13:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndentGoods {
    /**
     * id
     * 订单id
     * 商店id
     * 商品id
     * 商品名称
     * 退货状态(0.未退货, 1.申请退货, 2.已退货)
     * 商品价格
     * 商品数量
     * 总价格
     * 折扣
     * 添加时间
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long indent_id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long store_id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long goods_id;
    private String goods_name;
    private Integer return_status;
    private BigDecimal goods_price;
    private Integer number;
    private BigDecimal total_price;
    private BigDecimal goods_discount;
    private Long add_time;
}
