package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.ConnectedUserEntity;
import com.github.prgrms.socialserver.users.model.UserEntity;

import java.util.List;

public interface UserRepository {

    List<UserEntity> getAllUsers();

    UserEntity findById(Long seq);

    UserEntity findByEmail(String email);

    UserEntity insertUser(UserEntity entity);

    void update(UserEntity userEntity);

    List<ConnectedUserEntity> findAllConnectedUser(IdVO<UserEntity, Long> userId);

    List<IdVO<UserEntity, Long>> findConnectedIds(IdVO<UserEntity, Long> userId);

}
