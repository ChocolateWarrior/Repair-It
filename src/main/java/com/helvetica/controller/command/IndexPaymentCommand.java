package com.helvetica.controller.command;

import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;

public class IndexPaymentCommand implements Command{

    private MainPageService mainPageService;

    public IndexPaymentCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        mainPageService.payForRequest(request);
        return "redirect:/index";
    }
}
