package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.lang.Integer;
import java.util.*;

import static java.lang.Integer.parseInt;

public class JDBCRequestDao implements RequestDao{

    private Connection connection;
    private JDBCUserDao jdbcUserDao;
    public static final Logger log = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public JDBCRequestDao(Connection connection) {
        this.connection = connection;
        this.jdbcUserDao = new JDBCUserDao(connection);
    }

    @Override
    public void create(RepairRequest entity) {

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
        result.setUser(getUser(parseInt(rs.getString("user_id"))).get());
        result.setRequestTime(convertTime(rs.getDate("request_time")));
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

    public Optional<User> getUser(int id){
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
    public Optional<RepairRequest> findById(int id) {
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
        return Optional.ofNullable(result);
    }

    @Override
    public HashSet<RepairRequest> findAll() {
        HashSet<RepairRequest> resultSet = new HashSet<>();
        Map<Integer,RepairRequest> requests = new HashMap<>();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM requests;");
            while ( rs.next() ){
                RepairRequest request = extractFromResultSet(rs);
                request = makeUniqueRequest( requests, request);
                resultSet.add(request);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    public HashSet<RepairRequest> findByUser(int id) {
        HashSet<RepairRequest> resultSet = new HashSet<>();
        Map<Integer,RepairRequest> requests = new HashMap<>();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM requests WHERE user_id = \'" + id + "\';");
            while ( rs.next() ){
                RepairRequest request = extractFromResultSet(rs);
                request = makeUniqueRequest( requests, request);
                resultSet.add(request);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public void rejectRequest(int id, String message){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE requests SET" +
                             " state = ?," +
                             " rejection_message = ?" +
                             " WHERE id = ?")) {
            ps.setString(1, RequestState.REJECTED.name());
            ps.setString(2, message);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private RepairRequest makeUniqueRequest(Map<Integer, RepairRequest> requests,
                                            RepairRequest request) {
        requests.putIfAbsent(request.getId(), request);

        return requests.get(request.getId());
    }

    public void addRequestMaster(RepairRequest request, User master){
        try(PreparedStatement ps =
                connection.prepareStatement("INSERT INTO masters_requests(master_id, request_id) VALUES" +
                        "(?, ?)")) {
            ps.setInt(1, master.getId());
            ps.setInt(2, request.getId());

        } catch (Exception e){
            throw new RuntimeException(e);
        }
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
