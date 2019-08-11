package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserRegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class RegistrationCommand implements Command {

    private UserRegistrationService userRegistrationService;
    private ResourceBundle resourceBundle;
    public static final Logger log = LogManager.getLogger();

    public RegistrationCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        System.out.println(CommandUtility.getSessionLocale(request));
        resourceBundle = ResourceBundle.getBundle("property/messages",
                CommandUtility.getSessionLocale(request));

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(2, 30,
                resourceBundle.getString("valid.in_range"));
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator,
                resourceBundle.getString("valid.non_blank"));

        request.setAttribute("all_specifications", Specification.values());

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

        List<Specification> specList = new ArrayList<>();
        System.out.println("HERE");

        if(Objects.nonNull(request.getParameterValues("specifications"))) {
            specList = Arrays
                    .stream(request.getParameterValues("specifications"))
                    .map(Specification::valueOf)
                    .collect(Collectors.toList());
        }

        System.out.println(specList);


        Result result = notBlankValidator.validate(firstName);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName,
                    username, password, "first_name_error");

        result = notBlankValidator.validate(lastName);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName,
                    username, password, "last_name_error");

        result = notBlankValidator.validate(username);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName,
                    username, password, "username_error");

        notBlankValidator = new NotBlankValidator(
                new RangeLengthValidator(8, 50,resourceBundle.getString("valid.in_range"))
                , resourceBundle.getString("valid.non_blank") );
        result = notBlankValidator.validate(password);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName,
                    username, password, "password_error");

        if(!specList.isEmpty()){
            User user = new User(firstName, lastName, username, password, specList);
            log.info("executing master registration...");
            userRegistrationService.registerMaster(user);
        }else {
            User user = new User(firstName, lastName, username, password);
            log.info("executing user registration...");
            userRegistrationService.registerUser(user);
        }

        return "redirect:/login";
    }

    private String handleValidationError(HttpServletRequest request, Result result,
                                         String firstName, String lastName,
                                         String username, String password, String errorName){
        request.setAttribute(errorName, result.getMessage());
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        return "/WEB-INF/view/user_registration.jsp";
    }

}
