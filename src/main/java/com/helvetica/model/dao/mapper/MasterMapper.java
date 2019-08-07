package com.helvetica.model.dao.mapper;

import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MasterMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {

        User result = new User();

        result.setId(rs.getInt("id"));
        result.setFirstName(rs.getString("first_name"));
        result.setLastName(rs.getString("last_name"));
        result.setUsername(rs.getString("username"));
        result.setPassword(rs.getString("password"));
        result.setAuthority(Role.USER);
        return result;

    }

    @Override
    public User makeUnique(Map<Integer, User> cache, User entity) {

        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }

//
//    public User extractFromResultSet(ResultSet rs) throws SQLException {
//        User user = new User();
//        user.setId(rs.getLong("users.id"));
//        user.setFirstName(rs.getString("users.first_name"));
//        user.setLastName(rs.getString("users.last_name"));
//        user.setPassword(rs.getString("users.password"));
//        user.setUsername(rs.getString("users.username"));
//        return user;
//    }



}
