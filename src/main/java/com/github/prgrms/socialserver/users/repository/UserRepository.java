package com.github.prgrms.socialserver.users.repository;

import com.github.prgrms.socialserver.global.dao.CRUD;
import com.github.prgrms.socialserver.global.dao.DAO;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class UserRepository {

    @Autowired
    private DAO dao;

    public List<UserEntity> list() {
        String sql = "SELECT * FROM users";
        return (List) dao.executeSQL(CRUD.LIST, sql, new LinkedHashMap<>());
    }

    public UserEntity detail(Long seq) {
        String sql = "SELECT * FROM users WHERE seq = ?";
        LinkedHashMap<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, seq);
        return (UserEntity) dao.executeSQL(CRUD.R, sql, params);
    }

    public ApiResponseDTO insert(UserEntity entity) {
        String sql = "INSERT INTO users (email, passwd, login_count, last_login_at, create_at) " +
                "SELECT ?, ?, 0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP() " +
                "WHERE NOT EXISTS (SELECT seq FROM users WHERE email = ?)";
        LinkedHashMap<Integer, Object> params = new LinkedHashMap<>();
        params.put(1, entity.getEmail());
        params.put(2, entity.getPasswd());
        params.put(3, entity.getEmail());
        return (ApiResponseDTO) dao.executeSQL(CRUD.C, sql, params);
    }

}
