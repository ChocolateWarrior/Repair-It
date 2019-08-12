package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.User;

import java.math.BigDecimal;

public class BalanceService {

    private JDBCUserDao userDao;

    public BalanceService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public User findById(int id){
        return userDao.findById(id).get();
    }

    public void replenishBalance(int id, BigDecimal sum){
        userDao.replenishBalance(id, sum);
    }

}
