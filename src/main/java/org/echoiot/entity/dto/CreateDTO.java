package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/1 14:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDTO {

    @NotBlank(message = "店铺名不能为空！")
    private String store_name;
    @NotBlank(message = "店铺联系方式不能为空！")
    private String store_phone;
    @NotBlank(message = "店铺位置不能为空！")
    private String store_location;
//    @NotBlank(message = "验证码不许为空")
    private String verifyCode;
}
