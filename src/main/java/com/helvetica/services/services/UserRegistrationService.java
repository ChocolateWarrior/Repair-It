package com.helvetica.services.services;

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

    public void registerUser(HttpServletRequest request){

        final String firstName = request.getParameter("first_name");
        final String lastName = request.getParameter("last_name");
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        final User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);

        userDao.create(user);

    }

}
