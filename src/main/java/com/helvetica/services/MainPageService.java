package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.dao.imp.JDBCUserDao;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class MainPageService {

    private JDBCRequestDao requestDao;
    private JDBCUserDao userDao;

    public MainPageService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
        this.userDao = jdbcDaoFactory.createUserDao();

    }

    public String getMainPage(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("user_requests", requestDao.findByUser(user.getId()));
        request.setAttribute("master_requests", requestDao.findByMaster(user.getId()));
        request.setAttribute("user", user);

        return "/WEB-INF/view/index.jsp";
    }

    public void updateMasterRequest(HttpServletRequest request){

        int id = Integer.parseInt(request.getParameter("master_request_id"));
        requestDao.completeRequest(id);

    }

    public void completeRequest(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("master_request_id"));
        String state = request.getParameter("master_request_state");
        BigDecimal price = new BigDecimal(request.getParameter("master_request_price"));

        requestDao.updateStateAndPrice(id, RequestState.valueOf(state), price);
    }

    public void payForRequest(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("balance", user.getBalance());
        request.setAttribute("paid", RequestState.PAID.name());
        request.setAttribute("completed", RequestState.COMPLETED.name());

        int id = Integer.parseInt(request.getParameter("request_payment_id"));
        BigDecimal price = new BigDecimal(request.getParameter("request_payment_price"));

        System.out.println(id + " " + price);

        requestDao.updatePayment(id);
        userDao.subtractBalance(user.getId(), price);

    }

    public void leaveComment(HttpServletRequest request){

        request.setAttribute("completed", RequestState.COMPLETED.name());
        int id = Integer.parseInt(request.getParameter("request_comment_id"));
        String comment = request.getParameter("comment");

        requestDao.setRequestComment(id, comment);

    }



}
