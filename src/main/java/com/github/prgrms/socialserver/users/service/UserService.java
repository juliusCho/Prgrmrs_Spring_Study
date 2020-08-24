package com.github.prgrms.socialserver.users.service;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.ConnectedUserEntity;
import com.github.prgrms.socialserver.users.model.EmailVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

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

    public UserEntity findByEmail(EmailVO email) {
        checkNotNull(email, "email must be provided.");
        return userRepository.findByEmail(email.getAddress());
    }

    @Transactional(rollbackFor = Exception.class)
    public UserEntity join(EmailVO email, String passwd) {
        checkArgument(isNotEmpty(passwd), "password must be provided.");
        checkArgument(
                passwd.length() >= 4 && passwd.length() <= 15,
                "password length must be between 4 and 15 characters."
        );
        UserEntity entity = new UserEntity.Builder(email.getAddress(), passwordEncoder.encode(passwd)).build();
        return userRepository.insertUser(entity);
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
