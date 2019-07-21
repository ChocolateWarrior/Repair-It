package com.helvetica.controller.command;

import javax.servlet.http.HttpServletRequest;

public class UserPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        return "WEB-INF/user_page.jsp";
    }
}
