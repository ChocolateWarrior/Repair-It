package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;

import javax.servlet.http.HttpServletRequest;

public class RequestDisplayService {

    private JDBCRequestDao requestDao;

    public RequestDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
    }

    public String displayRequests(HttpServletRequest request){
        request.setAttribute("requests", requestDao.findAll());
        return "/WEB-INF/view/request_display.jsp";
    }

    public void rejectRequest(int id, String message){
        requestDao.rejectRequest(id, message);
    }

}
