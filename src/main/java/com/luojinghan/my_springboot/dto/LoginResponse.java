package com.luojinghan.my_springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应体 —— 后端处理完后序列化成 JSON 返回给前端。
 *
 * 成功时：
 *   { "success": true, "message": "登录成功", "token": "uuid...", "nickname": "GodBirdHan" }
 * 失败时：
 *   { "success": false, "message": "用户不存在", "token": null, "nickname": null }
 */
@Data
@Builder                        // Builder 模式，方便链式构建响应对象
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private boolean success;    // 登录是否成功
    private String message;     // 提示信息（成功或失败原因）
    private String token;       // 登录凭证（目前用 UUID，后续可换成 JWT）
    private String nickname;    // 用户昵称，前端用来展示"欢迎回来"
}
