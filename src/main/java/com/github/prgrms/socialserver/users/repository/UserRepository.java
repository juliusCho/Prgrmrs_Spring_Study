package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    private UserMapper userMapper;

    public UserRepository(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    public List<UserEntity> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userMapper);
    }

    public UserEntity getUserDetail(Long seq) {
        String sql = "SELECT * FROM users WHERE seq = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {seq}, userMapper);
    }

    public UserEntity checkUserExist(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[] {email}, userMapper);
    }

    public int insertUser(UserEntity entity) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        String sql = "INSERT INTO users (email, passwd, login_count, last_login_at, create_at) " +
                "VALUES (?, ?, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";
        return jdbcTemplate.update(sql, entity.getEmail(), entity.getPasswd());
    }

}
