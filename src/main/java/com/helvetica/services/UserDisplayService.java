package com.helvetica.services;

import com.helvetica.exception.DeleteDependentException;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.User;

import java.util.HashSet;

public class UserDisplayService {

    private JDBCUserDao userDao;

    public UserDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public HashSet<User> findAll(){
        return userDao.findAll();
    }

    public User findById(int id){
        return userDao.findById(id).get();
    }

    public User getByUsername(String username){
        return userDao.findByUsername(username).get();
    }

    public void deleteUser(int id){
        try {
            userDao.delete(id);
        } catch (DeleteDependentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }

    public void editUser(User userToEdit){
        userDao.update(userToEdit);
    }

}
