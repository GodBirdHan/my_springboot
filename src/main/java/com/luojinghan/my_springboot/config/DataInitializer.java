package com.luojinghan.my_springboot.config;

import com.luojinghan.my_springboot.entity.User;
import com.luojinghan.my_springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器 —— 项目启动后自动执行。
 *
 * 首次启动时检查：如果 users 表是空的，就插入一条测试用户。
 * MySQL 数据持久化，即使重启也不会丢失，所以后续启动直接跳过。
 * 这样首次启动后就能用 admin / 123456 登录。
 *
 * CommandLineRunner：Spring Boot 启动完成后自动调用 run() 方法。
 */
@Component                      // Spring 组件注解
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        // 只有表为空时才插入，避免重复创建
        if (userRepository.count() == 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User admin = User.builder()
                    .username("admin")
                    .password(encoder.encode("123456"))     // 密码加密存储，不存明文
                    .nickname("GodBirdHan")
                    .build();

            userRepository.save(admin);                     // INSERT INTO users ...
            System.out.println("✅ Test user created: admin / 123456");
        }
    }
}
