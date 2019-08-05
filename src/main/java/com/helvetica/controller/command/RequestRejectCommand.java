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

        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
//        String message = request.getParameter("rejectionMessage");
//        System.out.println(message);
        String message = "FUCK YOU!";

        requestDisplayService.rejectRequest(id, message);
        return "redirect:/repairit_war/display-request";

    }
}
