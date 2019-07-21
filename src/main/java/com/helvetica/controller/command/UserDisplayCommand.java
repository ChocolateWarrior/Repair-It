package com.helvetica.controller.command;

import com.helvetica.services.services.UserRegistrationService;

import javax.servlet.http.HttpServletRequest;

public class UserDisplayCommand implements Command {

    private UserRegistrationService userRegistrationService;

    public UserDisplayCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        userRegistrationService.registerUser(request);
        return "user_display.jsp";
    }
}
