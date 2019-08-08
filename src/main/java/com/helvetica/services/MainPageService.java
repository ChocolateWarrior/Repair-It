package com.helvetica.services;

import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.dao.imp.JDBCRequestDao;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class MainPageService {

    private JDBCRequestDao requestDao;

    public MainPageService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.requestDao = jdbcDaoFactory.createRequestDao();
    }

    public String getMainPage(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("user_requests", requestDao.findByUser(user.getId()));
        request.setAttribute("master_requests", requestDao.findByMaster(user.getId()));

        return "/WEB-INF/view/index.jsp";
    }

    public void updateMasterRequest(HttpServletRequest request){

        int id = Integer.parseInt(request.getParameter("master_request_id"));
        String state = request.getParameter("master_request_state");
        BigDecimal price = new BigDecimal(request.getParameter("master_request_price"));

        requestDao.updateStateAndPrice(id, RequestState.valueOf(state), price);

    }

}
