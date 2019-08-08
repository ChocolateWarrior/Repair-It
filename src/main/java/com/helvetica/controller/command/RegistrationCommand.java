package com.helvetica.controller.command;

import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserRegistrationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
 import java.util.List;
import java.util.Objects;

public class RegistrationCommand implements Command {

    private UserRegistrationService userRegistrationService;
    public static final Logger log = LogManager.getLogger();

    public RegistrationCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {


        request.setAttribute("all_specifications", Specification.values());

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] specifications = request.getParameterValues("specifications");

        if (!(Objects.nonNull(firstName) &&
                Objects.nonNull(lastName) &&
                Objects.nonNull(username) &&
                Objects.nonNull(password))) {
            return "/WEB-INF/view/user_registration.jsp";
        }

        List<String> userSpec = new ArrayList<>();
        List<Specification> specList = new ArrayList<>();

        if(Objects.nonNull(specifications)) {
            log.info("found specifications");
            userSpec = List.of(specifications);
            userSpec.forEach(e -> specList.add(Specification.valueOf(e)));
        }


        if(!userSpec.isEmpty()){
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
}
