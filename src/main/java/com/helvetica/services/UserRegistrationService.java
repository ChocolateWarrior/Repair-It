package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.User;


public class UserRegistrationService {

    private JDBCUserDao userDao;

    public UserRegistrationService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public void registerUser(User user){
        userDao.create(user);
    }

    public void registerMaster(User user){
        userDao.createMaster(user);
    }

    public boolean isDuplicateUsername (String username){
        return userDao.isDuplicateUsername(username);
    }

}
