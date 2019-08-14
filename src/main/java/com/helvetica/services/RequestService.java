package com.helvetica.services;

import com.helvetica.exception.DeleteDependentException;
import com.helvetica.model.dao.RequestDao;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;

import java.math.BigDecimal;
import java.util.Set;

public class RequestService {

    private JDBCDaoFactory jdbcDaoFactory;

    public RequestService() {
        this.jdbcDaoFactory = new JDBCDaoFactory();

    }

    public void addRequest(RepairRequest repairRequest){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.create(repairRequest);
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

    public Set<RepairRequest> displayRequests(){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        return requestDao.findAll();
    }

    public void deleteRequest(int id) {
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        try {
            requestDao.delete(id);
        } catch (DeleteDependentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public RepairRequest findById(int id){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        return requestDao.findById(id).get();
    }



    public void rejectRequest(int id, String message){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.rejectRequest(id, message);
    }

    public void editRequest(String masterUsername, RepairRequest requestToEdit){
        JDBCUserDao userDao = jdbcDaoFactory.createUserDao();
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();

        userDao.findByUsername(masterUsername).ifPresent(e ->
                requestDao.addRequestMaster(requestToEdit, e));

    }

    public void updateMasterRequest(int id, String state, BigDecimal price){
        JDBCRequestDao requestDao = jdbcDaoFactory.createRequestDao();
        requestDao.updateStateAndPrice(id, RequestState.valueOf(state), price);
    }

}
