package com.helvetica.controller.command;

import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

class CommandUtility {
    static void setUserRole(HttpServletRequest request,
                            Role role, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("username", name);
        session.setAttribute("role", role);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName){
        HashSet<String> users = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("users");

        if(users.stream().anyMatch(userName::equals)){
            return true;
        }
        users.add(userName);
        request.getSession().getServletContext()
                .setAttribute("users", users);
        return false;
    }
}
