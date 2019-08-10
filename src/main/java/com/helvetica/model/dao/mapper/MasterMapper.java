package com.helvetica.model.dao.mapper;

import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;

import java.math.BigDecimal;
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
        result.setBalance(rs.getBigDecimal("balance"));
        if(!result.hasAuthority(Role.USER))
            result.addAuthority(Role.USER);

        return result;

    }

    @Override
    public User makeUnique(Map<Integer, User> cache, User entity) {

        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }

}
