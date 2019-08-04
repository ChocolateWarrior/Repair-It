package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MainPageService {

    private JDBCRequestDao requestDao;

    public MainPageService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
    }

    public String getMainPage(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("user_requests", requestDao.findByUser(user.getId()));

        return "/WEB-INF/view/index.jsp";
    }
}
