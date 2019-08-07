package com.helvetica.model.dao.mapper;

import com.helvetica.model.entity.RepairRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class RequestMapper implements ObjectMapper<RepairRequest> {
    @Override
    public RepairRequest extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public RepairRequest makeUnique(Map<Integer, RepairRequest> cache, RepairRequest entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
