package com.key.oa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


/**
 * @author 孙强
 * 用于传递登录信息，包括工号和密码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotEmpty
    private String jobNumber;

    @NotEmpty
    private String password;
}
