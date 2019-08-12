package com.helvetica.controller.command;

import com.helvetica.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;

public class UserDisplayCommand implements Command {

    private UserDisplayService userDisplayService;

    public UserDisplayCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        request.setAttribute("users", userDisplayService.findAll());
        request.getRequestDispatcher("/WEB-INF/user_display.jsp");
        return "/WEB-INF/view/user_display.jsp";
    }
}
