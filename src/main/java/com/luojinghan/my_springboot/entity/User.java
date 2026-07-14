package com.luojinghan.my_springboot.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 用户实体 —— 映射到数据库的 users 表。
 * Lombok 注解自动生成 getter/setter/构造器，不用手写样板代码。
 */
@Entity                     // 告诉 JPA："这是一个数据库实体"
@Table(name = "users")      // 对应数据库里的 users 表
@Data                       // Lombok：自动生成 getter、setter、toString、equals、hashCode
@NoArgsConstructor          // Lombok：无参构造函数（JPA 要求必须有）
@AllArgsConstructor         // Lombok：全参构造函数
@Builder                    // Lombok：建造者模式，方便链式创建对象
public class User {

    @Id                                                     // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 自增（数据库自动生成ID）
    private Long id;

    @Column(unique = true, nullable = false)                // 用户名唯一，不能为空
    private String username;

    @Column(nullable = false)                               // 密码不能为空（存的是BCrypt加密后的值）
    private String password;

    private String nickname;                                // 昵称，登录成功后展示用
}
