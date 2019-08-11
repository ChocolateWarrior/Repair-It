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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RegistrationCommand implements Command {

    private UserRegistrationService userRegistrationService;
    public static final Logger log = LogManager.getLogger();

    public RegistrationCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(2, 30);
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator);

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

//        String[] specifications = request.getParameterValues("specifications");
//
//        List<String> userSpec = new ArrayList<>();
//        if(Objects.nonNull(specifications)) {
//            log.info("found specifications");
//            userSpec = List.of(specifications);
//            userSpec.forEach(e -> specList.add(Specification.valueOf(e)));
//        }

        List<Specification> specList = Arrays
                .stream(request.getParameterValues("specifications"))
                .map(Specification::valueOf)
                .collect(Collectors.toList());


        Result result = notBlankValidator.validate(firstName);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName, username, password);

        result = notBlankValidator.validate(lastName);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName, username, password);

        result = notBlankValidator.validate(username);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName, username, password);

        notBlankValidator = new NotBlankValidator(new RangeLengthValidator(8, 50));
        result = notBlankValidator.validate(password);

        if(!result.isOk())
            return handleValidationError(request, result, firstName, lastName, username, password);

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
                                         String username, String password){
        request.setAttribute("error", result.getMessage());
        request.setAttribute("first_name", firstName);
        request.setAttribute("last_name", lastName);
        request.setAttribute("username", username);
        request.setAttribute("password", password);
        return "/WEB-INF/view/user_registration.jsp";
    }

}
