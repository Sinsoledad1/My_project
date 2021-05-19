package org.echoiot.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 9:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationCode {
    /**
     * id
     * 用户id
     * 电话号码
     * 短信验证码
     * 失效时间
     * 是否有效(有效1，无效2)
     * 是否送达(是1，否2)
     * 添加时间
     * ip
     */

    private Long id;
    private Long uid;
    private String phone;
    private String code;
    private Long expire_time;
    private Integer is_used;
    private Integer delivery;
    private Long add_time;
    private String ip;
}
