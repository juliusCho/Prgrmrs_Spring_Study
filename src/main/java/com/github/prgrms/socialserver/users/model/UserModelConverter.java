package com.github.prgrms.socialserver.users.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelConverter {

    private static final Logger log = LoggerFactory.getLogger(UserModelConverter.class);

    public static final List<UserDTO> getAggregate(List<UserEntity> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream()
                .map(x -> {
                    UserDTO dto = null;
                    try {
                        dto = convertToDTO(x);
                    } catch (Exception e) {
                        StackTraceElement[] ste = e.getStackTrace();
                        log.error(String.valueOf(ste[ste.length - 1]));
                    } finally {
                        return dto;
                    }
                })
                .collect(Collectors.toList());
    }

    public static final UserDTO convertToDTO(UserEntity entity) {
        return new UserDTO(entity);
    }

    public static final UserEntity convertToEntity(UserDTO dto) {
        return new UserEntity
                .Builder(dto.getEmail().getAddress())
                .loginCount(dto.getLoginCount())
                .lastLoginAt(dto.getLastLoginAt())
                .createAt(dto.getCreateAt())
                .build();
    }

}
