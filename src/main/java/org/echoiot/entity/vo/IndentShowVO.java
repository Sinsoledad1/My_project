package org.echoiot.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.echoiot.entity.pojo.IndentGoods;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/19 18:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndentShowVO {
    /**
     * 订单id
     * 店铺id
     * 店铺名字
     * 顾客id
     * 销售者id
     * 支付状态(0.待支付, 1.已支付, 2.取消支付)
     * 退货状态(0.未退货, 1.完全退货, 2.部分退货)
     * 总价
     * 折后价
     * 备注
     * 添加时间
     * 支付时间
     * 退货时间
     * 配送时间
     * 配送状态(0.不配送, 1.待配送, 2.配送中, 3.已配送)
     * 配送人员id
     * 配送地址
     * 买家电话
     * 退货员工id
     * 订单货物链表
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long store_id;
    private String store_name;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long customer_id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long salesman_id;
    private Integer pay_status;
    private Integer return_status;
    private BigDecimal total_price;
    private BigDecimal discount_price;
    private String remark;
    private Long add_time;
    private Long pay_time;
    private Long return_time;
    private Long delivery_time;
    private Integer delivery_status;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long delivery_man_id;
    private String delivery_address;
    private String delivery_phone;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long return_salesman_id;
    private List<IndentGoods> Goods;
}
