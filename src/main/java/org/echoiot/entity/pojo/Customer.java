package org.echoiot.entity.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 9:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    /**
     * 顾客id
     * 消费的店铺id
     * 订单id
     * 联系电话
     * 添加时间
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long store_id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long indent_id;
    private String phone;
    private Long add_time;
}
