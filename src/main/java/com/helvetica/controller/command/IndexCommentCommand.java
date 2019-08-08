package com.helvetica.controller.command;

import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;

public class IndexCommentCommand implements Command {

    private MainPageService mainPageService;

    public IndexCommentCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        mainPageService.leaveComment(request);
        return "redirect:/index";
    }
}
