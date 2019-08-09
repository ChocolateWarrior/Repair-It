package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserEditCommand implements Command{

    private UserDisplayService userDisplayService;

    public UserEditCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));

        User userToEdit = userDisplayService.findById(id);
        request.setAttribute("user", userToEdit);

        Optional<String> firstName = Optional.ofNullable(request.getParameter("firstNameEdit"));
        Optional<String> lastName = Optional.ofNullable(request.getParameter("lastNameEdit"));
        Optional<String> username = Optional.ofNullable(request.getParameter("loginEdit"));
        Optional<String> password = Optional.ofNullable(request.getParameter("passwordEdit"));


        firstName.ifPresent(s -> userToEdit.setFirstName(s.isEmpty() ? userToEdit.getFirstName() : s));
        lastName.ifPresent(s -> userToEdit.setLastName(s.isEmpty() ? userToEdit.getLastName() : s));
        username.ifPresent(s -> userToEdit.setUsername(s.isEmpty() ? userToEdit.getUsername() : s));
        password.ifPresent(s -> userToEdit.setPassword(s.isEmpty() ? userToEdit.getPassword() : s));

        userDisplayService.editUser(userToEdit);
        return "/WEB-INF/view/user_edit.jsp";
    }

}
