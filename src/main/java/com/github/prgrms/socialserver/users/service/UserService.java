package com.github.prgrms.socialserver.users.service;

import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserEntity getUserDetail(Long seq) {
        return userRepository.getUserDetail(seq);
    }

    public int insertUser(String input) throws JSONException {
        JSONObject jsonObject = new JSONObject(input);
        UserEntity entity = new UserEntity
                .Builder(jsonObject.getString("principal"), jsonObject.getString("credentials"))
                .build();
        try {
            userRepository.checkUserExist(entity.getEmail());
        } catch (EmptyResultDataAccessException e) {
            return userRepository.insertUser(entity);
        }
        throw new IllegalArgumentException();
    }

}
