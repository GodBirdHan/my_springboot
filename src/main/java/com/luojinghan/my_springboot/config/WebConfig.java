package com.luojinghan.my_springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 全局配置。
 *
 * 核心：解决前后端分离的跨域问题。
 * Vue 跑在 localhost:5173，Spring Boot 跑在 localhost:8080，
 * 浏览器默认不允许一个端口去请求另一个端口的数据 —— 这叫"同源策略"。
 * 这里给 5173 开了 CORS 通行证，允许它访问 /api/** 下的所有接口。
 */
@Configuration     // 告诉 Spring：这是一个配置类，启动时加载
public class WebConfig {

    @Bean           // 把返回值注册为一个 Spring Bean，全局生效
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")                    // 允许跨域的路径
                        .allowedOrigins("http://localhost:5173")  // 只允许 Vue 前端这个地址
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);                  // 允许携带 cookie
            }
        };
    }
}
