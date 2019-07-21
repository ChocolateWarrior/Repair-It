package com.helvetica.controller.command;

import com.helvetica.services.services.UserRegistrationService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    private UserRegistrationService userRegistrationService;

    public RegistrationCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        userRegistrationService.registerUser(request);
        return "user_registration.jsp";
    }
}
