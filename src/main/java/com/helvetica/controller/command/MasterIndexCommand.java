package com.helvetica.controller.command;

import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class MasterIndexCommand implements Command{

    private MainPageService mainPageService;

    public MasterIndexCommand(){
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("master_request_id"));
        String state = request.getParameter("master_request_state");
        BigDecimal price = new BigDecimal(request.getParameter("master_request_price"));

        mainPageService.updateMasterRequest(id, state, price);
        return "redirect:/index";
    }
}
