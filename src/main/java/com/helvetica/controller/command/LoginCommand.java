package com.helvetica.controller.command;

import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;
import com.helvetica.services.services.UserDisplayService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class LoginCommand implements Command {

    private UserDisplayService userDisplayService;

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(Objects.isNull(username) || Objects.isNull(password)){
            return "/WEB-INF/view/login.jsp";
        }
        System.out.println(username + " " + password);


        Optional<User> userOptional = Optional.ofNullable(userDisplayService.getByUsernameAndPassword(username, password));
        if (!userOptional.isPresent()) {
            System.out.println("No such user " + username + " in database");
            return "/WEB-INF/view/login.jsp";
        }
//        if()

        User user = userOptional.get();

        if (user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            System.out.println("User " + username + " successfully logged in");
            return "redirect:index";
        } else {
            System.out.println("Invalid credentials for user " + username);
            return "/WEB-INF/view/login.jsp";
        }


    }

}
