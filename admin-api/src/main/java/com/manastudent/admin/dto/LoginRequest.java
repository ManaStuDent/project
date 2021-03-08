package com.manastudent.admin.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginName;
    private String password;
}
