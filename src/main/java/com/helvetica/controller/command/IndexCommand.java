package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IndexCommand implements Command {

    private MainPageService mainPageService;

    public IndexCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("user_requests", mainPageService.findByUser(user.getId()));
        request.setAttribute("master_requests", mainPageService.findByMaster(user.getId()));
        request.setAttribute("user", user);

        return "/WEB-INF/view/index.jsp";
    }
}
