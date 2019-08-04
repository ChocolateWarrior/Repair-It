package com.helvetica.controller.command;

import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;

public class IndexCommand implements Command {

    private MainPageService mainPageService;

    public IndexCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        return mainPageService.getMainPage(request);
    }
}
