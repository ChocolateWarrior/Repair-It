package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.lang.Integer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class JDBCRequestDao implements RequestDao{

    private Connection connection;
    private JDBCUserDao jdbcUserDao;

    public JDBCRequestDao(Connection connection) {
        this.connection = connection;
        this.jdbcUserDao = new JDBCUserDao(connection);
    }

    @Override
    public void create(RepairRequest entity) {

        System.out.println("OK(1)");
        System.out.println(entity);

        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO requests (specification, description, user_id, request_time)" +
                        " VALUES (? ,?, ?, ?)")){
            ps.setString(1 , entity.getSpecification());
            ps.setString(2 , entity.getDescription());
            ps.setInt(3 , entity.getUser().getId());
            ps.setTimestamp(4 , convertToTimestamp(entity.getRequestTime()));
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private RepairRequest extractFromResultSet(ResultSet rs)
            throws SQLException {
        RepairRequest result = new RepairRequest();

        result.setId(rs.getInt("id"));
        result.setSpecification(Specification.valueOf(rs.getString("specification")));
        result.setDescription(rs.getString("description"));
        result.setUser(getUser(parseInt(rs.getString("user_id"))));
        result.setRequestTime(convertTime(rs.getDate("request_time")));

        return result;
    }

    public User getUser(int id){
        return jdbcUserDao.findById(id);
    }

    public LocalDateTime convertTime(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }

    public Timestamp convertToTimestamp(LocalDateTime time){
        return Timestamp.valueOf(time);
    }

    @Override
    public RepairRequest findById(int id) {
        RepairRequest result = new RepairRequest();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM requests WHERE id = \'" + id + "\';");
            while ( rs.next() ){
                result = extractFromResultSet(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public RepairRequest findByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public HashSet<RepairRequest> findAll() {
        HashSet<RepairRequest> resultList = new HashSet<>();
        Map<Integer,RepairRequest> requests = new HashMap<>();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM requests;");
            while ( rs.next() ){
                RepairRequest request = extractFromResultSet(rs);
                request = makeUniqueRequest( requests, request);
                resultList.add(request);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private RepairRequest makeUniqueRequest(Map<Integer, RepairRequest> requests,  RepairRequest request) {

        requests.putIfAbsent(request.getId(), request);

//        if(!requests.containsKey(request.jsp.getId())){
//            requests.put(request.jsp.getId(), request.jsp);
//        }

        return requests.get(request.getId());
    }

    @Override
    public void update(RepairRequest entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {

    }
}