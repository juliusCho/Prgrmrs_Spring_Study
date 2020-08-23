package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.users.model.UserEntity;

import java.util.List;

public interface UserRepository {

    List<UserEntity> getAllUsers();

    UserEntity getUserDetail(Long seq);

    UserEntity checkUserExist(String email);

    int insertUser(UserEntity entity);

}
