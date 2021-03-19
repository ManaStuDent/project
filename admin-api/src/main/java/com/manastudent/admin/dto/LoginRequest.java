package com.manastudent.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull(message = "用户名不能为空")
    private String loginName;
    @NotNull(message = "密码不能为空")
    private String password;
}
