package com.helvetica.controller.command;

import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;

public class RequestDeleteCommand implements Command {
    private RequestDisplayService requestDisplayService;

    public RequestDeleteCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        requestDisplayService.deleteRequest(id);
        return "redirect:/display-request";
    }
}
