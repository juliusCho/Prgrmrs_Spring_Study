package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.global.exceptions.NotFoundException;
import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.ConnectedUserEntity;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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
    public UserEntity insertUser(UserEntity entity) {
        String sql = "INSERT INTO users (email, passwd, login_count, last_login_at, create_at) " +
                "VALUES (?, ?, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                //TODO 이름 프로퍼티 처리
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, entity.getEmail().getAddress());
                ps.setString(2, entity.getPasswd());
                return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        long generatedSeq = key != null ? key.longValue() : -1;
        return new UserEntity.Builder(generatedSeq, entity.getEmail().getAddress(), entity.getPasswd()).build();
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

    @Override
    public List<ConnectedUserEntity> findAllConnectedUser(IdVO<UserEntity, Long> userId) {
        // TODO 이름 프로퍼티 처리
        return jdbcTemplate.query("SELECT u.seq, u.email, c.granted_at FROM connections c JOIN users u ON c.target_seq = u.seq WHERE c.user_seq=? AND c.granted_at is not null ORDER BY seq DESC",
                new Object[]{userId.value()},
                (rs, rowNum) -> new ConnectedUserEntity(
                        rs.getLong("seq"),
                        new EmailVO(rs.getString("email")),
                        rs.getTimestamp("granted_at").toLocalDateTime()
                )
        );
    }

    @Override
    public List<IdVO<UserEntity, Long>> findConnectedIds(IdVO<UserEntity, Long> userId) {
        return jdbcTemplate.query("SELECT target_seq FROM connections WHERE user_seq=? AND granted_at is not null ORDER BY target_seq",
                new Object[]{userId.value()},
                (rs, rowNum) -> IdVO.of(UserEntity.class, rs.getLong("target_seq")));
    }

}
