package com.github.prgrms.socialserver.global.dao;

import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import com.github.prgrms.socialserver.users.model.UserModelConverterTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@SpringBootTest
@ComponentScan(basePackages = {"com.github.prgrms.socialserver"})
public class DAOTest {

    private static final Logger log = LoggerFactory.getLogger(DAOTest.class);

    private String url = "jdbc:h2:file:~/prgrmrs6/class#1_julius/h2;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";

    private String username = "sa";

    private String password = "";

    private DataSource dataSource = DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url(url)
            .username(username)
            .password("")
            .build();


    private DAO dao = new DAO(dataSource);

    @Test
    public void jdbc_testConnect_shouldBeSuccessful() throws Exception {
        try (Connection con = DriverManager.getConnection(url, username, password)) {
            assert("conn1: url=jdbc:h2:file:~/prgrmrs6/class#1_julius/h2 user=SA".equals(con.toString()));
        }
    }

    @Test
    public void dao_selectOne_shouldGetSeq1User() throws Exception {
        UserEntity random = UserModelConverterTest.getRandomEntity(1L);

        String sql = "SELECT * FROM users WHERE seq = ? AND email = ? AND last_login_at < ?";
        LinkedHashMap<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, 1L);
        params.put(2, "user1@prgrmrs6.com");
        params.put(3, new Date());

        UserEntity entity = (UserEntity) dao.executeSQL(CRUD.R, sql, params);
        log.debug("RESULT: {}", entity);
        assert(random.getSeq().compareTo(entity.getSeq()) == 0);
    }

    @Test
    public void dao_selectAll_getAllUserList() throws Exception {
        List<UserEntity> randomList = new ArrayList<>();
        randomList.add(UserModelConverterTest.getRandomEntity(1L));
        randomList.add(UserModelConverterTest.getRandomEntity(2L));
        randomList.add(UserModelConverterTest.getRandomEntity(3L));

        String sql = "SELECT * FROM users";
        List<UserEntity> entityList = (List) dao.executeSQL(CRUD.LIST, sql, new LinkedHashMap<>());
        log.debug("RESULT: {}", entityList);

        for (UserEntity random: randomList) {
            assert (entityList.stream().anyMatch(x -> x.getSeq().compareTo(random.getSeq()) == 0L));
        }
    }

    @Test
    public void dao_insertUser_succeedAndResponse() throws Exception {
        String sql = "INSERT INTO users (email, passwd, login_count, last_login_at, create_at) " +
                "SELECT ?, ?, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP() " +
                "WHERE NOT EXISTS (SELECT seq FROM users WHERE email = ?)";
        LinkedHashMap<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, "user4@prgrmrs6.com");
        params.put(2, "4");
        params.put(3, "user4@prgrmrs6.com");

        ApiResponseDTO responseDTO = (ApiResponseDTO) dao.executeSQL(CRUD.C, sql, params);
        log.debug("RESULT: {}", responseDTO);
        assert (responseDTO.isSuccess());
    }

    @Test
    public void dao_updateUser_successAndResponse() throws Exception {
        String sql = "UPDATE users SET passwd = ?, login_count = ?, last_login_at = ? " +
                "WHERE email = ? AND passwd = ?";
        LinkedHashMap<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, "4");
        params.put(2, 100);
        params.put(3, new Date());
        params.put(4, "user4@prgrmrs6.com");
        params.put(5, "4");

        ApiResponseDTO responseDTO = (ApiResponseDTO) dao.executeSQL(CRUD.C, sql, params);
        log.debug("RESULT: {}", responseDTO);
        assert (responseDTO.isSuccess());
    }

}
