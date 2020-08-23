package com.github.prgrms.socialserver.posts.repository;


import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.posts.model.PostEntity;
import com.github.prgrms.socialserver.users.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

  PostEntity save(PostEntity postEntity);

  void update(PostEntity postEntity);

  Optional<PostEntity> findById(IdVO<PostEntity, Long> postId);

  List<PostEntity> findAll(IdVO<UserEntity, Long> userId);

}
