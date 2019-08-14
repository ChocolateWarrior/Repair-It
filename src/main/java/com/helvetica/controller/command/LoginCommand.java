package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private UserService userService;
    private ResourceBundle resourceBundle;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(Objects.isNull(username) || Objects.isNull(password)){
            return "/WEB-INF/view/login.jsp";
        }
        User user;

        try {
            user = userService.getByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("message_er", resourceBundle.getString("login.invalid"));
            return "/WEB-INF/view/login.jsp";
        }

        if (Objects.isNull(user)) {
            request.setAttribute("message_er", resourceBundle.getString("login.invalid"));
            return "/WEB-INF/view/login.jsp";
        }

        if (user.getPassword().equals(DigestUtils.md5Hex(password).toUpperCase())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("balance", user.getBalance());
            session.setAttribute("roles", user.getAuthorities());
            return "redirect:/index";
        } else {
            request.setAttribute("message_er", resourceBundle.getString("login.invalid"));
            return "/WEB-INF/view/login.jsp";
        }


    }

}
