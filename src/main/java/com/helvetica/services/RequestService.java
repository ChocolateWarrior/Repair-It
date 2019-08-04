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

        System.out.println("OK(3)");



        HttpSession session = request.getSession();

        String specification = request.getParameter("specification");
        String description = request.getParameter("description");
        User user = (User)session.getAttribute("user");

        if (!(Objects.nonNull(specification) &&
                Objects.nonNull(description) &&
                Objects.nonNull(user))) {
            System.out.println("NULL: " + specification + " " + description + " " + user);
            return "/WEB-INF/view/request.jsp";
        }

        System.out.println("NONNULL: " + specification + " " + description + " " + user);

        RepairRequest repairRequest = new RepairRequest(user,description,LocalDateTime.now(), Specification.valueOf(specification.toUpperCase()));
//        repairRequest.setDescription(description);
//        repairRequest.setSpecification(Specification.valueOf(specification.toUpperCase()));
//        repairRequest.setUser(user);
//        repairRequest.setRequestTime(LocalDateTime.now());

        System.out.println("FUCK");
//        System.out.println(repairRequest);

        requestDao.create(repairRequest);

        return "/WEB-INF/view/request.jsp";
    }

}
