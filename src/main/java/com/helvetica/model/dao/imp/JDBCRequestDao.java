package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.dao.mapper.MasterMapper;
import com.helvetica.model.dao.mapper.RequestMapper;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.lang.Integer;
import java.util.*;

public class JDBCRequestDao implements RequestDao{

    private static final String FIND_BY_ID_QUERY = "SELECT" +
            " requests.user_id AS \"requests.user_id\"," +
            " requests.id AS \"requests.id\"," +
            " requests.specification AS \"requests.specification\"," +
            " requests.description AS \"requests.description\"," +
            " requests.request_time AS \"requests.request_time\"," +
            " requests.finish_time AS \"requests.finish_time\"," +
            " requests.comment AS \"requests.comment\"," +
            " requests.rejection_message AS \"requests.rejection_message\"," +
            " requests.price AS \"requests.price\"," +
            " requests.state AS \"requests.state\"" +
            " FROM requests WHERE id =?";

    private static final String FIND_ALL_QUERY = "SELECT" +
            " requests.user_id AS \"requests.user_id\"," +
            " requests.id AS \"requests.id\"," +
            " requests.specification AS \"requests.specification\"," +
            " requests.description AS \"requests.description\"," +
            " requests.request_time AS \"requests.request_time\"," +
            " requests.finish_time AS \"requests.finish_time\"," +
            " requests.comment AS \"requests.comment\"," +
            " requests.rejection_message AS \"requests.rejection_message\"," +
            " requests.price AS \"requests.price\"," +
            " requests.state AS \"requests.state\"" +
            " FROM requests;";

    private static final String FIND_BY_USER = "SELECT " +
            " requests.id AS \"requests.id\"," +
            " requests.user_id AS \"requests.user_id\"," +
            " requests.request_time AS \"requests.request_time\"," +
            " requests.state AS \"requests.state\"," +
            " requests.rejection_message AS \"requests.rejection_message\"," +
            " requests.description AS \"requests.description\"," +
            " requests.specification AS \"requests.specification\"," +
            " requests.finish_time AS \"requests.finish_time\"," +
            " requests.price AS \"requests.price\"," +
            " requests.comment AS \"requests.comment\"" +
            " FROM requests WHERE user_id =?";

    private static final String FIND_BY_MASTER = "SELECT" +
            " requests.id AS \"requests.id\"," +
            " requests.user_id AS \"requests.user_id\"," +
            " requests.request_time AS \"requests.request_time\"," +
            " requests.state AS \"requests.state\"," +
            " requests.rejection_message AS \"requests.rejection_message\"," +
            " requests.description AS \"requests.description\"," +
            " requests.specification AS \"requests.specification\"," +
            " requests.finish_time AS \"requests.finish_time\"," +
            " requests.price AS \"requests.price\"," +
            " requests.comment AS \"requests.comment\"," +
            " masters_requests.master_id AS \"masters_requests.master_id\"," +
            " masters_requests.request_id AS \"masters_requests.request_id\"" +
            " FROM requests LEFT JOIN masters_requests ON" +
            " requests.id = masters_requests.request_id" +
            " WHERE request_id=?";

    private static final String CREATE_QUERY = "INSERT INTO requests (specification, description," +
            " user_id, request_time VALUES (? ,?, ?, ?)";

    private static final String REJECT_REQUEST_QUERY = "UPDATE requests SET state = ?," +
            " rejection_message = ? WHERE id = ?";

    private static final String UPDATE_QUERY = "UPDATE requests SET state = ?," +
            " price= ? WHERE id = ?";

    private static final String COMPLETE_REQUEST_QUERY = "UPDATE requests SET state = ?," +
            " finish_time = ? WHERE id = ?";

    private static final String ADD_REQUEST_MASTER_QUERY = "INSERT INTO masters_requests" +
            "(master_id, request_id) VALUES (?, ?)";

    private static final String SET_COMMENT_QUERY = "UPDATE requests SET comment = ?" +
            " WHERE id = ?";

    private static final String PAYMENT_QUERY = "UPDATE requests SET state = ?" +
            " WHERE id = ?";

    private static final String EXTRACT_USER_QUERY = "SELECT" +
            " requests.id AS \"requests.id\"," +
            " requests.user_id AS \"requests.user_id\"," +
            " masters_requests.master_id AS \"masters_requests.master_id\"," +
            " masters_requests.request_id AS \"masters_requests.request_id\"," +
            " users.id AS \"users.id\"," +
            " users.first_name AS \"users.first_name\"," +
            " users.last_name AS \"users.last_name\"," +
            " users.password AS \"users.password\"," +
            " users.username AS \"users.username\"," +
            " users.balance AS \"users.balance\"" +
            " FROM requests" +
            " LEFT JOIN masters_requests ON requests.id = masters_requests.request_id" +
            " LEFT JOIN users ON users.id = requests.user_id" +
            " WHERE requests.id = ?";

