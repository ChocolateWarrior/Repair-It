package com.helvetica.model.dao.mapper;

import com.helvetica.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MasterMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public User makeUnique(Map<Integer, User> cache, User entity) {
        return null;
    }

//    @Override
//    public Student extractFromResultSet(ResultSet rs) throws SQLException {
//        Student student = new Student();
//        student.setId(rs.getInt("idstuden"));
//        student.setName(rs.getString("studen.name"));
//        student.setGroupe(rs.getInt("group"));
//        return student;
//    }
//
//    @Override
//    public Student makeUnique(Map<Integer, Student> cache,
//                              Student student) {
//        cache.putIfAbsent(student.getId(), student);
//        return cache.get(student.getId());
//    }

}
