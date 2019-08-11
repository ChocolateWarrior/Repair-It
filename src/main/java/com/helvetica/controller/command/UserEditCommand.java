package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.controller.validators.SimpleResult;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserEditCommand implements Command{

    private UserDisplayService userDisplayService;
    ResourceBundle resourceBundle;

    public UserEditCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(2, 30,
                resourceBundle.getString("valid.in_range"));

        int id = Integer.parseInt(request.getParameter("id"));

        User userToEdit = userDisplayService.findById(id);
        request.setAttribute("user", userToEdit);

        Optional<String> firstName = Optional.ofNullable(request.getParameter("firstNameEdit"));
        Optional<String> lastName = Optional.ofNullable(request.getParameter("lastNameEdit"));
        Optional<String> username = Optional.ofNullable(request.getParameter("loginEdit"));
        Optional<String> password = Optional.ofNullable(request.getParameter("passwordEdit"));

        Result result;

        if(firstName.isPresent()){
            result = rangeLengthValidator.validate(firstName.get());
            if (!result.isOk())
                return handleValidationError(request, result, firstName.get(),
                        lastName.get(), username.get(), password.get());
        }

        if(lastName.isPresent()) {
            result = rangeLengthValidator.validate(lastName.get());
            if (!result.isOk())
                return handleValidationError(request, result, firstName.get(),
                        lastName.get(), username.get(), password.get());
        }

        if(username.isPresent()) {
            result = rangeLengthValidator.validate(username.get());
            if (!result.isOk())
                return handleValidationError(request, result, firstName.get(),
                        lastName.get(), username.get(), password.get());
        }

        if(password.isPresent()){
            result = rangeLengthValidator.validate(firstName.get());
            if(!result.isOk())
                return handleValidationError(request, result, firstName.get(),
                        lastName.get(), username.get(), password.get());
        }

        firstName.ifPresent(s -> userToEdit.setFirstName(s.isEmpty() ? userToEdit.getFirstName() : s));
        lastName.ifPresent(s -> userToEdit.setLastName(s.isEmpty() ? userToEdit.getLastName() : s));
        username.ifPresent(s -> userToEdit.setUsername(s.isEmpty() ? userToEdit.getUsername() : s));
        password.ifPresent(s -> userToEdit.setPassword(s.isEmpty() ? userToEdit.getPassword() : s));

        userDisplayService.editUser(userToEdit);
        return "/WEB-INF/view/user_edit.jsp";
    }

    private String handleValidationError(HttpServletRequest request, Result result,
                                         String firstName, String lastName,
                                         String username, String password){
        request.setAttribute("error", result.getMessage());
        request.setAttribute("firstNameEdit", firstName);
        request.setAttribute("lastNameEdit", lastName);
        request.setAttribute("loginEdit", username);
        request.setAttribute("passwordEdit", password);
        return "/WEB-INF/view/user_registration.jsp";
    }

}
