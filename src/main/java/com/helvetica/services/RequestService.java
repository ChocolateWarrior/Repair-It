package com.helvetica.services;

import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

public class RequestService {

    private RequestDao requestDao;

    public RequestService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
    }

    public void addRequest(RepairRequest repairRequest){
        requestDao.create(repairRequest);
    }

}
