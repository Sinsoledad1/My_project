package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 16:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "手机号码不能为空！")
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;
    @NotBlank(message = "验证码不许为空")
    private String verifyCode;
}
