package com.helvetica.controller.command;

import com.helvetica.services.services.UserDisplayService;
import com.helvetica.services.services.UserRegistrationService;

import javax.servlet.http.HttpServletRequest;

public class UserDisplayCommand implements Command {

    private UserDisplayService userDisplayService;

    public UserDisplayCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        userDisplayService.displayUsers(request);
        return "/WEB-INF/view/user_display.jsp";
    }
}
