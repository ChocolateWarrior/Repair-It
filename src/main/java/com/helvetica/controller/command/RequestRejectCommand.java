package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.services.RequestDisplayService;

import javax.servlet.http.HttpServletRequest;

public class RequestRejectCommand implements Command {

    private RequestDisplayService requestDisplayService;

    public RequestRejectCommand() {
        this.requestDisplayService = new RequestDisplayService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(8, 80);
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator);

        int id = Integer.parseInt(request.getParameter("rejection_id"));
        String message = request.getParameter("rejection_message");

        Result result = notBlankValidator.validate(message);
        if(!result.isOk()){
            request.setAttribute("message_error", result.getMessage());
            request.setAttribute("rejection_message", message);
            return "/WEB-INF/view/request_display.jsp";
        }

        requestDisplayService.rejectRequest(id, message);
        return "/WEB-INF/view/request_display.jsp";

    }
}
