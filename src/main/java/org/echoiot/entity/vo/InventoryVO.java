package org.echoiot.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2021/1/21 13:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryVO {
    /**
        1.商品id
        2.商品名字
        3.商品库存
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String goods_name;
    private Integer goods_repertory;
}
