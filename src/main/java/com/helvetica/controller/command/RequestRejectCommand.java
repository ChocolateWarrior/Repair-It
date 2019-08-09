package com.helvetica.controller.command;

import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;

public class RequestRejectCommand implements Command {

    private RequestDisplayService requestDisplayService;

    public RequestRejectCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("rejection_id"));
        String message = request.getParameter("rejection_message");

        requestDisplayService.rejectRequest(id, message);
        return "redirect:/display-request";

    }
}
