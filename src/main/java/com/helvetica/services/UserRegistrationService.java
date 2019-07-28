package com.helvetica.services;

import com.helvetica.model.dao.UserDao;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserRegistrationService {

    private UserDao userDao;

    public UserRegistrationService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public void registerUser(User user){

        userDao.create(user);

    }

}
