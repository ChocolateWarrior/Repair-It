package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserDisplayService;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Log4j2
public class LoginCommand implements Command {

    private UserDisplayService userDisplayService;

    public LoginCommand() {
        this.userDisplayService = new UserDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(Objects.isNull(username) || Objects.isNull(password)){
            return "/WEB-INF/view/login.jsp";
        }

        User user;

        try {
            user = userDisplayService.getByUsernameAndPassword(username, password);
        }catch (Exception e){
            System.out.println("HERE IS THE ERROR");
            e.printStackTrace();
            request.setAttribute("message_er", "Wrong credentials!");
            return "/WEB-INF/view/login.jsp";
        }

        if (Objects.isNull(user)) {
            log.warn("No such user " + username + " in database");
            return "/WEB-INF/view/login.jsp";
        }

        if (user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("balance", user.getBalance());
            session.setAttribute("roles", user.getAuthorities());
            log.info("User " + username + " successfully logged in");
            return "redirect:/index";
        } else {
            log.warn("Invalid credentials for user " + username);
            return "/WEB-INF/view/login.jsp";
        }


    }

}
