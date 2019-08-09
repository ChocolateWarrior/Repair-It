package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashSet;

public class MainPageService {

    private JDBCRequestDao requestDao;
    private JDBCUserDao userDao;

    public MainPageService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
        this.userDao = jdbcDaoFactory.createUserDao();

    }

    public HashSet<RepairRequest> findByUser(int id){
        return requestDao.findByUser(id);
    }

    public HashSet<RepairRequest> findByMaster(int id){
        return requestDao.findByMaster(id);
    }


    public void completeRequest(HttpServletRequest request){

        int id = Integer.parseInt(request.getParameter("master_request_id"));
        requestDao.completeRequest(id);

    }

    public void updateMasterRequest(int id, String state, BigDecimal price){
        requestDao.updateStateAndPrice(id, RequestState.valueOf(state), price);
    }

    public void payForRequest(int user_id, int request_id, BigDecimal price){
        requestDao.updatePayment(request_id);
        userDao.subtractBalance(user_id, price);
    }

    public void leaveComment(int id, String comment){
        requestDao.setRequestComment(id, comment);
    }

}
