package com.github.prgrms.socialserver.users.service;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserDTO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.model.UserModelConverter;
import com.github.prgrms.socialserver.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> list() {
        List<UserEntity> entityList = userRepository.list();
        return UserModelConverter.getAggregate(entityList);
    }

    public UserDTO detail(Long seq) {
        UserEntity entity = userRepository.detail(seq);
        return UserModelConverter.convertToDTO(entity);
    }

    public ApiResponseDTO join(UserDTO dto) {
        UserEntity entity = UserModelConverter.convertToEntity(dto);
        return userRepository.insert(entity);
    }

}
