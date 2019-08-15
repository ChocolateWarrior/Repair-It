package com.helvetica.controller.command;

import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserDisplayCommand implements Command {

    private UserService userService;

    public UserDisplayCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        request.setAttribute("users", userService.findAll());
        return "/WEB-INF/view/user_display.jsp";
    }
}
