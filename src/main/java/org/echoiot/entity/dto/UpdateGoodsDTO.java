package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/10 15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGoodsDTO {

    /**
     * 商品id
     * 商品名称
     * 商品价格
     * 商品库存
     * 商品折扣
     */
    @NotNull(message = "商品id不能为空！")
    private Long id;
    @NotBlank(message = "商品名不能为空！")
    private String goods_name;
    @NotNull(message = "商品价格不能为空！")
    private BigDecimal goods_price;
    @NotNull(message = "商品库存不能为空！")
    private Integer goods_repertory;

    private BigDecimal goods_discount;
}
