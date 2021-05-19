package org.echoiot.entity.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/28 14:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 用户自身id
     * 手机号
     * 密码
     * 身份(member admin)
     * 所在店铺id
     * 真实姓名
     * 添加时间
     *
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;
    private String username;
    private String password;
    private String role;
    @JsonSerialize(using= ToStringSerializer.class)
    private Long store_id;
    private String real_name;
    private Long last_login;
    private Long add_time;

}
