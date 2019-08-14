package com.helvetica.controller.command;

import com.helvetica.services.RequestService;

import javax.servlet.http.HttpServletRequest;

public class IndexCompleteCommand implements Command{

    private RequestService requestService;

    public IndexCompleteCommand(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("master_request_id"));
        request.setAttribute("completed", true);
        requestService.completeRequest(id);
        return "redirect:/index";
    }
}
