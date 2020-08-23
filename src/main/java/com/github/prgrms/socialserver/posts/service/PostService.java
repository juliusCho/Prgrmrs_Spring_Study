package com.github.prgrms.socialserver.posts.service;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.posts.model.PostEntity;
import com.github.prgrms.socialserver.posts.repository.PostRepository;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class PostService {

  private final UserRepository userRepository;

  private final PostRepository postRepository;

  public PostService(UserRepository userRepository, PostRepository postRepository) {
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  @Transactional
  public PostEntity write(PostEntity post) {
    return save(post);
  }

  @Transactional
  public PostEntity modify(PostEntity post) {
    update(post);
    return post;
  }

  @Transactional(readOnly = true)
  public Optional<PostEntity> findById(IdVO<PostEntity, Long> postId) {
    checkNotNull(postId, "postId must be provided.");

    return postRepository.findById(postId);
  }

  @Transactional(readOnly = true)
  public List<PostEntity> findAll(IdVO<UserEntity, Long> userId) {
    checkNotNull(userId, "userId must be provided.");

    userRepository.findById(userId.value());
    return postRepository.findAll(userId);
  }

  private PostEntity save(PostEntity post) {
    return postRepository.save(post);
  }

  private void update(PostEntity post) {
    postRepository.update(post);
  }

}
