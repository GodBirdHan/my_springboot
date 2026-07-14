package com.luojinghan.my_springboot.controller;

import com.luojinghan.my_springboot.dto.LoginRequest;
import com.luojinghan.my_springboot.dto.LoginResponse;
import com.luojinghan.my_springboot.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口层 —— 暴露 HTTP 端点给前端调用。
 *
 * 所有接口以 /api/auth 开头。
 */
@RestController                     // = @Controller + @ResponseBody（返回值直接写入 HTTP 响应体）
@RequestMapping("/api/auth")        // 所有方法路径以 /api/auth 开头
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 登录接口。
     *
     * POST /api/auth/login
     * 请求体：{ "username": "admin", "password": "123456" }
     * 成功返回 200 + { success: true, token, nickname }
     * 失败返回 401 + { success: false, message: "密码错误" }
     *
     * @param request 前端传来的登录表单（@Valid 自动校验字段不为空）
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        if (response.isSuccess()) {
            // 登录成功 → HTTP 200
            return ResponseEntity.ok(response);
        } else {
            // 登录失败 → HTTP 401（Unauthorized）
            return ResponseEntity.status(401).body(response);
        }
    }
}
