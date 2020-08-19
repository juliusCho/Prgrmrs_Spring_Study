package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet rs, int i) throws SQLException {
        return new UserEntity
                .Builder(rs.getLong("seq"), rs.getString("email"), rs.getString("passwd"))
                .loginCount(rs.getInt("login_count"))
                .lastLoginAt(rs.getTimestamp("last_login_at").toLocalDateTime())
                .createAt(rs.getTimestamp("create_at").toLocalDateTime())
                .build();
    }

}
