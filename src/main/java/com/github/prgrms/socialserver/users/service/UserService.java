package com.github.prgrms.socialserver.users.service;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.ConnectedUserEntity;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserEntity findById(IdVO<UserEntity, Long> id) {
        return userRepository.findById(id.value());
    }

    @Transactional(rollbackFor = Exception.class)
    public UserEntity insertUser(String input) throws JSONException {
        JSONObject jsonObject = new JSONObject(input);
        UserEntity entity = new UserEntity
                .Builder(jsonObject.getString("principal"), jsonObject.getString("credentials"))
                .build();
        try {
            userRepository.findByEmail(entity.getEmail().getAddress());
        } catch (EmptyResultDataAccessException e) {
            return userRepository.insertUser(entity);
        }
        throw new IllegalArgumentException();
    }

    @Transactional
    public UserEntity login(EmailVO emailVO, String passwd) {
        checkNotNull(passwd, "password must be provided");

        UserEntity userEntity = userRepository.findByEmail(emailVO.getAddress());
        userEntity.login(passwordEncoder, passwd);
        userEntity.afterLoginSuccess();
        userRepository.update(userEntity);
        return userEntity;
    }

    @Transactional(readOnly = true)
    public List<ConnectedUserEntity> findAllConnectedUser(IdVO<UserEntity, Long> userId) {
        checkNotNull(userId, "userId must be provided.");

        return userRepository.findAllConnectedUser(userId);
    }

    @Transactional(readOnly = true)
    public List<IdVO<UserEntity, Long>> findConnectedIds(IdVO<UserEntity, Long> userId) {
        checkNotNull(userId, "userId must be provided.");

        return userRepository.findConnectedIds(userId);
    }

}
