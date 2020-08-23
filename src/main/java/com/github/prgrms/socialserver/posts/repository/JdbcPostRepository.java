package com.github.prgrms.socialserver.posts.repository;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.posts.model.PostEntity;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class JdbcPostRepository implements PostRepository {

  private final JdbcTemplate jdbcTemplate;

  private PostMapper postMapper;

  public JdbcPostRepository(JdbcTemplate jdbcTemplate, PostMapper postMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.postMapper = postMapper;
  }

  @Override
  public PostEntity save(PostEntity postEntity) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement("INSERT INTO posts(seq,user_seq,contents,like_count,comment_count,create_at) VALUES (null,?,?,?,?,?)", new String[]{"seq"});
      ps.setLong(1, postEntity.getUserId().value());
      ps.setString(2, postEntity.getContents());
      ps.setInt(3, postEntity.getLikes());
      ps.setInt(4, postEntity.getComments());
      ps.setTimestamp(5, Timestamp.valueOf(postEntity.getCreateAt()));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    long generatedSeq = key != null ? key.longValue() : -1;
    return new PostEntity.Builder(postEntity)
      .seq(generatedSeq)
      .build();
  }

  @Override
  public void update(PostEntity postEntity) {
    jdbcTemplate.update("UPDATE posts SET contents=?,like_count=?,comment_count=? WHERE seq=?",
      postEntity.getContents(),
      postEntity.getLikes(),
      postEntity.getComments(),
      postEntity.getSeq()
    );
  }

  @Override
  public Optional<PostEntity> findById(IdVO<PostEntity, Long> postId) {
    List<PostEntity> results = jdbcTemplate.query("SELECT p.*,u.email FROM posts p JOIN users u ON p.user_seq=u.seq WHERE p.seq=?",
      new Object[]{postId.value()},
      postMapper
    );
    return ofNullable(results.isEmpty() ? null : results.get(0));
  }

  @Override
  public List<PostEntity> findAll(IdVO<UserEntity, Long> userId) {
    return jdbcTemplate.query("SELECT p.*,u.email FROM posts p JOIN users u ON p.user_seq=u.seq WHERE p.user_seq=? ORDER BY p.seq DESC",
      new Object[]{userId.value()},
      postMapper
    );
  }

}
