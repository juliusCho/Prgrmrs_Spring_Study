package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.users.model.UserDTO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.model.UserModelConverter;
import com.github.prgrms.socialserver.users.model.UserModelConverterTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ComponentScan(basePackages = {"com.github.prgrms.socialserver"})
public class UserRepositoryImplTest {

    private static final Logger log = LoggerFactory.getLogger(UserRepositoryImplTest.class);

    private String h2 = "jdbc:h2:file:~/prgrmrs6/class#1_julius/h2";

    private String url = h2 + ";DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";

    private String username = "sa";

    private String password = "";

    private DataSource dataSource = DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url(url)
            .username(username)
            .password("")
            .build();

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    private UserRepositoryImpl userRepositoryImpl = new UserRepositoryImpl(jdbcTemplate, new UserMapper());


    @Test
    public void jdbc_testConnect_shouldBeSuccessful() throws Exception {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            assert(con.toString().contains(this.h2));
        }
    }

    @Test
    public void dao_selectOne_shouldGetSeq1User() throws Exception {
        UserEntity random = UserModelConverterTest.getRandomEntity(1L);

        UserEntity entity = (UserEntity) userRepositoryImpl.getUserDetail(random.getSeq());
        log.debug("RESULT: {}", entity);
        assert(random.getSeq().compareTo(entity.getSeq()) == 0);
    }

    @Test
    public void dao_selectAll_getAllUserList() throws Exception {
        List<UserEntity> randomList = new ArrayList<>();
        randomList.add(UserModelConverterTest.getRandomEntity(1L));
        randomList.add(UserModelConverterTest.getRandomEntity(2L));
        randomList.add(UserModelConverterTest.getRandomEntity(3L));

        List<UserEntity> entityList = userRepositoryImpl.getAllUsers();
        log.debug("RESULT: {}", entityList);

        for (UserEntity random: randomList) {
            assert (entityList.stream().anyMatch(x -> x.getSeq().compareTo(random.getSeq()) == 0L));
        }
    }

    @Test
    public void dao_insertUser_succeedAndResponse() throws Exception {
        UserModelConverter userModelConverter = new UserModelConverter();

        UserEntity random = UserModelConverterTest.getRandomEntity(99L);
        UserDTO dto = UserModelConverter.convertToDTO(UserModelConverterTest.getRandomEntity(99L));
        log.debug("DTO: {}", UserModelConverter.convertToDTO(random));

        int number = userRepositoryImpl.insertUser(UserModelConverter.convertToEntity(dto));
        log.debug("RESULT: {}", number);
        assert (number != 0);
    }

}
