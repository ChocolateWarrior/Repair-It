package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.UserDao;
import com.helvetica.model.dao.mapper.MasterMapper;
import com.helvetica.model.dao.mapper.RequestMapper;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {

    private Connection connection;
    public static final Logger log = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findById(int id) {

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?")){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            Map<Integer, User> users = extractFromResultSet(rs);
            return Optional.ofNullable(users.get(id));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashSet<User> findAll() {
        HashSet<User> result;

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users;")){

            ResultSet rs = ps.executeQuery();
            Map<Integer, User> users = extractFromResultSet(rs);
            result = new HashSet<>(users.values());

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<User> findByUsernameAndPassword(String username, String password){

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE username =? AND password=?")){
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            Map<Integer, User> users = extractFromResultSet(rs);
            return users.values().stream().findFirst();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<User> findByUsername(String username){
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE username =?")){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            Map<Integer, User> users = extractFromResultSet(rs);
            return users.values().stream().findFirst();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public HashSet<User> findMastersBySpecification(Specification specification){
        HashSet<User> result;
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM users WHERE id IN (SELECT user_id FROM master_specifications WHERE specifications=?)")){
            ps.setString(1, specification.name());
            ResultSet rs = ps.executeQuery();
            Map<Integer, User> users = extractFromResultSet(rs);
            result = new HashSet<>(users.values());

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void create(User entity) {

        log.info("executing user creation...");

        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO users (first_name, last_name, username, password, authority )" +
                    " VALUES (? ,?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1 , entity.getFirstName());
            ps.setString(2 , entity.getLastName());
            ps.setString(3 , entity.getUsername());
            ps.setString(4 , entity.getPassword());
            ps.setString(5, String.valueOf(Role.USER));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void createMaster(User entity) {

        log.info("executing master creation...");

        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO users (first_name, last_name, username, password, authority )" +
                        " VALUES (? ,?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1 , entity.getFirstName());
            ps.setString(2 , entity.getLastName());
            ps.setString(3 , entity.getUsername());
            ps.setString(4 , entity.getPassword());
            ps.setString(5, String.valueOf(Role.MASTER));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
                createMasterSpecifications(entity, id);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void createMasterSpecifications(User entity, int id){

        entity.getSpecifications().forEach(element -> {
                    try (PreparedStatement ms = connection.prepareStatement(
                            "INSERT INTO master_specifications (user_id, specifications) " +
                                    "VALUES(?, ?)")) {

                        ms.setInt(1, id);
                        ms.setString(2, element.name());
                        ms.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public void update(User entity) {
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE users SET" +
                             " first_name = ?," +
                             " last_name = ?," +
                             " username = ?," +
                             " password = ?" +
                             " where id = ?")) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getUsername());
            ps.setString(4, entity.getPassword());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replenishBalance(int id, BigDecimal sum){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE users SET" +
                             " balance = balance + ?" +
                             " where id = ?")) {
            ps.setBigDecimal(1, sum);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void subtractBalance(int id, BigDecimal price){
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE users SET" +
                             " balance = balance - ?" +
                             " where id = ?")) {
            ps.setBigDecimal(1, price);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement ps = connection.prepareStatement
                ("DELETE FROM users WHERE id = ?")){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {

    }

    private Map<Integer, User> extractFromResultSet(ResultSet rs) throws SQLException {

        MasterMapper masterMapper = new MasterMapper();
        RequestMapper requestMapper = new RequestMapper(connection);

        Map<Integer, User> masters = new HashMap<>();
        Map<Integer, RepairRequest> requests = new HashMap<>();


        while (rs.next()) {
            User master = masterMapper.extractFromResultSet(rs);
//            RepairRequest request = requestMapper.extractFromResultSet(rs);
//            Role authority = Role.USER;
//            System.out.println(request);
            master = masterMapper.makeUnique(masters, master);
//            request = requestMapper.makeUnique(requests, request);
//            System.out.println(request);

//            master.addMasterRequest(request);
//            master.getAuthority().add(authority);
        }
        return masters;
    }

}
