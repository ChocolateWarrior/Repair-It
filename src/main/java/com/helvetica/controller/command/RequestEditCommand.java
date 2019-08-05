package com.helvetica.controller.command;

import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;

public class RequestEditCommand implements Command {

    private RequestDisplayService requestDisplayService;

    public RequestEditCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        requestDisplayService.editRequest(request);
        return "/WEB-INF/view/user_edit.jsp";
    }
}
