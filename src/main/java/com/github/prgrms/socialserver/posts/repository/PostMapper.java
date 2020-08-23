package com.github.prgrms.socialserver.posts.repository;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.posts.model.PostEntity;
import com.github.prgrms.socialserver.posts.model.WriterVO;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PostMapper implements RowMapper<PostEntity> {

    @Override
    public PostEntity mapRow(ResultSet rs, int i) throws SQLException {
        return new PostEntity.Builder()
                .seq(rs.getLong("seq"))
                .userId(IdVO.of(UserEntity.class, rs.getLong("user_seq")))
                .contents(rs.getString("contents"))
                .likes(rs.getInt("like_count"))
                .comments(rs.getInt("comment_count"))
                .writer(new WriterVO(new EmailVO(rs.getString("email"))))
                .createAt(rs.getTimestamp("create_at").toLocalDateTime())
                .build();
    }
}
