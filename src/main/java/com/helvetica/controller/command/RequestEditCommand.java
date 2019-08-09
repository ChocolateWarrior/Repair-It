package com.helvetica.controller.command;

import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.User;
import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class RequestEditCommand implements Command {

    private RequestDisplayService requestDisplayService;

    public RequestEditCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        RepairRequest requestToEdit = requestDisplayService.findById(id);
        HashSet<User> masters = requestDisplayService.findMastersBySpecification(requestToEdit.getSpecification());
        request.setAttribute("request", requestToEdit);
        request.setAttribute("all_masters", masters);
        String masterUsername = request.getParameter("masters");

        requestDisplayService.editRequest(masterUsername, requestToEdit);
        return "/WEB-INF/view/request_edit.jsp";
    }
}
