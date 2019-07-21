package com.helvetica.controller.command;

import javax.servlet.http.HttpServletRequest;

public class AdminPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {

        return "WEB-INF/admin_page.jsp";
    }
}
