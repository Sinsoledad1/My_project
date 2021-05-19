package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/11/30 22:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePwDTO {
    @NotBlank(message = "手机号码不能为空！")
    private String username;
    @NotBlank(message = "旧密码不能为空！")
    private String password;
    @NotBlank(message = "新密码密码不能为空！")
    private String NewPassword;
    @NotBlank(message = "确认密码不能为空！")
    private String ConfirmPassword;
}
