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
 * @date 2020/11/30 9:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesLog {
    /**
     * 销售日志id
     * 店铺id
     * 销售额
     * 销售量
     * 顾客人数
     * 更新时间
     * 添加时间
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long store_id;
    private BigDecimal Sales;
    private Integer Sales_number;
    private Integer customer_number;
    private Long update_time;
    private Long add_time;


}
