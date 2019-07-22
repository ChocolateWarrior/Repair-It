package com.helvetica.controller.command;

import com.helvetica.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.setUserRole(request, Role.UNKNOWN, "Guest");
        return "/WEB-INF/view/index.jsp";
    }
}
