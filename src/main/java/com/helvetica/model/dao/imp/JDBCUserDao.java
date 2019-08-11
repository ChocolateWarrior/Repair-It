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
    private static final Logger log = LogManager.getLogger();
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("database");

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public Optional<User> findById(int id) {

        try (PreparedStatement ps = connection.prepareStatement("SELECT" +
                " users.id AS \"users.id\"," +
                " users.first_name AS \"users.first_name\"," +
                " users.last_name AS \"users.last_name\"," +
                " users.password AS \"users.password\"," +
                " users.balance AS \"users.balance\"," +
                " users.username AS \"users.username\"" +
                " FROM users\n" +
                " WHERE users.id = ?")){
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

        try (PreparedStatement ps = connection.prepareStatement("SELECT" +
                " users.id AS \"users.id\"," +
                " users.first_name AS \"users.first_name\"," +
                " users.last_name AS \"users.last_name\"," +
                " users.balance AS \"users.balance\"," +
                " users.password AS \"users.password\"," +
                " users.username AS \"users.username\"" +
                " FROM users")){

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
                "SELECT" +
                        " users.id AS \"users.id\"," +
                        " users.first_name AS \"users.first_name\"," +
                        " users.last_name AS \"users.last_name\"," +
                        " users.password AS \"users.password\"," +
                        " users.balance AS \"users.balance\"," +
                        " users.username AS \"users.username\"" +
                        " FROM users WHERE username=? AND password=?")){
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
                "SELECT" +
                        " users.id AS \"users.id\"," +
                        " users.first_name AS \"users.first_name\"," +
                        " users.last_name AS \"users.last_name\"," +
                        " users.password AS \"users.password\"," +
                        " users.balance AS \"users.balance\"," +
                        " users.username AS \"users.username\"" +
                        " FROM users WHERE username =?")){
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
                "SELECT " +
                        " users.id AS \"users.id\"," +
                        " users.first_name AS \"users.first_name\"," +
                        " users.last_name AS \"users.last_name\"," +
                        " users.password AS \"users.password\"," +
                        " users.balance AS \"users.balance\"," +
                        " users.username AS \"users.username\"," +
                        " master_specifications.specifications AS \"master_specifications.specifications\"," +
                        " master_specifications.user_id AS \"master_specifications.user_id\"" +
                        " FROM users" +
                        " LEFT JOIN master_specifications ON users.id=master_specifications.user_id WHERE specifications=?")){

            ps.setString(1, specification.name());
            ResultSet rs = ps.executeQuery();

            System.out.println("HERE find master by specs");

            Map<Integer, User> users = extractFromResultSet(rs);
            result = new HashSet<>(users.values());

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Set<User> findMastersByRequest(int id) {

        log.info("trying to find by request with id: " + id);
        HashSet<User> resultSet;
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT " +
                        " users.id AS \"users.id\"," +
                        " users.first_name AS \"users.first_name\"," +
                        " users.last_name AS \"users.last_name\"," +
                        " users.balance AS \"users.balance\"," +
                        " users.password AS \"users.password\"," +
                        " users.username AS \"users.username\"," +
                        " masters_requests.master_id AS \"masters_requests.master_id\"," +
                        " masters_requests.request_id AS \"masters_requests.request_id\"" +
                        " FROM users LEFT JOIN masters_requests ON" +
                        " users.id = masters_requests.master_id" +
                        " WHERE master_id=?"
        )){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            Map<Integer, User> users = extractFromResultSet(rs);
            resultSet = new HashSet<>(users.values());

            return resultSet;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void create(User entity) {

        log.info("executing user creation...");

        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO users (first_name, last_name, username, password)" +
                    " VALUES (? ,?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1 , entity.getFirstName());
            ps.setString(2 , entity.getLastName());
            ps.setString(3 , entity.getUsername());
            ps.setString(4 , entity.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
            }

            try (PreparedStatement aps = connection.prepareStatement(
                    "INSERT INTO user_authorities (user_id, authorities) VALUES (?, ?)")) {
                aps.setInt(1, entity.getId());
                aps.setString(2, Role.USER.name());
                aps.executeUpdate();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void createMaster(User entity) {

        log.info("executing master creation...");

        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO users (first_name, last_name, username, password)" +
                        " VALUES (? ,?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1 , entity.getFirstName());
            ps.setString(2 , entity.getLastName());
            ps.setString(3 , entity.getUsername());
            ps.setString(4 , entity.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                entity.setId(id);
                createMasterSpecifications(entity, id);
            }

            try (PreparedStatement aps = connection.prepareStatement(
                    "INSERT INTO user_authorities (user_id, authorities) VALUES (?, ?),(?, ?)")) {
                aps.setInt(1, entity.getId());
                aps.setString(2, Role.MASTER.name());
                aps.setInt(3,entity.getId());
                aps.setString(4, Role.USER.name());
                aps.executeUpdate();
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    @Override
    public void update(User entity) {
        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE users SET" +
                             " first_name = ?," +
                             " last_name = ?," +
                             " username = ?," +
                             " password = ?" +
                             " WHERE id = ?")) {
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getUsername());
            ps.setString(4, entity.getPassword());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();

            try (PreparedStatement authorityDeletePS = connection.prepareStatement(
                    "DELETE FROM user_authorities WHERE user_id = ?")) {
                authorityDeletePS.setInt(1, entity.getId());
                authorityDeletePS.executeUpdate();
            }
            try (PreparedStatement authorityInsertPS = connection.prepareStatement(
                    "INSERT INTO user_authorities VALUES (?, ?)")) {
                for (Role authority : entity.getAuthorities()) {
                    authorityInsertPS.setInt(1, entity.getId());
                    authorityInsertPS.setString(2, authority.name());
                    authorityInsertPS.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replenishBalance(int id, BigDecimal sum){

        try (PreparedStatement ps =
                     connection.prepareStatement("UPDATE users SET" +
                             " balance = balance + ?" +
                             " WHERE id = ?")) {
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
                             " WHERE id = ?")) {
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

    private Map<Integer, User> extractFromResultSet(ResultSet rss) throws SQLException {

        MasterMapper userMapper = new MasterMapper();
        RequestMapper requestMapper = new RequestMapper(connection);

        System.out.println("HERE extr 1");


        Map<Integer, User> users = new HashMap<>();
        Map<Integer, RepairRequest> requests = new HashMap<>();

        while (rss.next()) {
            User user = userMapper.extractFromResultSet(rss);
            System.out.println(user);
            userMapper.makeUnique(users, user);
        }

        for(User e : users.values()){

            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT" +
                            " users.id AS \"users.id\"," +
                            " user_authorities.user_id AS \"user_authorities.user_id\"," +
                            " user_authorities.authorities AS \"user_authorities.authorities\"" +
                            " FROM users LEFT JOIN user_authorities" +
                            " ON users.id=user_authorities.user_id" +
                            " WHERE users.id=?"
            )) {

                ps.setInt(1, e.getId());
                ResultSet rsa = ps.executeQuery();

                while(rsa.next()){
                    String authority = rsa.getString("user_authorities.authorities");
                    e.addAuthority(Role.valueOf(authority));
                }

            }

            users.values().forEach(System.out::println);

            System.out.println("HERE extr 2");

            try (PreparedStatement pss = connection.prepareStatement(
                    "SELECT" +
                            " users.id AS \"users.id\"," +
                            " masters_requests.master_id AS \"masters_requests.master_id\"," +
                            " masters_requests.request_id AS \"masters_requests.request_id\"," +
                            " master_specifications.specifications AS \"master_specifications.specifications\"," +
                            " master_specifications.user_id AS \"master_specifications.user_id\"," +
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
                            " FROM users" +
                            " LEFT JOIN masters_requests ON users.id = masters_requests.master_id" +
                            " LEFT JOIN master_specifications ON users.id = master_specifications.user_id" +
                            " LEFT JOIN requests ON requests.user_id = users.id" +
                            " WHERE users.id = ?")) {
                pss.setInt(1, e.getId());
                ResultSet rs = pss.executeQuery();

                while (rs.next()) {
                      if (rs.getInt("requests.id") != 0) {
                          RepairRequest request = requestMapper.extractFromResultSet(rs);
                          request = requestMapper.makeUnique(requests, request);

                          System.out.println(request);

                          if (Objects.nonNull(request) && !e.getUserRequests().contains(request)) {
                              e.addUserRequest(request);
                          }
                      }
//                    RepairRequest request = requestMapper.extractFromResultSet(rs);
//                    request = requestMapper.makeUnique(requests, request);
//
//                    System.out.println(request);
//
//                    if (Objects.nonNull(request) && !e.getUserRequests().contains(request)) {
//                        e.addUserRequest(request);
//                    }
                }

                users.values().forEach(System.out::println);

            }

            try (PreparedStatement requestPS = connection.prepareStatement("SELECT " +
                    " users.id AS \"users.id\"," +
                    " masters_requests.request_id AS \"masters_requests.request_id\"," +
                    " masters_requests.master_id AS \"masters_requests.master_id\"," +
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
                    " FROM users" +
                    " LEFT JOIN masters_requests ON users.id = masters_requests.master_id" +
                    " LEFT JOIN requests ON masters_requests.request_id = requests.id" +
                    " WHERE users.id = ?")) {
                requestPS.setLong(1, e.getId());
                ResultSet requestRS = requestPS.executeQuery();

                while (requestRS.next()) {
                    if(requestRS.getInt("requests.id") != 0) {
                        RepairRequest request = requestMapper.extractFromResultSet(requestRS);

                        request = requestMapper.makeUnique(requests, request);

                        if (Objects.nonNull(request) && !e.getMasterRequests().contains(request)) {
                            e.addMasterRequest(request);
//                        request.addMaster(e);
                        }
                    }
                }
            }
        }

        users.values().forEach(System.out::println);

        return users;
    }



}
