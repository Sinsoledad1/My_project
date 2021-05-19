package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/15 14:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnsDTO {
    /**
     * 订单id
     * 退货状态(1.全部退货 2.部分退货)
     * 退货商品
     */
    @NotNull(message = "订单id不能为空")
    private Long id;
    @NotNull(message = "请您确认是部分退货还是全部退货")
    private Integer return_status;
    private LinkedList<Long> return_goods;
}
