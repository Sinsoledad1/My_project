package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.HashMap;



/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/13 14:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndentGoodsDTO {
    /**
     * 配送状态(0.不配送, 1.待配送, 2.配送中)
     * 配送地址
     * 顾客电话
     * 订单备注
     * 传入商品的Map
     * 键:商品的id
     * 值：商品数量
     */
    @NotNull(message = "请您确认是否配送？")
    private Integer delivery_status;
    private String delivery_address;
    private String delivery_man_phone;
    @NotNull(message = "联系电话不能为空！")
    private String delivery_phone;
    private String remark;
    private HashMap<Long, Integer> goods;
}
