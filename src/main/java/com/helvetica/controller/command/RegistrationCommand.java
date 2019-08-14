package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class to manage user registration, works with it`s appropriate service
 * @author helvetica
 * @version 1.0
 */


public class RegistrationCommand implements Command {

    /** field of UserService type
     * @see UserService
     */
    private UserService userService;

    /** field of appropriate resource bundle */
    private ResourceBundle resourceBundle;

    /**
     * Constructor -creation of new object with new UserService from argument
     */
    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Typical execute method (Command pattern)
     * contains validators for proper registration management
     * @see NotBlankValidator
     * @see RangeLengthValidator
     * gets parameters, assures they are not null, validates,
     * if everithing is fine - calls service`s registration method with encrypted password &
     * returns redirect to login.
     * in case of failure - returns registration .jsp, with a hint of whic parameter is incorrect.
     * @param request - current request
     * @return path to appropriate .jsp
     */
    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages",
                CommandUtility.getSessionLocale(request));

        final String notUniqueMessage = resourceBundle.getString("reg.not_unique");

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(2, 30,
                resourceBundle.getString("valid.in_range"));
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator,
                resourceBundle.getString("valid.non_blank"));

        request.setAttribute("all_specifications", Specification.values());

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(userService.isDuplicateUsername(username)){
            request.setAttribute("error_message", notUniqueMessage);
            return handleRegistrationError(request, firstName, lastName, username, password);
        }

        if (!(Objects.nonNull(firstName) &&
                Objects.nonNull(lastName) &&
                Objects.nonNull(username) &&
                Objects.nonNull(password))) {
            return "/WEB-INF/view/user_registration.jsp";
        }

        List<Specification> specList = new ArrayList<>();

        if(Objects.nonNull(request.getParameterValues("specifications"))) {
            specList = Arrays
                    .stream(request.getParameterValues("specifications"))
                    .map(Specification::valueOf)
                    .collect(Collectors.toList());
        }


        Result result = notBlankValidator.validate(firstName);

        if(!result.isOk()) {
            request.setAttribute("first_name_error", result.getMessage());
            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        result = notBlankValidator.validate(lastName);

        if(!result.isOk()) {
            request.setAttribute("last_name_error", result.getMessage());
            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        result = notBlankValidator.validate(username);

        if(!result.isOk()) {
            request.setAttribute("username_error", result.getMessage());
            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        notBlankValidator = new NotBlankValidator(
                new RangeLengthValidator(8, 50,resourceBundle.getString("valid.in_range"))
                , resourceBundle.getString("valid.non_blank") );
        result = notBlankValidator.validate(password);

        if(!result.isOk()) {
            request.setAttribute("password_error", result.getMessage());
            return handleRegistrationError(request, firstName, lastName,
                    username, password);
        }

        String hashedPassword = DigestUtils.md5Hex(password).toUpperCase();

        if(!specList.isEmpty()){
            User user = new User(firstName, lastName, username, hashedPassword, specList);
            userService.registerMaster(user);
        }else {
            User user = new User(firstName, lastName, username, hashedPassword);
            userService.registerUser(user);
        }

        return "redirect:/login";
    }

    /**
     * Private method for managing possible failure
     * @param request - current request
     * @param firstName - first name attribute
     * @param lastName - last name attribute
     * @param username - username attribute
     * @param password - password attribute
     * @return path back to registration
     */
    private String handleRegistrationError(HttpServletRequest request,
                                           String firstName, String lastName,
                                           String username, String password){
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        return "/WEB-INF/view/user_registration.jsp";
    }

}
