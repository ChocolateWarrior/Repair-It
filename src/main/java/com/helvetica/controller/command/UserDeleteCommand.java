package com.helvetica.controller.command;

import com.helvetica.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;

public class UserDeleteCommand implements Command {
    private UserDisplayService userDisplayService;

    public UserDeleteCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        userDisplayService.deleteUser(id);
        return "redirect:/repairit_war/display";
    }
}
