package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class BalanceService {

    private JDBCUserDao userDao;

    public BalanceService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public void replenishBalance(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        User userToEdit = userDao.findById(id).get();
        request.setAttribute("user", userToEdit);
        request.setAttribute("balance", userToEdit.getBalance());
        request.setAttribute("replenish_message", "Balance successfully replenished!");

        Optional<String> sumOpt = Optional.ofNullable(request.getParameter("sum"));
        BigDecimal sum = sumOpt.isEmpty() ? new BigDecimal(0) : new BigDecimal(sumOpt.get());
        userDao.replenishBalance(id, sum);

    }

}
