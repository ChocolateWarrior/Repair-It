package com.helvetica.controller.command;

import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;

public class MasterIndexCommand implements Command{

    private MainPageService mainPageService;

    public MasterIndexCommand(){
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        mainPageService.updateMasterRequest(request);
        return "redirect:/index";
    }
}
