package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.UserDisplayService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;

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
            user = userDisplayService.getByUsername(username);
        }catch (Exception e){
            e.printStackTrace();
            request.setAttribute("message_er", "Wrong credentials!");
            return "/WEB-INF/view/login.jsp";
        }

        if (Objects.isNull(user)) {
            request.setAttribute("message_er", "Wrong credentials!");
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
            return "/WEB-INF/view/login.jsp";
        }


    }

}
