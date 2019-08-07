package com.helvetica.controller.command;

import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserRegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
 import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class RegistrationCommand implements Command {

    private UserRegistrationService userRegistrationService;

    public RegistrationCommand() {
        this.userRegistrationService = new UserRegistrationService();
    }

    @Override
    public String execute(HttpServletRequest request) {


        List<String> names = new ArrayList<>();
        Stream.of(Specification.values()).forEach(e -> names.add(e.name()));
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

        List<String> userSpec;
        List<Specification> specList = new ArrayList<>();
        userSpec = List.of(specifications);
        userSpec.forEach(e -> specList.add(Specification.valueOf(e)));


        if(!userSpec.isEmpty()){
            User user = new User(firstName, lastName, username, password, specList);
            userRegistrationService.registerMaster(user);
            return "redirect:/repairit_war/login";
        }else {
            User user = new User(firstName, lastName, username, password);
            userRegistrationService.registerUser(user);
        }

        return "redirect:/repairit_war/login";
    }
}
