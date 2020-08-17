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

    public Object list() {
        Object result = userRepository.list();
        if (result instanceof List<?>) {
            return UserModelConverter.getAggregate((List) result);
        }
        return result;
    }

    public Object detail(Long seq) {
        Object result = userRepository.detail(seq);
        if (result instanceof UserDTO) {
            return UserModelConverter.convertToDTO((UserEntity) result);
        }
        return result;
    }

    public ApiResponseDTO join(UserDTO dto) {
        UserEntity entity = UserModelConverter.convertToEntity(dto);
        return userRepository.insert(entity);
    }

}
