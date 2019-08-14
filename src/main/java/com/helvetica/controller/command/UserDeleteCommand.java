package com.helvetica.controller.command;

import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserDeleteCommand implements Command {

    private UserService userService;

    public UserDeleteCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        return "redirect:/display";
    }
}
