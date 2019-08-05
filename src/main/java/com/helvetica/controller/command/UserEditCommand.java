package com.helvetica.controller.command;

import com.helvetica.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;

public class UserEditCommand implements Command{

    private UserDisplayService userDisplayService;

    public UserEditCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        userDisplayService.editUser(request);
        return "/WEB-INF/view/user_edit.jsp";
    }

}
