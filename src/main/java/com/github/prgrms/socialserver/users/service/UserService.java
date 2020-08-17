package com.github.prgrms.socialserver.users.service;

import com.github.prgrms.socialserver.global.SystemMessages;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserDTO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.model.UserModelConverter;
import com.github.prgrms.socialserver.users.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

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

    public ApiResponseDTO join(String input) {
        try {
            JSONObject jsonObject = new JSONObject(input);
            UserDTO dto = new UserDTO.Builder(0L, jsonObject.getString("principal"))
                    .passwd(jsonObject.getString("credentials"))
                    .build();

            UserEntity entity = UserModelConverter.convertToEntity(dto);
            return userRepository.insert(entity);
        } catch (JSONException e) {
            StackTraceElement[] ste = e.getStackTrace();
            log.error(String.valueOf(ste[ste.length - 1]));
            return new ApiResponseDTO(false, SystemMessages.ERR_MSG[3]);
        }
    }

}
