package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.users.model.UserEntity;

import java.util.List;

public interface UserRepository {

    List<UserEntity> getAllUsers();

    UserEntity findById(Long seq);

    UserEntity findByEmail(String email);

    int insertUser(UserEntity entity);

    void update(UserEntity userEntity);

}
