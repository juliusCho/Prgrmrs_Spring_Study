package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.global.exceptions.NotFoundException;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
public class JdbcUserRepository implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    private UserMapper userMapper;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userMapper);
    }

    @Override
    public UserEntity findById(Long seq) {
        String sql = "SELECT * FROM users WHERE seq = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[] {seq}, userMapper))
                .orElseThrow(() -> new NotFoundException(UserEntity.class, seq));
    }

    @Override
    public UserEntity findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[] {email}, userMapper))
                .orElseThrow(() -> new NotFoundException(UserEntity.class, email));
    }

    @Override
    public int insertUser(UserEntity entity) {
        String sql = "INSERT INTO users (email, passwd, login_count, last_login_at, create_at) " +
                "VALUES (?, ?, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";
        return jdbcTemplate.update(sql, entity.getEmail().getAddress(), entity.getPasswd());
    }

    @Override
    public void update(UserEntity user) {
        // TODO 이름 프로퍼티 처리
        jdbcTemplate.update("UPDATE users SET passwd=?,login_count=?,last_login_at=? WHERE seq=?",
                user.getPasswd(),
                user.getLoginCount(),
                user.getLastLoginAt(),
                user.getSeq()
        );
    }

}
