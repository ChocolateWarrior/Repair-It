package com.helvetica.model.dao.mapper;

import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class RequestMapper implements ObjectMapper<RepairRequest> {

    private JDBCUserDao jdbcUserDao;
    private Connection connection;

    public RequestMapper(Connection connection) {
        this.connection = connection;
        this.jdbcUserDao = new JDBCUserDao(connection);
    }

    @Override
    public RepairRequest extractFromResultSet(ResultSet rs) throws SQLException {

        RepairRequest result = new RepairRequest();

        result.setId(rs.getInt("id"));
        result.setSpecification(Specification.valueOf(rs.getString("specification")));
        result.setDescription(rs.getString("description"));
        result.setUser(getUser(parseInt(rs.getString("user_id"))).get());
        result.setRequestTime(convertTime(rs.getDate("request_time")));
        if(Objects.nonNull(rs.getString("state")))
            result.setState(RequestState.valueOf(rs.getString("state")));
        if(Objects.nonNull(rs.getDate("finish_time")))
            result.setFinishTime(convertTime(rs.getDate("finish_time")));
        if(Objects.nonNull(rs.getBigDecimal("price")))
            result.setPrice(rs.getBigDecimal("price"));
        if(Objects.nonNull(rs.getString("rejection_message")))
            result.setRejectionMessage(rs.getString("rejection_message"));
        if(Objects.nonNull(rs.getString("comment")))
            result.setComment(rs.getString("comment"));

        return result;
    }

    @Override
    public RepairRequest makeUnique(Map<Integer, RepairRequest> cache, RepairRequest entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }

    private Optional<User> getUser(int id){
        return jdbcUserDao.findById(id);
    }

    private LocalDateTime convertTime(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }


}
