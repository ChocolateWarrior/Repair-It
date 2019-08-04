package com.helvetica.controller.command;

import com.helvetica.services.RequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RequestCommand implements Command {

    private RequestService requestService;

    public RequestCommand() {
        this.requestService = new RequestService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("OK(2)");

        return requestService.addRequest(request);
//        return "/WEB-INF/view/request.jsp";
    }
}
