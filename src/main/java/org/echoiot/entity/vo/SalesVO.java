package org.echoiot.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2021/1/21 15:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesVO {
    /**
     * 商品的名字
     * 商品的销量
     */
    private String goods_name;
    private Integer SalesNum;
}
