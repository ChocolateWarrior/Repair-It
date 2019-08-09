package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.User;

import java.util.HashSet;
import java.util.Optional;

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

    public User getByUsernameAndPassword(String username, String password){
        return userDao.findByUsernameAndPassword(username, password).get();
    }

    public void deleteUser(int id) {
        userDao.delete(id);
    }

    public void editUser(User userToEdit){
        userDao.update(userToEdit);
    }

}
