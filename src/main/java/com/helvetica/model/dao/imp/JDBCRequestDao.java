package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.dao.mapper.MasterMapper;
import com.helvetica.model.dao.mapper.RequestMapper;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.lang.Integer;
import java.util.*;

public class JDBCRequestDao implements RequestDao{

    private Connection connection;
    private static final Logger log = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    JDBCRequestDao(Connection connection) {
        this.connection = connection;
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


    @Override
    public Optional<RepairRequest> findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM requests WHERE id =?"
        )){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Map<Integer, RepairRequest> request = extractFromResultSet(rs);
            return Optional.ofNullable(request.get(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<RepairRequest> findAll() {
        HashSet<RepairRequest> resultSet;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM requests;")){
            ResultSet rs = ps.executeQuery();

            Map<Integer, RepairRequest> requests = extractFromResultSet(rs);
            resultSet = new HashSet<>(requests.values());

            return resultSet;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<RepairRequest> findByUser(int id) {

        log.info("trying to find by user with id: " + id);
        HashSet<RepairRequest> resultSet;
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM requests WHERE user_id =?")){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Integer, RepairRequest> requests = extractFromResultSet(rs);
            resultSet = new HashSet<>(requests.values());

            return resultSet;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Set<RepairRequest> findByMaster(int id) {

        log.info("trying to find by master with id: " + id);
        HashSet<RepairRequest> resultSet;
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM requests WHERE id IN " +
                        "(SELECT request_id FROM masters_requests WHERE master_id=?)"
        )){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Integer, RepairRequest> requests = extractFromResultSet(rs);
            resultSet = new HashSet<>(requests.values());

            return resultSet;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    public void updateStateAndPrice(int id, RequestState state, BigDecimal price){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE requests SET" +
                             " state = ?," +
                             " price= ?" +
                             " where id = ?")) {
            ps.setString(1, state.name());
            ps.setBigDecimal(2, price);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeRequest(int id){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE requests SET" +
                             " state = ?," +
                             " finish_time = ?" +
                             " where id = ?")) {
            ps.setString(1, RequestState.COMPLETED.name());
            ps.setTimestamp(2, convertToTimestamp(LocalDateTime.now()));
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequestMaster(RepairRequest request, User master){

        try(PreparedStatement ps =
                connection.prepareStatement("INSERT INTO masters_requests(master_id, request_id) VALUES" +
                        "(?, ?)")) {
            ps.setInt(1, master.getId());
            ps.setInt(2, request.getId());

            ps.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void setRequestComment(int id, String comment){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE requests SET" +
                             " comment = ?" +
                             " where id = ?")) {
            ps.setString(1, comment);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePayment(int id){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE requests SET" +
                             " state = ?" +
                             " where id = ?")) {
            ps.setString(1, RequestState.PAID.name());
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
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

    private Timestamp convertToTimestamp(LocalDateTime time){
        return Timestamp.valueOf(time);
    }

    private Map<Integer, RepairRequest> extractFromResultSet(ResultSet rs) throws SQLException {
        Map<Integer, RepairRequest> requests = new HashMap<>();
        Map<Integer, User> masters = new HashMap<>();

        MasterMapper masterMapper = new MasterMapper();
        RequestMapper requestMapper = new RequestMapper(connection);

        while (rs.next()) {
            RepairRequest request = requestMapper.extractFromResultSet(rs);
//            User master = masterMapper.extractFromResultSet(rs);
//            String authorityStr = rs.getString("user_authorities.authorities");

//            master = masterMapper.makeUnique(masters, master);
            request = requestMapper.makeUnique(requests, request);
//            if (authorityStr != null) {
//                user.getAuthorities().add(Authority.valueOf(authorityStr));
//            }

//            if (!request.getMasters().contains(master) && master.getId() != 0) {
//                request.addMaster(master);
//            }
        }
        return requests;
    }

}
