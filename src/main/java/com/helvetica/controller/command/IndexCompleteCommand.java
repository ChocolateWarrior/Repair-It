package com.helvetica.controller.command;

import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;

public class IndexCompleteCommand implements Command{

    private MainPageService mainPageService;

    public IndexCompleteCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("master_request_id"));
        request.setAttribute("completed", true);
        mainPageService.completeRequest(id);
        return "/WEB-INF/view/index.jsp";
    }
}
