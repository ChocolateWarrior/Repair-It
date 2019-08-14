package com.helvetica.controller.command;

import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserEditCommand implements Command{

    private UserService userService;
    ResourceBundle resourceBundle;

    public UserEditCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(2, 30,
                resourceBundle.getString("valid.in_range"));

        int id = Integer.parseInt(request.getParameter("id"));

        User userToEdit = userService.findById(id);
        request.setAttribute("user", userToEdit);

        Optional<String> firstName = Optional.ofNullable(request.getParameter("first_name"));
        Optional<String> lastName = Optional.ofNullable(request.getParameter("last_name"));
        Optional<String> username = Optional.ofNullable(request.getParameter("username"));

        Result result;


        String str="";
        if (firstName.isPresent()){
            if (!firstName.get().isEmpty())
                str = firstName.get();
        }

        if(!str.isEmpty()){
            result = rangeLengthValidator.validate(firstName.get());
            if (!result.isOk()) {
                request.setAttribute("first_name_error", result.getMessage());
                return handleValidationError(request, firstName.get(),
                        lastName.get(), username.get());
            }
            str = "";
        }

        if (lastName.isPresent()){
            if (!lastName.get().isEmpty())
                str = lastName.get();
        }

        if(!str.isEmpty()) {
            result = rangeLengthValidator.validate(lastName.get());
            if (!result.isOk()) {
                request.setAttribute("last_name_error", result.getMessage());
                return handleValidationError(request, firstName.get(),
                        lastName.get(), username.get());
            }
            str = "";
        }

        if (username.isPresent()){
            if (!username.get().isEmpty())
                str = username.get();
        }

        if(!str.isEmpty()) {
            result = rangeLengthValidator.validate(username.get());
            if (!result.isOk()) {
                request.setAttribute("username_error", result.getMessage());
                return handleValidationError(request, firstName.get(),
                        lastName.get(), username.get());
            }
        }

        firstName.ifPresent(s -> userToEdit.setFirstName(s.isEmpty() ? userToEdit.getFirstName() : s));
        lastName.ifPresent(s -> userToEdit.setLastName(s.isEmpty() ? userToEdit.getLastName() : s));
        username.ifPresent(s -> userToEdit.setUsername(s.isEmpty() ? userToEdit.getUsername() : s));


        userService.editUser(userToEdit);
        request.setAttribute("message_sc", resourceBundle.getString("global.success"));
        return "/WEB-INF/view/user_edit.jsp";
    }
    private String handleValidationError(HttpServletRequest request,
                                         String firstName, String lastName,
                                         String username){
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("username", username);
        return "/WEB-INF/view/user_edit.jsp";
    }

}
