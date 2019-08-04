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

    public String addRequest(HttpServletRequest request){

        HttpSession session = request.getSession();

        String specification = request.getParameter("specification");
        String description = request.getParameter("description");
        User user = (User)session.getAttribute("user");

        if (!(Objects.nonNull(specification) &&
                Objects.nonNull(description) &&
                Objects.nonNull(user))) {
            return "/WEB-INF/view/request.jsp";
        }

        RepairRequest repairRequest = new RepairRequest(user,
                description,
                LocalDateTime.now(),
                Specification.valueOf(specification.toUpperCase()));

        requestDao.create(repairRequest);

        return "/WEB-INF/view/request.jsp";
    }

}