    private static final String EXTRACT_AUTHORITY_QUERY = "SELECT" +
            " requests.id AS \"requests.id\"," +
            " masters_requests.master_id AS \"masters_requests.master_id\"," +
            " masters_requests.request_id AS \"masters_requests.request_id\"," +
            " users.id AS \"users.id\"," +
            " users.first_name AS \"users.first_name\"," +
            " users.last_name AS \"users.last_name\"," +
            " users.password AS \"users.password\"," +
            " users.username AS \"users.username\"," +
            " users.balance AS \"users.balance\"," +
            " user_authorities.user_id AS \"user_authorities.user_id\"," +
            " user_authorities.authorities AS \"user_authorities.authorities\"" +
            " FROM requests" +
            " LEFT JOIN masters_requests ON requests.id = masters_requests.request_id" +
            " LEFT JOIN users ON masters_requests.master_id = users.id " +
            " LEFT JOIN user_authorities ON users.id = user_authorities.user_id " +
            " WHERE requests.id = ?";

    private Connection connection;

    JDBCRequestDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<RepairRequest> findById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_QUERY)){
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
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY)){
            ResultSet rs = ps.executeQuery();

            Map<Integer, RepairRequest> requests = extractFromResultSet(rs);
            resultSet = new HashSet<>(requests.values());

            return resultSet;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<RepairRequest> findByUser(int id) {

        HashSet<RepairRequest> resultSet;
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_USER)){
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

        HashSet<RepairRequest> resultSet;
        try (PreparedStatement ps = connection.prepareStatement(
                FIND_BY_MASTER
        )){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Integer, RepairRequest> requests = extractFromResultSet(rs);
            resultSet = new HashSet<>(requests.values());

            return resultSet;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void create(RepairRequest entity) {

        try(PreparedStatement ps = connection.prepareStatement
                (CREATE_QUERY)){
            ps.setString(1 , entity.getSpecification());
            ps.setString(2 , entity.getDescription());
            ps.setInt(3 , entity.getUser().getId());
            ps.setTimestamp(4 , convertToTimestamp(entity.getRequestTime()));
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void rejectRequest(int id, String message){
        try (PreparedStatement ps =
                     connection.prepareStatement(REJECT_REQUEST_QUERY)) {
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
                     connection.prepareStatement(UPDATE_QUERY)) {
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
                     connection.prepareStatement(COMPLETE_REQUEST_QUERY)) {
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
                    connection.prepareStatement(ADD_REQUEST_MASTER_QUERY)) {
            ps.setInt(1, master.getId());
            ps.setInt(2, request.getId());

            ps.executeUpdate();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void setRequestComment(int id, String comment){
        try (PreparedStatement ps =
                     connection.prepareStatement(SET_COMMENT_QUERY)) {
            ps.setString(1, comment);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePayment(int id){
        try (PreparedStatement ps =
                     connection.prepareStatement(PAYMENT_QUERY)) {
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

    private Map<Integer, RepairRequest> extractFromResultSet(ResultSet rss) throws SQLException {
        Map<Integer, RepairRequest> requests = new HashMap<>();
        Map<Integer, User> users = new HashMap<>();

        MasterMapper userMapper = new MasterMapper();
        RequestMapper requestMapper = new RequestMapper(connection);

        while (rss.next()) {
            RepairRequest request = requestMapper.extractFromResultSet(rss);
            requestMapper.makeUnique(requests, request);
        }

        for(RepairRequest e : requests.values()){

            try (PreparedStatement ps = connection.prepareStatement(
                    EXTRACT_AUTHORITY_QUERY)) {
                ps.setInt(1, e.getId());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    User user = userMapper.extractFromResultSet(rs);
                    user = userMapper.makeUnique(users, user);

                    if (Objects.nonNull(rs.getString("user_authorities.authorities"))) {
                        Role authority = Role
                                .valueOf(rs.getString("user_authorities.authorities"));
                        user.getAuthorities().add(authority);
                    }

                    if ((user.getId() != 0) && !e.getMasters().contains(user)) {
                        e.addMaster(user);
                    }
                }
            }

            try (PreparedStatement pss = connection.prepareStatement(
                    EXTRACT_USER_QUERY)) {
                pss.setInt(1, e.getId());
                ResultSet rs = pss.executeQuery();

                while (rs.next()) {
                    if (rs.getInt("requests.id") != 0) {
                        User user = userMapper.extractFromResultSet(rs);
                        user = userMapper.makeUnique(users, user);

                        if (Objects.isNull(e.getUser())) {
                            e.setUser(user);
                        }
                    }
                }
            }

        }

        return requests;
    }

}
