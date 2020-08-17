package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.Secrets;
import com.github.prgrms.socialserver.global.utils.DateUtil;
import com.github.prgrms.socialserver.global.utils.EncryptUtil;
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
        if (entity.getSeq().compareTo(0L) == 0 || entity.getEmail().isEmpty()) {
            return null;
        }
        return new UserDTO.Builder(entity.getSeq(), EncryptUtil.setEncryption(Secrets.EMAIL_KEY).decrypt(entity.getEmail()))
                .passwd(EncryptUtil.setEncryption(Secrets.PASSWD_KEY).decrypt(entity.getPasswd()))
                .loginCount(entity.getLoginCount())
                .lastLoginAt(DateUtil.convertToLocalString(entity.getLastLoginAt()))
                .createAt(DateUtil.convertToLocalString(entity.getCreateAt()))
                .build();
    }

    public static final UserEntity convertToEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setSeq(dto.getSeq());
        entity.setEmail(EncryptUtil.setEncryption(Secrets.EMAIL_KEY).encrypt(dto.getEmail()));
        entity.setPasswd(EncryptUtil.setEncryption(Secrets.PASSWD_KEY).encrypt(dto.getPasswd()));
        entity.setLoginCount(dto.getLoginCount());
        entity.setLastLoginAt(DateUtil.convertToUTCDate(dto.getLastLoginAt()));
        entity.setCreateAt(DateUtil.convertToUTCDate(dto.getCreateAt()));
        return entity;
    }

}
