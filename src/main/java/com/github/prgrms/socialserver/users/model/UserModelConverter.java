package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.utils.DateUtil;
import com.github.prgrms.socialserver.global.utils.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelConverter {

    private static String PASSWD_KEY;
    private static String EMAIL_KEY;

    @Value("${secrets.users.passwd}")
    public void setPasswdKey(String passwdKey) {
        PASSWD_KEY = passwdKey;
    }
    @Value("${secrets.users.email}")
    public void setEmailKey(String emailKey) {
        EMAIL_KEY = emailKey;
    }

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
        return new UserDTO
                .Builder(EncryptUtil.setEncryption(EMAIL_KEY).decrypt(entity.getEmail()), EncryptUtil.setEncryption(PASSWD_KEY).decrypt(entity.getPasswd()))
                .loginCount(entity.getLoginCount())
                .lastLoginAt(DateUtil.convertToLocalString(entity.getLastLoginAt()))
                .createAt(DateUtil.convertToLocalString(entity.getCreateAt()))
                .build();
    }

    public static final UserEntity convertToEntity(UserDTO dto) {
        return new UserEntity
                .Builder(EncryptUtil.setEncryption(EMAIL_KEY).encrypt(dto.getEmail()), EncryptUtil.setEncryption(PASSWD_KEY).encrypt(dto.getPasswd()))
                .loginCount(dto.getLoginCount())
                .lastLoginAt(DateUtil.convertToUTCDate(dto.getLastLoginAt()))
                .createAt(DateUtil.convertToUTCDate(dto.getCreateAt()))
                .build();
    }

}
