package com.helvetica.controller.command;

import com.helvetica.model.entity.RequestState;
import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;

public class RequestDisplayCommand implements Command {

    private RequestDisplayService requestDisplayService;

    public RequestDisplayCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("requests", requestDisplayService.displayRequests());
        request.setAttribute("rejected", RequestState.REJECTED);
        request.setAttribute("completed", RequestState.COMPLETED);
        request.setAttribute("accepted", RequestState.ACCEPTED);
        request.setAttribute("paid", RequestState.PAID);

        return "/WEB-INF/view/request_display.jsp";
    }
}
