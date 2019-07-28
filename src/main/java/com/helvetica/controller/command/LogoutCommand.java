package com.helvetica.controller.command;

import com.helvetica.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
//        CommandUtility.setUserRole(request, Role.UNKNOWN, "Guest");

        HttpSession session = request.getSession();
        session.invalidate();
        return "/WEB-INF/view/login.jsp";
    }
}
