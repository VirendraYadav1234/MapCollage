package com.mapCollage.Security.JdbcRepository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapCollage.Security.Entity.AppUser;
import com.mapCollage.Security.Model.UserPatchRequest;

@Repository
public class AppUserJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AppUserJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // PATCH: Partial update
    public int patchUser(Long userId, UserPatchRequest req) {

        StringBuilder sql = new StringBuilder("UPDATE APP_USERS SET UPDATED_AT = SYSTIMESTAMP ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        if (req.getEmail() != null) {
            sql.append(", EMAIL = :email ");
            params.addValue("email", req.getEmail());
        }

        if (req.getEnabled() != null) {
            sql.append(", ENABLED = :enabled ");
            params.addValue("enabled", req.getEnabled() ? "Y" : "N");
        }

        if (req.getAccountLocked() != null) {
            sql.append(", ACCOUNT_LOCKED = :accountLocked ");
            params.addValue("accountLocked", req.getAccountLocked() ? "Y" : "N");
        }

        sql.append(" WHERE USER_ID = :userId");

        return jdbcTemplate.update(sql.toString(), params);
    }

    // GET single user
    public AppUser getUser(Long userId) {

        String sql = "SELECT USER_ID, USERNAME, EMAIL, ENABLED, ACCOUNT_LOCKED, CREATED_AT, UPDATED_AT " +
                     "FROM APP_USERS WHERE USER_ID = :userId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);

        return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            AppUser user = new AppUser();
            user.setUserId(rs.getLong("USER_ID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setEmail(rs.getString("EMAIL"));
            user.setEnabled(rs.getString("ENABLED"));
            user.setAccountLocked(rs.getString("ACCOUNT_LOCKED"));
            Timestamp createdAtTs = rs.getTimestamp("CREATED_AT");
            //if (createdAtTs != null) user.setCreatedAt(createdAtTs.toLocalDateTime());
            Timestamp updatedAtTs = rs.getTimestamp("UPDATED_AT");
            if (updatedAtTs != null) user.setUpdatedAt(updatedAtTs.toLocalDateTime());
            return user;
        });
    }

    // GET all users
    public List<AppUser> getAllUsers() {
        String sql = "SELECT USER_ID, USERNAME, EMAIL, ENABLED, ACCOUNT_LOCKED, CREATED_AT, UPDATED_AT FROM APP_USERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AppUser user = new AppUser();
            user.setUserId(rs.getLong("USER_ID"));
            user.setUsername(rs.getString("USERNAME"));
            user.setEmail(rs.getString("EMAIL"));
            user.setEnabled(rs.getString("ENABLED"));
            user.setAccountLocked(rs.getString("ACCOUNT_LOCKED"));
            Timestamp createdAtTs = rs.getTimestamp("CREATED_AT");
           // if (createdAtTs != null) user.setCreatedAt(createdAtTs.toLocalDateTime());
            Timestamp updatedAtTs = rs.getTimestamp("UPDATED_AT");
            if (updatedAtTs != null) user.setUpdatedAt(updatedAtTs.toLocalDateTime());
            return user;
        });
    }

    // DELETE user
    public int deleteUser(Long userId) {
        String sql = "DELETE FROM APP_USERS WHERE USER_ID = :userId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        return jdbcTemplate.update(sql, params);
    }
}
