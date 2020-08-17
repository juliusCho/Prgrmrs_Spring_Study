package com.github.prgrms.socialserver.users.model;

import com.github.prgrms.socialserver.global.Secrets;
import com.github.prgrms.socialserver.global.utils.EncryptUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserModelConverterTest {

    private static final Logger log = LoggerFactory.getLogger(UserModelConverterTest.class);

    private static final UserModelConverter userModelConverter = new UserModelConverter();


    public static UserEntity getRandomEntity(Long seq) throws Exception {
        UserEntity entity = new UserEntity();
        entity.setSeq(seq);
        entity.setEmail(EncryptUtil.setEncryption(Secrets.EMAIL_KEY).encrypt("user" + seq + "@prgrmrs6.com"));
        entity.setPasswd(EncryptUtil.setEncryption(Secrets.PASSWD_KEY).encrypt("1"));
        entity.setLoginCount(0);
        entity.setLastLoginAt(new Date());
        entity.setCreateAt(new Date());
        return entity;
    }



    @Test
    public void userModelConverter_withRandomEntity_convertToDTOAndEntity() throws Exception {
        UserEntity entity = this.getRandomEntity(1L);
        log.debug("INPUT: {}", entity);

        assert(userModelConverter.convertToDTO(entity).getClass().equals(UserDTO.class));

        UserDTO dto = userModelConverter.convertToDTO(entity);

        assert(userModelConverter.convertToEntity(dto).equals(entity));
    }



    @Test
    public void userModelConverter_withRandomEntityList_convertToDTOList() throws Exception {
        List<UserEntity> entityList = new ArrayList<>();
        entityList.add(this.getRandomEntity(1L));
        entityList.add(this.getRandomEntity(2L));
        entityList.add(this.getRandomEntity(3L));
        log.debug("INPUT: {}", entityList);

        List<UserDTO> dtoList = userModelConverter.getAggregate(entityList);
        log.debug("OUTPUT: {}", dtoList);
    }

}
