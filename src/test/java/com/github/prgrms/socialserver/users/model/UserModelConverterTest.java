package com.github.prgrms.socialserver.users.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class UserModelConverterTest {

    private static final Logger log = LoggerFactory.getLogger(UserModelConverterTest.class);

    private static final UserModelConverter userModelConverter = new UserModelConverter();


    public static UserEntity getRandomEntity(Long seq) throws Exception {
        return new UserEntity
                .Builder(seq, "user" + seq + "@prgrmrs6.com", "1")
                .loginCount(0)
                .lastLoginAt(LocalDateTime.now(ZoneOffset.UTC))
                .createAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();
    }



    @Test
    public void userModelConverter_withRandomEntity_convertToDTOAndEntity() throws Exception {
        UserEntity entity = this.getRandomEntity(1L);
        log.debug("INPUT: {}", entity);

        UserDTO dto = userModelConverter.convertToDTO(entity);
        log.debug("dto: {}", dto);

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
