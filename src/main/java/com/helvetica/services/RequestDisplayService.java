package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;

import javax.servlet.http.HttpServletRequest;

public class RequestDisplayService {

    private JDBCRequestDao requestDao;
    private JDBCUserDao userDao;

    public RequestDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
        this.userDao=jdbcDaoFactory.createUserDao();
    }

    public String displayRequests(HttpServletRequest request){
        request.setAttribute("requests", requestDao.findAll());
        return "/WEB-INF/view/request_display.jsp";
    }

    public void rejectRequest(int id, String message){
        requestDao.rejectRequest(id, message);
    }

    public void editRequest(HttpServletRequest request){

        int id = Integer.parseInt(request.getParameter("id"));

        RepairRequest requestToEdit = requestDao.findById(id);
        String masterUsername = request.getParameter("master");
        requestDao.addRequestMaster(requestToEdit, userDao.findByUsername(masterUsername));

    }


}
