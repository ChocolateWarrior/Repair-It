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


    public RequestMapper() {
    }

    @Override
    public RepairRequest extractFromResultSet(ResultSet rs) throws SQLException {

        RepairRequest result = new RepairRequest();


        result.setId(rs.getInt("requests.id"));
        result.setSpecification(Specification.valueOf(rs.getString("requests.specification")));
        result.setDescription(rs.getString("requests.description"));
        result.setRequestTime(convertTime(rs.getDate("requests.request_time")));
        if(Objects.nonNull(rs.getString("requests.state")))
            result.setState(RequestState.valueOf(rs.getString("requests.state")));
        if(Objects.nonNull(rs.getDate("requests.finish_time")))
            result.setFinishTime(convertTime(rs.getDate("requests.finish_time")));
        if(Objects.nonNull(rs.getBigDecimal("requests.price")))
            result.setPrice(rs.getBigDecimal("requests.price"));
        if(Objects.nonNull(rs.getString("requests.rejection_message")))
            result.setRejectionMessage(rs.getString("requests.rejection_message"));
        if(Objects.nonNull(rs.getString("requests.comment")))
            result.setComment(rs.getString("requests.comment"));

        return result;
    }

    @Override
    public RepairRequest makeUnique(Map<Integer, RepairRequest> cache, RepairRequest entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }

    private LocalDateTime convertTime(Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }


}
