package com.helvetica.services;

import com.helvetica.model.dao.DaoFactory;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.dao.imp.TransactionalFactory;
import com.helvetica.model.entity.RepairRequest;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class MainPageService {

    private JDBCDaoFactory jdbcDaoFactory;

    public MainPageService() {
        this.jdbcDaoFactory = new JDBCDaoFactory();

    }

    public Set<RepairRequest> findByUser(int id){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        return requestDao.findByUser(id);
    }

    public void completeRequest(int id){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.completeRequest(id);
    }

    public void leaveComment(int id, String comment){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.setRequestComment(id, comment);
    }

    public void payForRequest(int user_id, int request_id, BigDecimal price) {

        TransactionalFactory transactionalFactory = new TransactionalFactory();
        JDBCRequestDao requestDao = transactionalFactory.createRequestDao();
        JDBCUserDao userDao =transactionalFactory.createUserDao();

        try {
            transactionalFactory.begin();

            requestDao.updatePayment(request_id);
            userDao.subtractBalance(user_id, price);

            transactionalFactory.commit();
        } catch (SQLException e) {
            try {
                transactionalFactory.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            transactionalFactory.close();
        }
    }

}
