package com.helvetica.services;

import com.helvetica.exception.DeleteDependentException;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class RequestDisplayService {

    private JDBCRequestDao requestDao;
    private JDBCUserDao userDao;


    public RequestDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
        this.userDao=jdbcDaoFactory.createUserDao();
    }

    public Set<RepairRequest> displayRequests(){
        return requestDao.findAll();
    }

    public void deleteRequest(int id) {
        try {
            requestDao.delete(id);
        } catch (DeleteDependentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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

        userDao.findByUsername(masterUsername).ifPresent(e ->
                requestDao.addRequestMaster(requestToEdit, e));

    }

    public void updateMasterRequest(int id, String state, BigDecimal price){
        requestDao.updateStateAndPrice(id, RequestState.valueOf(state), price);
    }

}
