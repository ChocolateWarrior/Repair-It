package com.helvetica.services;

import com.helvetica.model.dao.UserDao;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserDisplayService {

    private UserDao userDao;

    public UserDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public void displayUsers(HttpServletRequest request){
        request.setAttribute("users", userDao.findAll());
        request.getRequestDispatcher("/WEB-INF/user_display.jsp");
    }

    public User getByUsernameAndPassword(String username, String password){
        return userDao.findByUsernameAndPassword(username, password);
    }

}
