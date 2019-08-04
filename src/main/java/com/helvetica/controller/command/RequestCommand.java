package com.helvetica.controller.command;

import com.helvetica.services.RequestService;

import javax.servlet.http.HttpServletRequest;

public class RequestCommand implements Command {

    private RequestService requestService;

    public RequestCommand() {
        this.requestService = new RequestService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        return requestService.addRequest(request);
    }
}
