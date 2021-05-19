package org.echoiot.entity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/7 14:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteCode {
    /**
     * 邀请码id
     * 店铺id
     * 发送者id
     * 邀请码内容
     * 添加时间
     */
    private Long id;
    private Long store_id;
    private Long postman_id;
    private String code;
    private Long add_time;
}
