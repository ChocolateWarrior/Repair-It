package com.helvetica.services;

import com.helvetica.exception.DeleteDependentException;
import com.helvetica.model.dao.DaoFactory;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.dao.imp.TransactionalFactory;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class UserService {

    private JDBCDaoFactory jdbcDaoFactory;

    public UserService() {
        this.jdbcDaoFactory = new JDBCDaoFactory();

    }

    public HashSet<User> findAll(){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        return userDao.findAll();
    }

    public User findById(int id){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        return userDao.findById(id).get();
    }

    public User getByUsername(String username){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        return userDao.findByUsername(username).get();
    }

    public void deleteUser(int id){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        try {
            userDao.delete(id);
        } catch (DeleteDependentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    public void editUser(User userToEdit){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        userDao.update(userToEdit);
    }

    public void replenishBalance(int id, BigDecimal sum){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        userDao.replenishBalance(id, sum);
    }

    public HashSet<User> findMastersBySpecification(String specification){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        return userDao.findMastersBySpecification(Specification.valueOf(specification));
    }

    public void registerUser(User user){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        userDao.create(user);
    }

    public void registerMaster(User user){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        userDao.createMaster(user);
    }

    public boolean isDuplicateUsername (String username){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        return userDao.isDuplicateUsername(username);
    }

    public void payForRequest(int user_id, int request_id, BigDecimal price) {

        TransactionalFactory transactionalFactory = new TransactionalFactory();
        JDBCRequestDao requestDao = transactionalFactory.createRequestDao();
        JDBCUserDao userDao =transactionalFactory.createUserDao();

        try {
            transactionalFactory.begin();

            requestDao.updatePayment(request_id);
            userDao.subtractBalance(user_id, price);

            transactionalFactory.commit();
        } catch (SQLException e) {
            try {
                transactionalFactory.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            transactionalFactory.close();
        }
    }

}
