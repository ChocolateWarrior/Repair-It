package com.helvetica.controller.command;

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
        return "/WEB-INF/view/request_display.jsp";
    }
}
