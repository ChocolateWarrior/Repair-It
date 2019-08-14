package com.helvetica.controller.command;

import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;
import com.helvetica.services.RequestService;
import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IndexCommand implements Command {

    private UserService userService;
    private RequestService requestService;

    public IndexCommand(UserService userService, RequestService requestService) {
        this.userService = userService;
        this.requestService = requestService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = userService.getByUsername((String)session.getAttribute("username"));

        request.setAttribute("user_requests", requestService.findByUser(user.getId()));
        request.setAttribute("master_requests", user.getMasterRequests());
        request.setAttribute("paid", RequestState.PAID);
        request.setAttribute("accepted", RequestState.ACCEPTED);
        request.setAttribute("completed", RequestState.COMPLETED);
        request.setAttribute("user", user);

        return "/WEB-INF/view/index.jsp";
    }
}
