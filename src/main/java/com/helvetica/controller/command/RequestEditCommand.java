package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.PositiveValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;
import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;

public class RequestEditCommand implements Command {

    private RequestDisplayService requestDisplayService;

    public RequestEditCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        PositiveValidator positiveValidator = new PositiveValidator(new BigDecimal(0), new BigDecimal(50000));
        NotBlankValidator notBlankValidator = new NotBlankValidator(positiveValidator);

        int id = Integer.parseInt(request.getParameter("id"));
        RepairRequest requestToEdit = requestDisplayService.findById(id);
        HashSet<User> masters = requestDisplayService.findMastersBySpecification(requestToEdit.getSpecification());
        request.setAttribute("request", requestToEdit);
        request.setAttribute("all_masters", masters);
        String masterUsername = request.getParameter("masters");


        if(Objects.nonNull(request.getParameter("master_request_price"))) {

            Result result = notBlankValidator.validate(request.getParameter("master_request_price"));

            if (!result.isOk()){
                request.setAttribute("price_message_er", result.getMessage());
                return "/WEB-INF/view/request_edit.jsp";
            }

            BigDecimal price = new BigDecimal(request.getParameter("master_request_price"));
            requestDisplayService.updateMasterRequest(id, RequestState.ACCEPTED.name(), price);
        }

        requestDisplayService.editRequest(masterUsername, requestToEdit);
        return "/WEB-INF/view/request_edit.jsp";
    }
}
