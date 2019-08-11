package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.RepairRequest;
import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;
import com.helvetica.services.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Objects;

public class RequestCommand implements Command {

    private RequestService requestService;

    public RequestCommand() {
        this.requestService = new RequestService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(8, 80);
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator);

        HttpSession session = request.getSession();

        String specification = request.getParameter("specification");
        String description = request.getParameter("description");
        User user = (User)session.getAttribute("user");

        if (!(Objects.nonNull(specification) &&
                Objects.nonNull(description) &&
                Objects.nonNull(user))) {
            return "/WEB-INF/view/request.jsp";
        }

        Result result = notBlankValidator.validate(description);
        if(!result.isOk()){
            request.setAttribute("error", result.getMessage());
            request.setAttribute("specification", specification);
            request.setAttribute("description", description);
            return "/WEB-INF/view/request.jsp";
        }

        RepairRequest repairRequest = new RepairRequest(user,
                description,
                LocalDateTime.now(),
                Specification.valueOf(specification.toUpperCase()));

        requestService.addRequest(repairRequest);
        return "/WEB-INF/view/request.jsp";

    }
}
