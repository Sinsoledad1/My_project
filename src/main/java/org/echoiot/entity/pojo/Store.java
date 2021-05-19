package org.echoiot.entity.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/28 13:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    /**
     * 店铺id
     * 店铺名称
     * 创建者id
     * 店长id
     * 店铺联系电话
     * 位置
     * 添加时间
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String store_name;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creator_id;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long boss_id;
    private String store_phone;
    private String store_location;
    private Long add_time;

}
