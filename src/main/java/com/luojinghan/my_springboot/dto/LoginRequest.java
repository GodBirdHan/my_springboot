package com.luojinghan.my_springboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录请求体 —— 前端发来的 JSON 会反序列化成这个对象。
 *
 * 前端发：
 *   { "username": "admin", "password": "123456" }
 *         │
 *         ▼  反序列化
 *   LoginRequest(username="admin", password="123456")
 */
@Data
public class LoginRequest {

    @NotBlank(message = "用户名不能为空")     // Bean Validation：空字符串直接拒绝，不进业务层
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
