package com.helvetica.services;

import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.dao.imp.TransactionalFactory;
import com.helvetica.model.entity.RepairRequest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Set;

public class MainPageService {

    private TransactionalFactory jdbcDaoFactory;

    public MainPageService() {
        this.jdbcDaoFactory = new TransactionalFactory();

    }

    public Set<RepairRequest> findByUser(int id){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        return requestDao.findByUser(id);
    }

    public Set<RepairRequest> findByMaster(int id){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        return requestDao.findByMaster(id);
    }


    public void completeRequest(int id){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.completeRequest(id);
    }

    public void payForRequest(int user_id, int request_id, BigDecimal price){

        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();

        try {
            jdbcDaoFactory.begin();

            requestDao.updatePayment(request_id);
            userDao.subtractBalance(user_id, price);

            jdbcDaoFactory.commit();
        } catch (SQLException e) {
            try {
                jdbcDaoFactory.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            try {
                jdbcDaoFactory.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveComment(int id, String comment){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.setRequestComment(id, comment);
    }

}
