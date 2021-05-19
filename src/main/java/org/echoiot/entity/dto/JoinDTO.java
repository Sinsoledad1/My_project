package org.echoiot.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author BeiChen
 * @version 1.0
 * @date 2020/12/9 11:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDTO {
    @NotBlank(message = "邀请码不许为空！")
    private String Code;
}
