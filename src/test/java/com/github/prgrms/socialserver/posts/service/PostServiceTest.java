package com.github.prgrms.socialserver.posts.service;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.posts.model.PostEntity;
import com.github.prgrms.socialserver.posts.model.WriterVO;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired private PostService postService;

  private IdVO<UserEntity, Long> userId;

  @BeforeAll
  void setUp() {
    userId = IdVO.of(UserEntity.class, 1L);
  }

  @Test
  @Order(1)
  void 포스트를_작성한다() {
    WriterVO writer = new WriterVO(new EmailVO("test00@gmail.com"));
    String contents = randomAlphabetic(40);
    PostEntity post = postService.write(new PostEntity(userId, writer, contents));
    assertThat(post, is(notNullValue()));
    assertThat(post.getSeq(), is(notNullValue()));
    assertThat(post.getContents(), is(contents));
    log.info("Written post: {}", post);
  }

  @Test
  @Order(2)
  void 포스트를_수정한다() {
    PostEntity post = postService.findById(IdVO.of(PostEntity.class, 1L)).orElse(null);
    assertThat(post, is(notNullValue()));
    String contents = randomAlphabetic(40);
    post.modify(contents);
    postService.modify(post);
    assertThat(post.getContents(), is(contents));
    log.info("Modified post: {}", post);
  }

  @Test
  @Order(3)
  void 포스트_목록을_조회한다() {
    List<PostEntity> posts = postService.findAll(userId);
    assertThat(posts, is(notNullValue()));
    assertThat(posts.size(), is(4));
  }

}