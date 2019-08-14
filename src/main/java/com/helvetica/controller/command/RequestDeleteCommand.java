package com.helvetica.controller.command;

import com.helvetica.services.RequestService;

import javax.servlet.http.HttpServletRequest;

public class RequestDeleteCommand implements Command {

    private RequestService requestService;

    public RequestDeleteCommand(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        requestService.deleteRequest(id);
        return "redirect:/display-request";
    }
}
