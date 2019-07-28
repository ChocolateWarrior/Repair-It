package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.UserRegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RegistrationCommand implements Command {

    private UserRegistrationService userRegistrationService;

    public RegistrationCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!(Objects.nonNull(firstName) &&
                Objects.nonNull(lastName) &&
                Objects.nonNull(username) &&
                Objects.nonNull(password))) {
            return "/WEB-INF/view/user_registration.jsp";
        }

        User user = new User(firstName, lastName, username, password);
        userRegistrationService.registerUser(user);
        return "/WEB-INF/view/user_registration.jsp";
    }
}
