package com.luojinghan.my_springboot.repository;

import com.luojinghan.my_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * 用户数据访问层。
 * 继承 JpaRepository 后自动获得 CRUD 方法，不需要写 SQL。
 * 方法名就是查询指令 —— Spring Data JPA 会根据方法名自动生成 SQL。
 *
 * 例：findByUsername("admin") → SELECT * FROM users WHERE username = 'admin'
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户。
     * @return Optional 包装 —— 用户可能存在也可能不存在，调用方自己处理两种情况。
     */
    Optional<User> findByUsername(String username);
}
