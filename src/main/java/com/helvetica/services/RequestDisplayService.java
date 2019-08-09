package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;


public class RequestDisplayService {

    private JDBCRequestDao requestDao;
    private JDBCUserDao userDao;
    public static final Logger log = LogManager.getLogger();


    public RequestDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
        this.userDao=jdbcDaoFactory.createUserDao();
    }

    public HashSet<RepairRequest> displayRequests(){
        return requestDao.findAll();
    }

    public RepairRequest findById(int id){
        return requestDao.findById(id).get();
    }

    public HashSet<User> findMastersBySpecification(String specification){
        return userDao.findMastersBySpecification(Specification.valueOf(specification));
    }

    public void rejectRequest(int id, String message){
        requestDao.rejectRequest(id, message);
    }

    public void editRequest(String masterUsername, RepairRequest requestToEdit){

        log.info("Masters username: " + masterUsername);
        userDao.findByUsername(masterUsername).ifPresent(e ->
            requestDao.addRequestMaster(requestToEdit, e));

    }

}
