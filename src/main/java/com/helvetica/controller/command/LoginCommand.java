package com.helvetica.controller.command;

import com.helvetica.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            //System.out.println("Not");
            return "/login.jsp";
        }
        System.out.println(name + " " + pass);
        //System.out.println("Yes!");
//todo: check login with DB

//        if()

        if(CommandUtility.checkUserIsLogged(request, name)) {
            return "/WEB-INF/error.jsp";
        }

        if (name.equals("Admin")){
            CommandUtility.setUserRole(request, Role.ADMIN, name);
            return "/WEB-INF/admin_page.jsp";
        } else if(name.equals("User")) {
            CommandUtility.setUserRole(request, Role.USER, name);
            return "/WEB-INF/user_page.jsp";
        } else {
            CommandUtility.setUserRole(request, Role.UNKNOWN, name);
            return "/login.jsp";
        }


    }

}
