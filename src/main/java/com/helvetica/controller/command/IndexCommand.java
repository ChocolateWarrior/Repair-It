package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.MainPageService;
import com.helvetica.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class IndexCommand implements Command {

    private MainPageService mainPageService;
    private UserDisplayService userDisplayService;

    public IndexCommand() {
        this.mainPageService = new MainPageService();
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {


        HttpSession session = request.getSession();
        User user = userDisplayService.getByUsernameAndPassword((String)session.getAttribute("username"),
                (String)session.getAttribute("username"));

        request.setAttribute("user_requests", mainPageService.findByUser(user.getId()));
        request.setAttribute("master_requests", mainPageService.findByMaster(user.getId()));
        request.setAttribute("user", user);

        return "/WEB-INF/view/index.jsp";
    }
}
