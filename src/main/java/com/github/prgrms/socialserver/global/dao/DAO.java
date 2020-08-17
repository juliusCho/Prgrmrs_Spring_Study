package com.github.prgrms.socialserver.global.dao;

import com.github.prgrms.socialserver.global.SystemMessages;
import com.github.prgrms.socialserver.global.model.ApiResponseDTO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class DAO {

    private static final Logger log = LoggerFactory.getLogger(DAO.class);

    @Autowired
    private DataSource dataSource;

    private Connection con;

    public DAO() {};
    public DAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    private void dbConnect() {
        this.con = DataSourceUtils.getConnection(this.dataSource);
    }

    private void sqlError(SQLException e) {
        log.error(SystemMessages.ERR_MSG[0], e);
        try {
            this.con.close();
        } catch (SQLException se) {
            log.error(SystemMessages.ERR_MSG[1], se);
        }
    }

    private void dbDisconnect() {
        DataSourceUtils.releaseConnection(this.con, this.dataSource);
    }





    public Object executeSQL(CRUD type, String sql, LinkedHashMap<Integer, Object> params) {
        this.dbConnect();
        try {
            switch (type) {
                case C:
                case U:
                    return this.update(sql, params);
                case R:
                    return this.selectOne(sql, params);
                case LIST:
                    return this.select(sql, params);
                default:    // D
                    return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            this.sqlError(e);
            return new ApiResponseDTO(false, SystemMessages.ERR_MSG[0]);
        } finally {
            this.dbDisconnect();
        }
    }

    private void setParams(PreparedStatement ps, LinkedHashMap<Integer, Object> params) {
        params.keySet().stream().forEach(key -> {
            try {
                switch (params.get(key).getClass().getName()) {
                    case "java.lang.String":
                        ps.setString(key, params.get(key).toString());
                        break;
                    case "java.lang.Long":
                        ps.setLong(key, Long.parseLong(params.get(key).toString()));
                        break;
                    case "java.lang.Integer":
                        ps.setInt(key, Integer.parseInt(params.get(key).toString()));
                        break;
                    case "java.util.Date":
                        ps.setTimestamp(key, new Timestamp(((java.util.Date) params.get(key)).getTime()));
                        break;
                    default:
                        break;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private ApiResponseDTO update(String sql, LinkedHashMap<Integer, Object> params) throws SQLException {
        PreparedStatement ps = this.con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        this.setParams(ps, params);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected == 0) {
            return new ApiResponseDTO(true, String.format(SystemMessages.SUCCESS_MSG[1], 0));
        }

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            return new ApiResponseDTO(true, String.format(SystemMessages.SUCCESS_MSG[1], rowsAffected));
        } else {
            log.error(SystemMessages.ERR_MSG[2]);
            return new ApiResponseDTO(false, SystemMessages.ERR_MSG[2]);
        }
    }

    private Object selectOne(String sql, LinkedHashMap<Integer, Object> params) throws SQLException {
        PreparedStatement ps = this.con.prepareStatement(sql);
        this.setParams(ps, params);
        ResultSet rs = ps.executeQuery();
        if (!rs.first()) {
            return new ApiResponseDTO(true, SystemMessages.SUCCESS_MSG[2]);
        } else {
            return this.getEntity(rs);
        }
    }

    private Object select(String sql, LinkedHashMap<Integer, Object> params) throws SQLException {
        PreparedStatement ps = this.con.prepareStatement(sql);
        this.setParams(ps, params);
        ResultSet rs = ps.executeQuery();
        if (!rs.first()) {
            return new ApiResponseDTO(true, SystemMessages.SUCCESS_MSG[2]);
        }

        List<UserEntity> entityList = new ArrayList<>();
        entityList.add(this.getEntity(rs));
        while (rs.next()) {
            entityList.add(this.getEntity(rs));
        }
        return entityList;
    }

    private UserEntity getEntity(ResultSet rs) throws SQLException {
        UserEntity entity = new UserEntity();
        entity.setSeq(rs.getLong("seq"));
        entity.setEmail(rs.getString("email"));
        entity.setPasswd(rs.getString("passwd"));
        entity.setLoginCount(rs.getInt("login_count"));
        entity.setLastLoginAt(rs.getTimestamp("last_login_at"));
        entity.setCreateAt(rs.getTimestamp("create_at"));
        return entity;
    }

}
