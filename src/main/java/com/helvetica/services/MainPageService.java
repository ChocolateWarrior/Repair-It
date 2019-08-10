package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class MainPageService {

    private JDBCRequestDao requestDao;
    private JDBCUserDao userDao;

    public MainPageService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
        this.userDao = jdbcDaoFactory.createUserDao();

    }

    public Set<RepairRequest> findByUser(int id){
        return requestDao.findByUser(id);
    }

    public Set<RepairRequest> findByMaster(int id){
        return requestDao.findByMaster(id);
    }


    public void completeRequest(int id){
        requestDao.completeRequest(id);
    }

    public void updateMasterRequest(int id, String state, BigDecimal price){
        requestDao.updateStateAndPrice(id, RequestState.valueOf(state), price);
    }

    public void payForRequest(int user_id, int request_id, BigDecimal price){

        Connection connection = userDao.getConnection();
        try {
            connection.setAutoCommit(false);

            requestDao.updatePayment(request_id);
            userDao.subtractBalance(user_id, price);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveComment(int id, String comment){
        requestDao.setRequestComment(id, comment);
    }

}
