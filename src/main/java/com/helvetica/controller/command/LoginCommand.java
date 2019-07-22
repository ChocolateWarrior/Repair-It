package com.helvetica.controller.command;

import com.helvetica.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if( username == null || username.equals("") || password == null || password.equals("")  ){
            //System.out.println("Not");
            return "/WEB-INF/view/login.jsp";
        }
        System.out.println(username + " " + password);
        //System.out.println("Yes!");
//todo: check login with DB

//        if()

        if(CommandUtility.checkUserIsLogged(request, username)) {
            return "/WEB-INF/error.jsp";
        }

        if (username.equals("Admin")){
            CommandUtility.setUserRole(request, Role.ADMIN, username);
            return "/WEB-INF/view/admin_page.jsp";
        } else if(username.equals("User")) {
            CommandUtility.setUserRole(request, Role.USER, username);
            return "/WEB-INF/view/user_page.jsp";
        } else {
            CommandUtility.setUserRole(request, Role.UNKNOWN, username);
            return "/WEB-INF/view/login.jsp";
        }


    }

}
