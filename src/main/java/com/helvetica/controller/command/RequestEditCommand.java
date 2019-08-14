package com.helvetica.controller.command;

import com.helvetica.controller.validators.PositiveValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;
import com.helvetica.services.RequestService;
import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class RequestEditCommand implements Command {

    private RequestService requestService;
    private UserService userService;
    private ResourceBundle resourceBundle;

    public RequestEditCommand(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        PositiveValidator positiveValidator = new PositiveValidator(new BigDecimal(0),
                new BigDecimal(50000), resourceBundle.getString("valid.positive"));

        int id = Integer.parseInt(request.getParameter("id"));
        RepairRequest requestToEdit = requestService.findById(id);
        HashSet<User> masters = userService.findMastersBySpecification(requestToEdit.getSpecification());
        request.setAttribute("request", requestToEdit);
        request.setAttribute("all_masters", masters);
        String masterUsername = request.getParameter("masters");

        Optional<String> priceOptional = Optional.ofNullable(request.getParameter("master_request_price"));

        String priceStr="";
        if (priceOptional.isPresent()){
            if (!priceOptional.get().isEmpty())
                priceStr = priceOptional.get();
        }

        if(!priceStr.isEmpty()) {
            Result result = positiveValidator.validate(request.getParameter("master_request_price"));
            if (!result.isOk()) {
                request.setAttribute("price_message_er", result.getMessage());
                return "/WEB-INF/view/request_edit.jsp";
            }

            BigDecimal price = new BigDecimal(request.getParameter("master_request_price"));
            requestService.updateMasterRequest(id, RequestState.ACCEPTED.name(), price);

        }

        requestService.editRequest(masterUsername, requestToEdit);
        request.setAttribute("message_sc", resourceBundle.getString("global.success"));
        return "/WEB-INF/view/request_edit.jsp";
    }
}
