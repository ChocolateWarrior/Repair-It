package com.helvetica.model.dao.imp;

import com.helvetica.model.dao.UserDao;
import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao {

    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findById(int id) {
        User result = new User();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM users WHERE id = \'" + id + "\';");
            while ( rs.next() ){
                result = extractFromResultSet(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static User extractFromResultSet(ResultSet rs)
            throws SQLException {
        User result = new User();

        result.setId(rs.getInt("id"));
        result.setFirstName(rs.getString("first_name"));
        result.setLastName(rs.getString("last_name"));
        result.setUsername(rs.getString("username"));
        result.setPassword(rs.getString("password"));
        result.setAuthority(Role.USER);

        return result;
    }

    @Override
    public HashSet<User> findAll() {
        HashSet<User> resultList = new HashSet<>();
        Map<Integer,User> users = new HashMap<>();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM users;");
            while ( rs.next() ){
                User user = extractFromResultSet(rs);
                user = makeUniqueUser( users, user);
                resultList.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    private User makeUniqueUser(Map<Integer, User> users,  User user) {

//        users.putIfAbsent(user.getId(), user);

        if(!users.containsKey(user.getId())){
            users.put(user.getId(), user);
        }

        return users.get(user.getId());
    }

    public User findByUsernameAndPassword(String usernameIn, String passwordIn){
        User result = new User();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM users WHERE username = \'" + usernameIn +
                            "\' AND password=\'" + passwordIn + "\';");
            while ( rs.next() ){
                result = extractFromResultSet(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public User findByUsername(String username){
        User result = new User();
        try (Statement ps = connection.createStatement()){
            ResultSet rs = ps.executeQuery(
                    "SELECT * FROM users WHERE username = \'" + username + "\';");
            while ( rs.next() ){
                result = extractFromResultSet(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    @Override
    public void create(User entity) {
        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO users (first_name, last_name, username, password, authority )" +
                    " VALUES (? ,?, ?, ?, ?)")){
            ps.setString(1 , entity.getFirstName());
            ps.setString(2 , entity.getLastName());
            ps.setString(3 , entity.getUsername());
            ps.setString(4 , entity.getPassword());
            ps.setString(5, String.valueOf(Role.USER));
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void createMaster(User entity) {
        try(PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO users (first_name, last_name, username, password, authority )" +
                        " VALUES (? ,?, ?, ?, ?)")){
            ps.setString(1 , entity.getFirstName());
            ps.setString(2 , entity.getLastName());
            ps.setString(3 , entity.getUsername());
            ps.setString(4 , entity.getPassword());
            ps.setString(5, String.valueOf(Role.MASTER));
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        entity.getSpecifications().forEach(element -> {
                    try (PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO master_specifications (user_id, specifications) " +
                                    "VALUES(?, ?)")) {
                        ps.setInt(1, entity.getId());
                        ps.setString(2, element.name());
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
}
