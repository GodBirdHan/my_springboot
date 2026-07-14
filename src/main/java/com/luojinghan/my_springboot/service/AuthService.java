package com.luojinghan.my_springboot.service;

import com.luojinghan.my_springboot.dto.LoginRequest;
import com.luojinghan.my_springboot.dto.LoginResponse;
import com.luojinghan.my_springboot.entity.User;
import com.luojinghan.my_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 认证服务 —— 处理登录逻辑。
 *
 * 流程：
 *   1. 根据用户名去数据库查用户
 *   2. 用户存在 → BCrypt 比对密码
 *   3. 密码正确 → 生成 token 返回
 *   4. 任何一步失败 → 返回 error 信息
 */
@Service                         // Spring 组件注解：把这个类交给 Spring 容器管理
@RequiredArgsConstructor         // Lombok：为 final 字段自动生成构造函数（相当于构造器注入）
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 用户登录。
     *
     * @param request 前端传来的 { username, password }
     * @return LoginResponse —— success=true 表示登录成功，附 token 和 nickname
     */
    public LoginResponse login(LoginRequest request) {

        // ① 根据用户名查数据库
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);      // Optional：如果没找到返回 null

        // ② 用户不存在 → 直接拒绝
        if (user == null) {
            return LoginResponse.builder()
                    .success(false)
                    .message("用户不存在")
                    .build();
        }

        // ③ 用户存在 → BCrypt 比对密码
        //    BCrypt 是单向加密，无法解密。输入同样的密码会产出同样的密文。
        //    passwordEncoder.matches(原始密码, 数据库里的密文) → true/false
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return LoginResponse.builder()
                    .success(false)
                    .message("密码错误")
                    .build();
        }

        // ④ 密码正确 → 生成 token（当前用 UUID，后续可换成 JWT）
        String token = UUID.randomUUID().toString();

        return LoginResponse.builder()
                .success(true)
                .message("登录成功")
                .token(token)
                .nickname(user.getNickname())
                .build();
    }
}
