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
 * @date 2020/11/28 12:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    /**
     * id
     * 商品名称
     * 商品价格
     * 商品库存
     * 店铺id
     * 商品折扣
     * 添加时间
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String goods_name;
    private BigDecimal goods_price;
    private Integer goods_repertory;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long store_id;
    private BigDecimal goods_discount;
    private Long add_time;
}
